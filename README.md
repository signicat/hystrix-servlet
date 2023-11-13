Hystrix-Servlet
===============

A simple "glue" between Hystrix and Servlet 3.0.

# What Problem Are We Solving

Assume the following setup (simplified):

* Clients (named C1, C2, ...) are tenants of a single Servlet container, running on a single JVM on a single physical node.
* Each client has a unique set of end users (named U1, U2, ...), accessing its service, which we host in said Servlet container.
* Each client has access to a set of back-end services (named B1, B2, ...). Note that these may overlap. Say, both client C1 and C2 allow their users to use back-end service B1, but only client C3 allows its users to use back-end service B2.
* We do not control the back-end services.
  * In fact, we do not necessarily control exactly *how* to access back-end services. Some of them are accessed through binary-only libraries. We don't know exactly what's going on inside those libraries.
* We (obviously) do not control end users.
* The application running in the Servlet container is a straight-forward synchronous Java web application.

In most cases this works quite beautifully. When serving a request for an end user of, say, client C1, the following happens:

1. End user U1 wants to access client C1 from his browser.
1. An HTTP request is received by the Servlet container.
1. The container thread passes it to the Servlet.service() method.
1. This method will determine that the request is for client C1.
1. Some configuration is looked up.
1. It is determined (somehow) that this request will go towards back-end service B1.
1. B1 replies in time.
1. The response is returned to U1.

Note that in real life clients are protected by class-loader separation, BUT they DO share the same pool of threads
from the Servlet container. If a back-end service has an extremely high latency, **this will become a problem**.

1. Several end users want to access C1 or C2 from their browsers. *Recall that both C1 and C2 go toward back-end service B1.*
1. B1 is having trouble, with very high latencies.
1. As in the example above, container threads are used to service users of C1 and C2.
1. *Due to the high latency of B1, these container threads are blocked for a very long time!*
1. More users of C1 and C2 come along, many of the first users are re-trying their requests, and pretty soon *all Servlet container threads are blocked*.
1. Some end user wanting to access C3 comes along. Recall that client C3 doesn't even have access to back-end service B1 (which is having trouble), but is *only* using back-end B2.
1. **There is essentially a denial-of-service for users of C3**.

Note that

* It can be argued that the high response times for users of C1 and C2 are "acceptable", i.e. we are just propagating the high latencies back towards users. It's not really our fault.
* The denial-of-service for users of C3 is *totally unacceptable*. These should be completely unaffected by the problems at B1.

**Hystrix-Servlet is a library that can mitigate this problem, by isolating clients in their own thread pools.**


# How Are We Solving This Problem

