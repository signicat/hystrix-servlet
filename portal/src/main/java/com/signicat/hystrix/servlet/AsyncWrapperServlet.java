package com.signicat.hystrix.servlet;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.contrib.servopublisher.HystrixServoMetricsPublisher;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.signicat.platform.log.DefaultLogger;
import com.signicat.platform.log.ThreadLocalLogContext;
import com.signicat.platform.log.TimeTracker;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rx.Observable;
import rx.Observer;

/**
 * @author Einar Rosenvinge &lt;einros@signicat.com&gt;
 */
@WebServlet(asyncSupported = true)
public class AsyncWrapperServlet extends HttpServlet {

    public static final String DEFAULT_COMMAND_GROUP_KEY = "default";
    private static final DefaultLogger log = new DefaultLogger(AsyncWrapperServlet.class);
    public  static final long DEFAULT_TIMEOUT = 50000L;
    private static final int HYSTRIX_ADDED_TIMEOUT_DELAY = 10000;  //we want the servlet container to time things out before Hystrix does
    public  static final int DEFAULT_CORE_POOL_SIZE = 100;
    private final HttpServlet wrappedServlet;
    private final long timeoutMillis;
    private final int corePoolSize;

    static {
        HystrixPlugins.getInstance().registerMetricsPublisher(HystrixServoMetricsPublisher.getInstance());
    }

    AsyncWrapperServlet(final HttpServlet wrappedServlet) {
        this(wrappedServlet, DEFAULT_TIMEOUT, DEFAULT_CORE_POOL_SIZE);
    }

    public AsyncWrapperServlet(final HttpServlet wrappedServlet, final long timeoutMillis, final int corePoolSize) {
        this.wrappedServlet = wrappedServlet;
        this.timeoutMillis = timeoutMillis;
        this.corePoolSize = corePoolSize;
    }

    @Override
    public void init() throws ServletException {
        wrappedServlet.init();
    }

    @Override
    public void destroy() {
        Hystrix.reset(5, TimeUnit.SECONDS);
        wrappedServlet.destroy();
    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(timeoutMillis);
        TimeoutAwareHttpServletRequest timeoutAwareHttpServletReq = new TimeoutAwareHttpServletRequest(req);
        TimeoutAwareHttpServletResponse timeoutAwareHttpServletResp = new TimeoutAwareHttpServletResponse(resp);
        asyncContext.addListener(new BaseServletAsyncListener(timeoutAwareHttpServletReq,
                                                              timeoutAwareHttpServletResp));

        onBeforeCommandSubmit();

        String threadLocalContextToSet = ThreadLocalLogContext.getContext();
        String threadLocalHttpSessionToSet = ThreadLocalLogContext.getHttpSession();
        TimeTracker threadLocalTimeTrackerToSet = ThreadLocalLogContext.getTimeTracker();
        String threadLocalTransactionIdToSet = ThreadLocalLogContext.getTransactionId();

        final String key = getCommandGroupKey(wrappedServlet, req);
        log.logTrace("Scheduling Hystrix command with key '" + key + "'");

        //double thread pool size for pool with magic name 'default'
        final int size = DEFAULT_COMMAND_GROUP_KEY.equals(key) ? (corePoolSize * 2) : corePoolSize;
        final int queueSize = corePoolSize * 4;
        final int queueRejectionSize = corePoolSize * 2;

        BaseServletCommand command =
                new BaseServletCommand(
                        HystrixCommand.Setter
                                .withGroupKey(
                                        HystrixCommandGroupKey.Factory.asKey(key))
                                .andCommandKey(HystrixCommandKey.Factory.asKey(key))
                                .andCommandPropertiesDefaults(
                                        HystrixCommandProperties.Setter()
                                                .withExecutionIsolationThreadTimeoutInMilliseconds(
                                                        (int) timeoutMillis + HYSTRIX_ADDED_TIMEOUT_DELAY)
                                                .withCircuitBreakerEnabled(false)
                                                .withExecutionIsolationStrategy(
                                                        HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                                .withFallbackEnabled(false))
                                .andThreadPoolPropertiesDefaults(
                                        HystrixThreadPoolProperties.Setter()
                                                .withCoreSize(size)
                                                .withMaxQueueSize(queueSize)
                                                .withQueueSizeRejectionThreshold(queueRejectionSize)),
                        timeoutAwareHttpServletReq,
                        timeoutAwareHttpServletResp,
                        threadLocalContextToSet,
                        threadLocalHttpSessionToSet,
                        threadLocalTimeTrackerToSet,
                        threadLocalTransactionIdToSet);

        Observable<Object> observable = command.observe();
        observable.subscribe(new BaseServletObserver(asyncContext));

        onAfterCommandSubmit();
    }

    private static String getCommandGroupKey(HttpServlet srv, HttpServletRequest req) {
        if (srv instanceof HystrixAwareServlet) {
            return ((HystrixAwareServlet) srv).getCommandGroupKey(req);
        }
        return DEFAULT_COMMAND_GROUP_KEY;
    }

    /**
     * Useful? for subclasses, but really mostly for testing. Be careful, exceptions thrown from here
     * will mess things up.
     */
    public void onBeforeCommandSubmit() {
    }

    /**
     * Useful? for subclasses, but really mostly for testing. Be careful, exceptions thrown from here
     * will mess things up.
     */
    public void onAfterCommandSubmit() {
    }

    public void servletComplete(AsyncEvent asyncEvent) throws IOException {
    }

    @SuppressWarnings({"EmptyCatchBlock", "ThrowableResultOfMethodCallIgnored"})
    public void servletTimeout(AsyncEvent asyncEvent) throws IOException {
        try {
            final String msg = "Timeout from async listener";
            if (asyncEvent.getThrowable() == null) {
                log.logError(msg);
            } else {
                log.logError(msg, asyncEvent.getThrowable());
            }
            ((HttpServletResponse) asyncEvent.getAsyncContext().getResponse())
                    .sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, msg);
        } catch (Exception e) {
        } finally {
            try {
                asyncEvent.getAsyncContext().complete();
            } catch (Exception e) {
            }
        }
    }

