Hystrix-Servlet
===============

A simple ?glue? between Hystrix and Servlet 3.0.

# What Problem Are We Solving

Assume the following setup (simplified):

* Clients (named C1, C2, ...) are tenants of a single Servlet container, running on a single JVM on a single physical node.
* Each client has a unique set of end users (named U1, U2, ...), accessing its service, which we host in said Servlet container.
* Each client has access to a set of back-end services (named B1, B2, ...). Note that these may overlap. Say, both client C1 and C2 allow their users to use back-end service B1, but only client C3 allows its users to use back-end service B2.
* We do not control the back-end services.
* We (obviously) do not control end users.
* The application running in the Servlet container is a straight-forward synchronous Java web application.

In most cases this works quite beautifully. When serving a request for an end user of, say, client C1, the following happens:

1. End user U1 wants to access client C1 from his browser.
2. An HTTP request is received by the Servlet container.
3. The container thread passes it to the Servlet.service() method.
  * This method will determine that the request is for client C1.
  * Some configuration is looked up.
  * It is determined (somehow) that this request will go towards back-end service B1.
  * B1 replies in time.
4. The response is returned to U1.

Note that in real life clients are protected by class-loader separation, BUT they DO share the same pool of threads from the
Servlet container. If a back-end service has an extremely high latency, **this will become a problem**.

1. Several end users want to access C1 or C2 from their browsers. Recall that both C1 and C2 go toward back-end service B1.
2. B1 is having trouble, with very high latencies.
3. As in the example above, container threads are used to service users of C1 and C2.
  * Due to the high latency of B1, these container threads are blocked for a very long time.
4. More users of C1 and C2 come along, many of the first users are re-trying their requests, and pretty soon *all Servlet container threads are blocked*.
5. Some end user wanting to access C3 comes along. Recall that client C3 doesn't even have access to back-end service B1 (which is having trouble), but is *only* using B2.
6. **There is essentially a denial-of-service for users of C3**.

Note that

* It can be argued that the high response times for users of C1 and C2 is ?acceptable?, i.e. we are just propagating the high latencies back towards users. It's not really our fault.
* The denial-of-service for users of C3 is *totally unacceptable*. These should be completely unaffected by the problems at B1.

# How Are We Solving This Problem

# Limitations