We already have two important building blocks - [Hystrix](https://github.com/Netflix/Hystrix) and
[Servlet 3.0](http://docs.oracle.com/javaee/6/api/javax/servlet/package-summary.html).

In Servlet 3.0, essentially the `ServletRequest.startAsync()` method allows us to

* Be called by a Servlet container thread in `Servlet.service()`, as usual.
* Call `ServletRequest.startAsync()` and pass the resulting `AsyncContext` on to a thread pool or a queue of our own.
* Return from `Servlet.service()`.
* The Servlet container thread is done!

Obviously we need to keep track of the `AsyncContext`, and process that in a thread of our own. Through that object
we have access to the `ServletRequest` and `ServletResponse` pair, so we are able to fully service the HTTP request.
When we're done, we call `AsyncContext.complete()` to notify the Servlet container.

Now that "pass it on to a thread pool of our own" business can be taken care of by Hystrix, which is an excellent
library for doing just that - isolating groups of commands in separate thread pools.

Following our original example, we can isolate access to back-ends B1 and B2 in separate Hystrix thread pools. The
example then becomes:

1. Several end users want to access C1 or C2 from their browsers. *Recall that both C1 and C2 go toward back-end service B1.*
1. B1 is having trouble, with very high latencies.
1. Container threads are only used to accept the HTTP requests from users of C1 and C2.
1. These requests are handed over to the thread pool of back-end B1. The Servlet container threads are done.
1. *Due to the high latency of B1, the B1 thread pool threads are blocked for a very long time!*
1. More users of C1 and C2 come along, many of the first users are re-trying their requests, and pretty soon *all B1 thread pool threads are blocked*.
1. Some end user wanting to access C3 comes along. Recall that client C3 doesn't even have access to back-end service B1 (which is having trouble), but is *only* using back-end B2.
1. Since we have plenty of Servlet container threads available, one of these will accept the HTTP request from the user of C3.
1. This HTTP request is handed over to the thread pool of B2, and serviced appropriately.

Now note that

* The high B1 latencies are still propagated back towards end users, but *only the end users that are actually accessing B1*, i.e. users of C1 and C2.
* **Users of C3, accessing back-end B2, are unaffected.**


# Quick-Start Guide

This assumes that you already have a working web application with a `web.xml` file.  See also [Javadoc](http://signicat.github.io/hystrix-servlet/apidocs/).

## Initial Setup

Add a dependency towards `hystrix-servlet` to your `pom.xml`:

    <dependency>
        <groupId>com.signicat.hystrix</groupId>
        <artifactId>hystrix-servlet</artifactId>
        <version>1.0.11</version>
        <scope>compile</scope>
    </dependency>

The Servlets that you wish to execute within a separate thread pool (e.g. `SomethingServlet`), create a wrapper class
alongside the current Servlet class:

    package com.foo;
    import javax.servlet.annotation.WebServlet;
    import com.signicat.hystrix.servlet.AsyncWrapperServlet;

    @WebServlet(asyncSupported = true)
    public class AsyncSomethingServlet extends AsyncWrapperServlet {
        public AsyncSomethingServlet() {
            super(new SomethingServlet());
        }
    }

And configure the wrapper class inside `web.xml` instead of the original Servlet class:

    <servlet>
        <servlet-name>SomethingServlet</servlet-name>
        <display-name>Something Servlet</display-name>
        <servlet-class>com.foo.AsyncSomethingServlet</servlet-class>
    </servlet>

## Configuring Which Thread Pool to Execute Within

With the setup above, all requests towards `SomethingServlet` (wrapped by our new class `AsyncSomethingServlet`)
will execute in a thread pool separate from the Servlet container threads. This thread pool is called `default`.

Now, one might want to

1. Execute all requests towards `SomethingServlet` in thread pool `foo`, and execute all requests towards `SomewhatServlet` in thread pool `bar`.
1. Determine when the request is received by `SomethingServlet` which thread pool to run within.

Both are supported.

For the first case, quite simply let your `SomethingServlet` implement the interface [`com.signicat.hystrix.servlet.HystrixAwareServlet`](http://signicat.github.io/hystrix-servlet/apidocs/com/signicat/hystrix/servlet/HystrixAwareServlet.html):

    package com.foo;
    import com.signicat.hystrix.servlet.HystrixAwareServlet;

    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;

    public class SomethingServlet extends HttpServlet implements HystrixAwareServlet {

        /** The rest of SomethingServlet is here... */

        @Override
        public String getCommandGroupKey(HttpServletRequest request) {
            return "foo";
        }
    }

Note that this must be done to your original `SomethingServlet`, not our new wrapper class (which we called
`AsyncSomethingServlet`).

Obviously, do the same to `SomewhatServlet`, but let that return `"bar"`. Now, `SomethingServlet` will execute in
thread pool `foo`, while `SomewhatServlet` will execute in thread pool `bar`.

The solution for the second case is essentially the same as for the first, but I'm including it for completeness:


    package com.foo;
    import com.signicat.hystrix.servlet.AsyncWrapperServlet;
    import com.signicat.hystrix.servlet.HystrixAwareServlet;

    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;

    public class SomethingServlet extends HttpServlet implements HystrixAwareServlet {

        /** The rest of SomethingServlet is here... */

        @Override
        public String getCommandGroupKey(HttpServletRequest request) {
            final String method = request.getParameter("method");
            if (method == null || method.isEmpty()) {
                return AsyncWrapperServlet.DEFAULT_COMMAND_GROUP_KEY;
            }
            if (method.equals("this")) {
                return "blah";
            } else if (method.equals("that")) {
                return "bleh";
            }

            // Customize this to your liking. This example just checks a request parameter, you can do whatever you want.

            return AsyncWrapperServlet.DEFAULT_COMMAND_GROUP_KEY;
        }
    }


# Limitations

In real life, a Servlet will not only communicate with *one* back-end service, but several. If
FooServlet calls towards back-end services B1, B2 and B3, which thread pool should one run requests towards
FooServlet within? With Hystrix-Servlet, you have to "just" make a choice, which is not necessarily easy.

In cases like this, the partial overlaps of back-end access will mess up thread-pool isolation.

A better solution would be to push Hystrix further down the stack, and wrap the back-end communication
libraries themselves.

Hystrix allows setting various parameters when creating a `HystrixCommand`, but setting these is not
available through Hystrix-Servlet. The only knobs for use are the command group key (thread pool name, as shown above),
command timeout (in milliseconds), and thread pool size. The latter two are available as constructor arguments
to [`AsyncWrapperServlet`](http://signicat.github.io/hystrix-servlet/apidocs/com/signicat/hystrix/servlet/AsyncWrapperServlet.html).

A few words regarding timeout handling. Assume that an operation in a running Hystrix thread takes forever.
Hystrix-Servlet will let the Servlet container time things out first (i.e. use the timeout given as a constructor
argument to [`AsyncWrapperServlet`](http://signicat.github.io/hystrix-servlet/apidocs/com/signicat/hystrix/servlet/AsyncWrapperServlet.html),
or the default of 50 seconds). In this case [`AsyncWrapperServlet.onServletTimeout()`](http://signicat.github.io/hystrix-servlet/apidocs/com/signicat/hystrix/servlet/AsyncWrapperServlet.html#onServletTimeout-javax.servlet.AsyncEvent-)
is called, which by default returns a HTTP 504 to the end user (this method can be overridden, but make sure to read
the javadoc). After this *we do not want the Hystrix thread to tamper with the original `HttpServletRequest` or
`HttpServletResponse`*. This is accomplished by giving the Hystrix thread a wrapped request and response in the first
place, and when timeout happens, we poke the wrapper so that access to the original request and response is prevented.
This is suboptimally implemented and not waterproof. Suboptimal in the sense that all methods in
`TimeoutAwareHttpServletRequest` and `TimeoutAwareHttpServletResponse` are synchronized. Not waterproof in the sense
that objects *returned* from the `HttpServletRequest` or `HttpServletResponse` are *not* wrapped - so if something
somewhere has cached a reference to e.g. a `Writer` (returned from `HttpServletResponse.getWriter()`) early on,
this can still be (ab)used by the Hystrix thread post-timeout.


