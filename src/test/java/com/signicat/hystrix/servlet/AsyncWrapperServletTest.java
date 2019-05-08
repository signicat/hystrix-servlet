/*
 * Copyright (C) 2015 Signicat AS
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.signicat.hystrix.servlet;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Test;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Einar Rosenvinge &lt;einarmr@gmail.com&gt;
 */
public class AsyncWrapperServletTest {

    @Test
    public void require_That_Servlet_Timeout_Kicks_In_But_Hystrix_Timeout_Does_Not_Kick_In() throws Exception {
        CountDownLatch servletTimeout = new CountDownLatch(1);

        final AsyncTestServlet servlet = new AsyncTestServlet(new CountDownLatch(1),
                                                              servletTimeout,
                                                              new CountDownLatch(1),
                                                              new CountDownLatch(1),
                                                              new CountDownLatch(1),
                                                              new CountDownLatch(1),
                                                              null,
                                                              new TimeoutServlet(servletTimeout), 1000L);
        try (TestServer server = new TestServer(0, servlet)) {
            server.start();
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet("http://localhost:" + server.getPort() + "/bananarama");
                try (CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {
                    StatusLine statusLine = httpResponse.getStatusLine();
                    assertThat(statusLine.getStatusCode(), equalTo(504));
                    assertThat(statusLine.getReasonPhrase(), equalTo("Timeout from async listener"));
                    EntityUtils.consume(httpResponse.getEntity());
                    assertThat(servlet.servletTimeout.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(servlet.servletComplete.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(servlet.servletError.getCount(), equalTo(1L));
                    assertThat(servlet.hystrixError.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(servlet.hystrixCompleted.getCount(), equalTo(1L));
                    assertThat(servlet.hystrixNext.getCount(), equalTo(1L));
                }
            }
        }
    }

    @Test
    public void require_That_Servlet_Timeout_Kicks_In_And_Hystrix_Timeout_Also_Kicks_In() throws Exception {
        CountDownLatch servletTimeout = new CountDownLatch(1);
        CountDownLatch hystrixError = new CountDownLatch(1);

        final AsyncTestServlet servlet = new AsyncTestServlet(new CountDownLatch(1),
                                                              servletTimeout,
                                                              new CountDownLatch(1),
                                                              new CountDownLatch(1),
                                                              hystrixError,
                                                              new CountDownLatch(1),
                                                              null,
                                                              new TimeoutServlet(servletTimeout, hystrixError), 1000L);
        try (TestServer server = new TestServer(0, servlet)) {
            server.start();
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet("http://localhost:" + server.getPort() + "/bananarama");
                try (CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {
                    StatusLine statusLine = httpResponse.getStatusLine();
                    assertThat(statusLine.getStatusCode(), equalTo(504));
                    assertThat(statusLine.getReasonPhrase(), equalTo("Timeout from async listener"));
                    EntityUtils.consume(httpResponse.getEntity());
                    assertThat(servlet.servletTimeout.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(servlet.servletComplete.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(servlet.servletError.getCount(), equalTo(1L));
                    assertThat(servlet.hystrixError.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(servlet.hystrixCompleted.getCount(), equalTo(1L));
                    assertThat(servlet.hystrixNext.getCount(), equalTo(1L));
                }
            }
        }
    }

    @Test
    public void require_That_RuntimeException_In_Wrapped_Servlet_Is_Handled_Correctly() throws Exception {
        final AsyncTestServlet servlet = new AsyncTestServlet(
                new ExceptionThrowingServlet(new RuntimeException("Bananarama failed")), 10 * 1000L);
        try (TestServer server = new TestServer(0, servlet)) {
            server.start();
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet("http://localhost:" + server.getPort() + "/bananarama");
                try (CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {
                    StatusLine statusLine = httpResponse.getStatusLine();
                    assertThat(statusLine.getStatusCode(), equalTo(500));
                    assertThat(statusLine.getReasonPhrase(), equalTo("Error from async observer"));
                    EntityUtils.consume(httpResponse.getEntity());
                    assertThat(servlet.hystrixError.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(servlet.hystrixCompleted.getCount(), equalTo(1L));
                    assertThat(servlet.hystrixNext.getCount(), equalTo(1L));
                    assertThat(servlet.servletComplete.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(servlet.servletTimeout.getCount(), equalTo(1L));
                    assertThat(servlet.servletError.getCount(), equalTo(1L));
                }
            }
        }
    }

    @Test
    public void require_That_IOException_In_Wrapped_Servlet_Is_Handled_Correctly() throws Exception {
        final AsyncTestServlet servlet = new AsyncTestServlet(
                new ExceptionThrowingServlet(new IOException("Bananarama IO failed")), 10 * 1000L);
        try (TestServer server = new TestServer(0, servlet)) {
            server.start();
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet("http://localhost:" + server.getPort() + "/bananarama");
                try (CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {
                    StatusLine statusLine = httpResponse.getStatusLine();
                    assertThat(statusLine.getStatusCode(), equalTo(500));
                    assertThat(statusLine.getReasonPhrase(), equalTo("Error from async observer"));
                    EntityUtils.consume(httpResponse.getEntity());
                    assertThat(servlet.hystrixError.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(servlet.hystrixCompleted.getCount(), equalTo(1L));
                    assertThat(servlet.hystrixNext.getCount(), equalTo(1L));
                    assertThat(servlet.servletComplete.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(servlet.servletTimeout.getCount(), equalTo(1L));
                    assertThat(servlet.servletError.getCount(), equalTo(1L));
                }
            }
        }
    }

    @Test
    public void require_That_Exception_In_Async_Wrapper_Servlet_Is_Handled_Correctly() throws Exception {
        final AsyncTestServlet servlet = new AsyncTestServlet(new CountDownLatch(1),
                                                              new CountDownLatch(1),
                                                              new CountDownLatch(1),
                                                              new CountDownLatch(1),
                                                              new CountDownLatch(1),
                                                              new CountDownLatch(1),
                                                              new RuntimeException("Exception in wrapper"),
                                                              new ExceptionThrowingServlet(null), 10 * 1000L);
        try (TestServer server = new TestServer(0, servlet)) {
            server.start();
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet("http://localhost:" + server.getPort() + "/bananarama");
                try (CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {
                    StatusLine statusLine = httpResponse.getStatusLine();
                    assertThat(statusLine.getStatusCode(), equalTo(500));
                    //assertThat(statusLine.getReasonPhrase(), equalTo("Server Error"));  //apparently from Jetty
                    EntityUtils.consume(httpResponse.getEntity());
                    assertThat(servlet.servletComplete.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(servlet.servletTimeout.getCount(), equalTo(1L));
                    assertThat(servlet.servletError.getCount(), equalTo(1L));
                    assertThat(servlet.hystrixError.getCount(), equalTo(1L));
                    assertThat(servlet.hystrixCompleted.getCount(), equalTo(1L));
                    assertThat(servlet.hystrixNext.getCount(), equalTo(1L));
                }
            }
        }
    }

    @Test
    public void require_That_Init_Calls_Init() throws ServletException {
        MockServlet mockServlet = new MockServlet();
        AsyncWrapperServlet asyncServlet = new AsyncWrapperServlet(mockServlet);

        assertThat(mockServlet.inited, is(false));
        asyncServlet.init();
        assertThat(mockServlet.inited, is(true));
    }

    @Test
    public void require_That_Destroy_Calls_Destroy() {
        MockServlet mockServlet = new MockServlet();
        AsyncWrapperServlet asyncServlet = new AsyncWrapperServlet(mockServlet);

        assertThat(mockServlet.destroyed, is(false));
        asyncServlet.destroy();
        assertThat(mockServlet.destroyed, is(true));
    }

    @Test
    public void require_That_Service_Calls_Service_Asynchronously()
            throws ServletException, IOException, InterruptedException {
        MockServlet mockServlet = new MockServlet();
        AsyncWrapperServlet asyncServlet = new AsyncWrapperServlet(mockServlet);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest(response);

        assertThat(request.isAsyncStarted(), is(false));
        asyncServlet.service(request, response);

        assertThat(request.isAsyncStarted(), is(true));
        assertThat(request.getAsyncContext().getTimeout(), equalTo(50000L));
        assertThat(mockServlet.servicedOnce.await(60, TimeUnit.SECONDS), is(true));
    }

    public static class AsyncTestServlet extends AsyncWrapperServlet {

        private final CountDownLatch servletComplete;
        private final CountDownLatch servletTimeout;
        private final CountDownLatch servletError;
        private final CountDownLatch hystrixCompleted;
        private final CountDownLatch hystrixError;
        private final CountDownLatch hystrixNext;
        private final Exception exceptionToThrow;

        public AsyncTestServlet(HttpServlet hystrixAwareServlet, long timeoutMillis) {
            this(new CountDownLatch(1),
                 new CountDownLatch(1),
                 new CountDownLatch(1),
                 new CountDownLatch(1),
                 new CountDownLatch(1),
                 new CountDownLatch(1),
                 null,
                 hystrixAwareServlet,
                 timeoutMillis);
        }

        public AsyncTestServlet(CountDownLatch servletComplete,
                                CountDownLatch servletTimeout,
                                CountDownLatch servletError,
                                CountDownLatch hystrixCompleted,
                                CountDownLatch hystrixError,
                                CountDownLatch hystrixNext,
                                Exception exceptionToThrow,
                                HttpServlet hystrixAwareServlet,
                                final long timeout) {
            super(hystrixAwareServlet, timeout, AsyncWrapperServlet.DEFAULT_CORE_POOL_SIZE);
            this.servletComplete = servletComplete;
            this.servletTimeout = servletTimeout;
            this.servletError = servletError;
            this.hystrixCompleted = hystrixCompleted;
            this.hystrixError = hystrixError;
            this.hystrixNext = hystrixNext;
            this.exceptionToThrow = exceptionToThrow;
        }

        @Override
        protected Runnable onBeforeCommandSubmit() {
            if (exceptionToThrow == null) {
                return super.onBeforeCommandSubmit();
            }
            if (exceptionToThrow instanceof RuntimeException) {
                throw (RuntimeException) exceptionToThrow;
            } else {
                throw new RuntimeException(exceptionToThrow);
            }
        }

        @Override
        public void onServletCompleted(AsyncEvent asyncEvent) throws IOException {
            System.err.println("onServletCompleted()");
            servletComplete.countDown();
            super.onServletCompleted(asyncEvent);
        }

        @Override
        public void onServletTimeout(AsyncEvent asyncEvent) throws IOException {
            System.err.println("onServletTimeout()");
            servletTimeout.countDown();
            super.onServletTimeout(asyncEvent);
        }

        @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
        @Override
        public void onServletError(AsyncEvent asyncEvent) throws IOException {
            System.err.println("onServletError()");
            if (asyncEvent.getThrowable() != null) {
                asyncEvent.getThrowable().printStackTrace(System.err);
            }
            servletError.countDown();
            super.onServletError(asyncEvent);
        }

        @Override
        public void onHystrixCompleted(AsyncContext asyncContext) {
            System.err.println("onHystrixCompleted()");
            hystrixCompleted.countDown();
            super.onHystrixCompleted(asyncContext);
        }

        @Override
        public void onHystrixError(AsyncContext asyncContext, Throwable throwable) {
            System.err.println("onHystrixError()");
            throwable.printStackTrace(System.err);
            hystrixError.countDown();
            super.onHystrixError(asyncContext, throwable);
        }

        @Override
        public void onHystrixNext(AsyncContext asyncContext, Object o) {
            System.err.println("onHystrixNext()");
            hystrixNext.countDown();
            super.onHystrixNext(asyncContext, o);
        }
    }

    public static class ExceptionThrowingServlet extends HttpServlet {

        private final Exception exceptionToThrow;

        public ExceptionThrowingServlet(Exception exceptionToThrow) {
            this.exceptionToThrow = exceptionToThrow;
        }

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if (exceptionToThrow == null) {
                return;
            }
            if (exceptionToThrow instanceof ServletException) {
                throw (ServletException) exceptionToThrow;
            } else if (exceptionToThrow instanceof IOException) {
                throw (IOException) exceptionToThrow;
            } else if (exceptionToThrow instanceof RuntimeException) {
                throw (RuntimeException) exceptionToThrow;
            } else {
                throw new RuntimeException(exceptionToThrow);
            }
        }
    }

    public static class TimeoutServlet extends HttpServlet {

        private final CountDownLatch[] latchesToWaitForUntilReturning;

        public TimeoutServlet(final CountDownLatch... latchesToWaitForUntilReturning) {
            this.latchesToWaitForUntilReturning = latchesToWaitForUntilReturning;
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            for (CountDownLatch countDownLatch : latchesToWaitForUntilReturning) {
                try {
                    countDownLatch.await(60, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    //this should not be interrupted here
                    throw new RuntimeException(e);
                }
            }
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resp.setStatus(200, "Bananarama");
            resp.getWriter().write("Late response from servlet.");
            resp.getWriter().flush();
            resp.getWriter().close();
        }
    }

    public static class MockServlet extends HttpServlet {

        private boolean inited = false;
        private boolean destroyed = false;
        CountDownLatch servicedOnce = new CountDownLatch(1);

        @Override
        public void init() throws ServletException {
            inited = true;
        }

        @Override
        public void destroy() {
            destroyed = true;
        }

        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response)
                throws IOException, ServletException {
            servicedOnce.countDown();
        }
    }

    private static class TestServer implements AutoCloseable {

        private final Server server;

        private TestServer(final int port, HttpServlet servlet) {
            server = new Server(port);
            ServletHandler handler = new ServletHandler();
            server.setHandler(handler);
            ServletHolder servletHolder = new ServletHolder(servlet);
            handler.addServletWithMapping(servletHolder, "/*");
        }

        public void start() throws Exception {
            server.start();
        }

        public int getPort() {
            return ((ServerConnector) server.getConnectors()[0]).getLocalPort();
        }

        @Override
        public void close() {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
