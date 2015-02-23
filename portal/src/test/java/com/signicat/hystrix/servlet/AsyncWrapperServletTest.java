package com.signicat.hystrix.servlet;

import com.signicat.hystrix.servlet.AsyncWrapperServlet;
import com.signicat.hystrix.servlet.HystrixAwareServlet;
import com.signicat.platform.log.ThreadLocalLogContext;
import com.signicat.platform.log.TimeTracker;

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

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ksc.portal.MockHttpServletRequest;
import ksc.portal.MockHttpServletResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Einar Rosenvinge &lt;einros@signicat.com&gt;
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
        TestServer server = new TestServer(0, servlet);
        try {
            server.start();
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpGet = new HttpGet("http://localhost:" + server.getPort() + "/bananarama");
                CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
                try {
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
                } finally {
                    httpResponse.close();
                }
            } finally {
                httpclient.close();
            }
        } finally {
            server.close();
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
        TestServer server = new TestServer(0, servlet);
        try {
            server.start();
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpGet = new HttpGet("http://localhost:" + server.getPort() + "/bananarama");
                CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
                try {
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
                } finally {
                    httpResponse.close();
                }
            } finally {
                httpclient.close();
            }
        } finally {
            server.close();
        }
    }

    @Test
    public void require_That_RuntimeException_In_Wrapped_Servlet_Is_Handled_Correctly() throws Exception {
        final AsyncTestServlet servlet = new AsyncTestServlet(new ExceptionThrowingServlet(new RuntimeException("Bananarama failed")), 10 * 1000L);
        TestServer server = new TestServer(0, servlet);
        try {
            server.start();
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpGet = new HttpGet("http://localhost:" + server.getPort() + "/bananarama");
                CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
                try {
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
                } finally {
                    httpResponse.close();
                }
            } finally {
                httpclient.close();
            }
        } finally {
            server.close();
        }
    }

    @Test
    public void require_That_IOException_In_Wrapped_Servlet_Is_Handled_Correctly() throws Exception {
        final AsyncTestServlet servlet = new AsyncTestServlet(new ExceptionThrowingServlet(new IOException("Bananarama IO failed")), 10 * 1000L);
        TestServer server = new TestServer(0, servlet);
        try {
            server.start();
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpGet = new HttpGet("http://localhost:" + server.getPort() + "/bananarama");
                CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
                try {
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
                } finally {
                    httpResponse.close();
                }
            } finally {
                httpclient.close();
            }
        } finally {
            server.close();
        }
    }

    @Test
    public void require_That_Thread_Local_State_Is_Kept_For_Hystrix_Thread() throws Exception {
        final String context = "Kontext";
        final String httpSession = "httpSesjon";
        final TimeTracker timeTracker = new TimeTracker(123L);
        final String transactionId = "transaktionID";

        final ThreadLocalServlet threadLocalServlet = new ThreadLocalServlet();
        final AsyncThreadLocalWrapperServlet servlet = new AsyncThreadLocalWrapperServlet(context,
                                                                                          httpSession,
                                                                                          timeTracker,
                                                                                          transactionId,
                                                                                          threadLocalServlet,
                                                                                          10 * 1000L);
        TestServer server = new TestServer(0, servlet);
        try {
            server.start();
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpGet = new HttpGet("http://localhost:" + server.getPort() + "/bananarama");
                CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
                try {
                    StatusLine statusLine = httpResponse.getStatusLine();
                    assertThat(statusLine.getStatusCode(), equalTo(200));
                    assertThat(statusLine.getReasonPhrase(), equalTo("OK"));
                    EntityUtils.consume(httpResponse.getEntity());
                    assertThat(threadLocalServlet.foundContext.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(threadLocalServlet.foundHttpSession.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(threadLocalServlet.foundTimeTracker.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(threadLocalServlet.foundTransactionId.await(60, TimeUnit.SECONDS), is(true));
                    assertThat(threadLocalServlet.context, equalTo(context));
                    assertThat(threadLocalServlet.httpSession, equalTo(httpSession));
                    assertThat(threadLocalServlet.timeTracker, not(nullValue()));
                    assertThat(threadLocalServlet.transactionId, equalTo(transactionId));
                } finally {
                    httpResponse.close();
                }
            } finally {
                httpclient.close();
            }
        } finally {
            server.close();
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
        TestServer server = new TestServer(0, servlet);
        try {
            server.start();
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpGet = new HttpGet("http://localhost:" + server.getPort() + "/bananarama");
                CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
                try {
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
                } finally {
                    httpResponse.close();
                }
            } finally {
                httpclient.close();
            }
        } finally {
            server.close();
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

    public static class AsyncThreadLocalWrapperServlet extends AsyncWrapperServlet {
        private final String threadLocalContextToSet;
        private final String threadLocalHttpSessionToSet;
        private final TimeTracker threadLocalTimeTrackerToSet;
        private final String threadLocalTransactionIdToSet;

        public AsyncThreadLocalWrapperServlet(
                String threadLocalContextToSet, String threadLocalHttpSessionToSet,
                TimeTracker threadLocalTimeTrackerToSet, String threadLocalTransactionIdToSet,
                HystrixAwareServlet hystrixAwareServlet,
                final long timeout) {
            super(hystrixAwareServlet, timeout, AsyncWrapperServlet.DEFAULT_CORE_POOL_SIZE);
            this.threadLocalContextToSet = threadLocalContextToSet;
            this.threadLocalHttpSessionToSet = threadLocalHttpSessionToSet;
            this.threadLocalTimeTrackerToSet = threadLocalTimeTrackerToSet;
            this.threadLocalTransactionIdToSet = threadLocalTransactionIdToSet;
        }

        @Override
        public void onBeforeCommandSubmit() {
            if (threadLocalContextToSet != null) {
                ThreadLocalLogContext.setContext(threadLocalContextToSet);
            }
            if (threadLocalHttpSessionToSet != null) {
                ThreadLocalLogContext.setHttpSession(threadLocalHttpSessionToSet);
            }
            if (threadLocalTimeTrackerToSet != null) {
                ThreadLocalLogContext.createNewTimeTracker();
            }
            if (threadLocalTransactionIdToSet != null) {
                ThreadLocalLogContext.setTransactionId(threadLocalTransactionIdToSet);
            }
        }

        @Override
        public void onAfterCommandSubmit() {
            ThreadLocalLogContext.cleanup();
        }
    }

    public static class ThreadLocalServlet extends TestHystrixAwareServlet {
        private final CountDownLatch foundContext = new CountDownLatch(1);
        private final CountDownLatch foundHttpSession = new CountDownLatch(1);
        private final CountDownLatch foundTimeTracker = new CountDownLatch(1);
        private final CountDownLatch foundTransactionId = new CountDownLatch(1);
        private volatile String context;
        private volatile String httpSession;
        private volatile TimeTracker timeTracker;
        private volatile String transactionId;

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if (ThreadLocalLogContext.getContext() != null) {
                foundContext.countDown();
                context = ThreadLocalLogContext.getContext();
            }
            if (ThreadLocalLogContext.getHttpSession() != null) {
                foundHttpSession.countDown();
                httpSession = ThreadLocalLogContext.getHttpSession();
            }
            if (ThreadLocalLogContext.getTimeTracker() != null) {
                foundTimeTracker.countDown();
                timeTracker = ThreadLocalLogContext.getTimeTracker();
            }
            if (ThreadLocalLogContext.getTransactionId() != null) {
                foundTransactionId.countDown();
                transactionId = ThreadLocalLogContext.getTransactionId();
            }
        }
    }

    public static class AsyncTestServlet extends AsyncWrapperServlet {
        private final CountDownLatch servletComplete;
        private final CountDownLatch servletTimeout;
        private final CountDownLatch servletError;
        private final CountDownLatch hystrixCompleted;
        private final CountDownLatch hystrixError;
        private final CountDownLatch hystrixNext;
        private final Exception exceptionToThrow;

        public AsyncTestServlet(HystrixAwareServlet hystrixAwareServlet, long timeoutMillis) {
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
                                HystrixAwareServlet hystrixAwareServlet,
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
        public void onBeforeCommandSubmit() {
            if (exceptionToThrow == null) {
                return;
            }
            if (exceptionToThrow instanceof RuntimeException) {
                throw (RuntimeException) exceptionToThrow;
            } else {
                throw new RuntimeException(exceptionToThrow);
            }
        }

        @Override
        public void servletComplete(AsyncEvent asyncEvent) throws IOException {
            System.err.println("servletComplete()");
            servletComplete.countDown();
            super.servletComplete(asyncEvent);
        }

        @Override
        public void servletTimeout(AsyncEvent asyncEvent) throws IOException {
            System.err.println("servletTimeout()");
            servletTimeout.countDown();
            super.servletTimeout(asyncEvent);
        }

        @Override
        public void servletError(AsyncEvent asyncEvent) throws IOException {
            System.err.println("servletError()");
            if (asyncEvent.getThrowable() != null) {
                asyncEvent.getThrowable().printStackTrace(System.err);
            }
            servletError.countDown();
            super.servletError(asyncEvent);
        }

        @Override
        public void hystrixCompleted(AsyncContext asyncContext) {
            System.err.println("hystrixCompleted()");
            hystrixCompleted.countDown();
            super.hystrixCompleted(asyncContext);
        }

        @Override
        public void hystrixError(AsyncContext asyncContext, Throwable throwable) {
            System.err.println("hystrixError()");
            throwable.printStackTrace(System.err);
            hystrixError.countDown();
            super.hystrixError(asyncContext, throwable);
        }

        @Override
        public void hystrixNext(AsyncContext asyncContext, Object o) {
            System.err.println("hystrixNext()");
            hystrixNext.countDown();
            super.hystrixNext(asyncContext, o);
        }
    }
    
    public static class ExceptionThrowingServlet extends TestHystrixAwareServlet {
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

    public static class TimeoutServlet extends TestHystrixAwareServlet {
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

    public static class MockServlet extends TestHystrixAwareServlet {
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

    public static class TestServer implements Closeable {  //TODO: AutoCloseable
        private final Server server;

        public TestServer(final int port, HttpServlet servlet) {
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
            return ((ServerConnector)server.getConnectors()[0]).getLocalPort();
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

    public static class TestHystrixAwareServlet extends HystrixAwareServlet {
        @Override
        public String getCommandGroupKey(HttpServletRequest request) {
            return AsyncWrapperServlet.DEFAULT_COMMAND_GROUP_KEY;
        }
    }
}