    @SuppressWarnings({"EmptyCatchBlock", "ThrowableResultOfMethodCallIgnored"})
    public void servletError(AsyncEvent asyncEvent) throws IOException {
        try {
            final String msg = "Error from async listener";
            if (asyncEvent.getThrowable() == null) {
                log.logError(msg);
            } else {
                log.logError(msg, asyncEvent.getThrowable());
            }
            ((HttpServletResponse) asyncEvent.getAsyncContext().getResponse())
                    .sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
        } catch (Exception e) {
        } finally {
            try {
                asyncEvent.getAsyncContext().complete();
            } catch (Exception e) {
            }
        }
    }

    @SuppressWarnings("EmptyCatchBlock")
    public void hystrixCompleted(AsyncContext asyncContext) {
        try {
            asyncContext.complete();
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("EmptyCatchBlock")
    public void hystrixError(AsyncContext asyncContext, Throwable throwable) {
        try {
            final HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
            final String msg = "Error from async observer";
            if (throwable == null) {
                log.logError(msg);
            } else {
                log.logError(msg, throwable);
            }
            if (throwable instanceof HystrixRuntimeException) {
                HystrixRuntimeException hre = (HystrixRuntimeException) throwable;
                switch (hre.getFailureType()) {
                    case TIMEOUT:
                        response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, msg);
                        break;
                    case COMMAND_EXCEPTION:
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
                        break;
                    default:
                        response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, msg);
                        break;
                }
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
            }
        } catch (Exception e) {
        }
        try {
            asyncContext.complete();
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("EmptyCatchBlock")
    public void hystrixNext(AsyncContext asyncContext, Object o) {
        try {
            asyncContext.complete();
        } catch (Exception e) {
        }
    }

    public class BaseServletAsyncListener implements AsyncListener {
        private final TimeoutAwareHttpServletRequest timeoutAwareHttpServletReq;
        private final TimeoutAwareHttpServletResponse timeoutAwareHttpServletResp;

        public BaseServletAsyncListener(TimeoutAwareHttpServletRequest timeoutAwareHttpServletReq,
                                        TimeoutAwareHttpServletResponse timeoutAwareHttpServletResp) {
            this.timeoutAwareHttpServletReq = timeoutAwareHttpServletReq;
            this.timeoutAwareHttpServletResp = timeoutAwareHttpServletResp;
        }

        @Override
        public void onComplete(AsyncEvent asyncEvent) throws IOException {
            timeoutAwareHttpServletReq.resetWrapped();
            timeoutAwareHttpServletResp.resetWrapped();
            servletComplete(asyncEvent);
        }

        @Override
        public void onTimeout(AsyncEvent asyncEvent) throws IOException {
            timeoutAwareHttpServletReq.resetWrapped();
            timeoutAwareHttpServletResp.resetWrapped();
            servletTimeout(asyncEvent);
        }

        @Override
        public void onError(AsyncEvent asyncEvent) throws IOException {
            timeoutAwareHttpServletReq.resetWrapped();
            timeoutAwareHttpServletResp.resetWrapped();
            servletError(asyncEvent);
        }

        @Override
        public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
        }
    }

    public class BaseServletCommand extends HystrixCommand<Object> {
        private final TimeoutAwareHttpServletRequest timeoutAwareHttpServletReq;
        private final TimeoutAwareHttpServletResponse timeoutAwareHttpServletResp;
        private final String threadLocalContextToSet;
        private final String threadLocalHttpSessionToSet;
        private final TimeTracker threadLocalTimeTrackerToSet;
        private final String threadLocalTransactionIdToSet;


        public BaseServletCommand(Setter setter,
                                  TimeoutAwareHttpServletRequest timeoutAwareHttpServletReq,
                                  TimeoutAwareHttpServletResponse timeoutAwareHttpServletResp,
                                  String threadLocalContextToSet, String threadLocalHttpSessionToSet,
                                  TimeTracker threadLocalTimeTrackerToSet, String threadLocalTransactionIdToSet) {
            super(setter);
            this.timeoutAwareHttpServletReq = timeoutAwareHttpServletReq;
            this.timeoutAwareHttpServletResp = timeoutAwareHttpServletResp;
            this.threadLocalContextToSet = threadLocalContextToSet;
            this.threadLocalHttpSessionToSet = threadLocalHttpSessionToSet;
            this.threadLocalTimeTrackerToSet = threadLocalTimeTrackerToSet;
            this.threadLocalTransactionIdToSet = threadLocalTransactionIdToSet;
        }

        @Override
        protected Object run() throws Exception {
            try {
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

                wrappedServlet.service(timeoutAwareHttpServletReq, timeoutAwareHttpServletResp);
            } finally {
                ThreadLocalLogContext.cleanup();
            }
            return new Object();
        }
    }

    public class BaseServletObserver implements Observer<Object> {
        private final AsyncContext asyncContext;

        public BaseServletObserver(AsyncContext asyncContext) {
            this.asyncContext = asyncContext;
        }

        @Override
        public void onCompleted() {
            hystrixCompleted(asyncContext);
        }

        @Override
        public void onError(Throwable throwable) {
            hystrixError(asyncContext, throwable);
        }

        @Override
        public void onNext(Object o) {
            hystrixNext(asyncContext, o);
        }
    }

}
