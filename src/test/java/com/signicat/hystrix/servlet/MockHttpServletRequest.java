package com.signicat.hystrix.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncListener;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

/**
 * @author Einar Rosenvinge &lt;einros@signicat.com&gt;
 */
public class MockHttpServletRequest implements HttpServletRequest {

    private final MockHttpServletResponse response;
    private MockAsyncContext asyncContext = null;
    private final Map<String, String> parameters;
    private String pathInfo;

    final CountDownLatch getAuthTypeCalled = new CountDownLatch(1);
    final CountDownLatch getCookiesCalled = new CountDownLatch(1);
    final CountDownLatch getDateHeaderCalled = new CountDownLatch(1);
    final CountDownLatch getHeaderCalled = new CountDownLatch(1);
    final CountDownLatch getHeadersCalled = new CountDownLatch(1);
    final CountDownLatch getHeaderNamesCalled = new CountDownLatch(1);
    final CountDownLatch getIntHeaderCalled = new CountDownLatch(1);
    final CountDownLatch getMethodCalled = new CountDownLatch(1);
    final CountDownLatch getPathInfoCalled = new CountDownLatch(1);
    final CountDownLatch setPathInfoCalled = new CountDownLatch(1);
    final CountDownLatch getPathTranslatedCalled = new CountDownLatch(1);
    final CountDownLatch getContextPathCalled = new CountDownLatch(1);
    final CountDownLatch getQueryStringCalled = new CountDownLatch(1);
    final CountDownLatch getRemoteUserCalled = new CountDownLatch(1);
    final CountDownLatch isUserInRoleCalled = new CountDownLatch(1);
    final CountDownLatch getUserPrincipalCalled = new CountDownLatch(1);
    final CountDownLatch getRequestedSessionIdCalled = new CountDownLatch(1);
    final CountDownLatch getRequestURICalled = new CountDownLatch(1);
    final CountDownLatch getRequestURLCalled = new CountDownLatch(1);
    final CountDownLatch getServletPathCalled = new CountDownLatch(1);
    final CountDownLatch getSessionCalledNoParams = new CountDownLatch(1);
    final CountDownLatch getSessionCalledBooleanParam = new CountDownLatch(1);
    final CountDownLatch changeSessionIdCalled = new CountDownLatch(1);
    final CountDownLatch isRequestedSessionIdValidCalled = new CountDownLatch(1);
    final CountDownLatch isRequestedSessionIdFromCookieCalled = new CountDownLatch(1);
    final CountDownLatch isRequestedSessionIdFromURLCalled = new CountDownLatch(1);
    final CountDownLatch isRequestedSessionIdFromUrlCalled = new CountDownLatch(1);
    final CountDownLatch authenticateCalled = new CountDownLatch(1);
    final CountDownLatch loginCalled = new CountDownLatch(1);
    final CountDownLatch logoutCalled = new CountDownLatch(1);
    final CountDownLatch getPartsCalled = new CountDownLatch(1);
    final CountDownLatch getPartCalled = new CountDownLatch(1);
    final CountDownLatch upgradeCalled = new CountDownLatch(1);
    final CountDownLatch getAttributeCalled = new CountDownLatch(1);
    final CountDownLatch getAttributeNamesCalled = new CountDownLatch(1);
    final CountDownLatch getCharacterEncodingCalled = new CountDownLatch(1);
    final CountDownLatch setCharacterEncodingCalled = new CountDownLatch(1);
    final CountDownLatch getContentLengthCalled = new CountDownLatch(1);
    final CountDownLatch getContentLengthLongCalled = new CountDownLatch(1);
    final CountDownLatch getContentTypeCalled = new CountDownLatch(1);
    final CountDownLatch getInputStreamCalled = new CountDownLatch(1);
    final CountDownLatch getParameterCalled = new CountDownLatch(1);
    final CountDownLatch getParameterNamesCalled = new CountDownLatch(1);
    final CountDownLatch getParameterValuesCalled = new CountDownLatch(1);
    final CountDownLatch getParameterMapCalled = new CountDownLatch(1);
    final CountDownLatch getProtocolCalled = new CountDownLatch(1);
    final CountDownLatch getSchemeCalled = new CountDownLatch(1);
    final CountDownLatch getServerNameCalled = new CountDownLatch(1);
    final CountDownLatch getServerPortCalled = new CountDownLatch(1);
    final CountDownLatch getReaderCalled = new CountDownLatch(1);
    final CountDownLatch getRemoteAddrCalled = new CountDownLatch(1);
    final CountDownLatch getRemoteHostCalled = new CountDownLatch(1);
    final CountDownLatch setAttributeCalled = new CountDownLatch(1);
    final CountDownLatch removeAttributeCalled = new CountDownLatch(1);
    final CountDownLatch getLocaleCalled = new CountDownLatch(1);
    final CountDownLatch getLocalesCalled = new CountDownLatch(1);
    final CountDownLatch isSecureCalled = new CountDownLatch(1);
    final CountDownLatch getRequestDispatcherCalled = new CountDownLatch(1);
    final CountDownLatch getRealPathCalled = new CountDownLatch(1);
    final CountDownLatch getRemotePortCalled = new CountDownLatch(1);
    final CountDownLatch getLocalNameCalled = new CountDownLatch(1);
    final CountDownLatch getLocalAddrCalled = new CountDownLatch(1);
    final CountDownLatch getLocalPortCalled = new CountDownLatch(1);
    final CountDownLatch getServletContextCalled = new CountDownLatch(1);
    final CountDownLatch startAsyncCalledNoParams = new CountDownLatch(1);
    final CountDownLatch startAsyncCalledTwoParams = new CountDownLatch(1);
    final CountDownLatch isAsyncStartedCalled = new CountDownLatch(1);
    final CountDownLatch isAsyncSupportedCalled = new CountDownLatch(1);
    final CountDownLatch getAsyncContextCalled = new CountDownLatch(1);
    final CountDownLatch getDispatcherTypeCalled = new CountDownLatch(1);

    public MockHttpServletRequest(MockHttpServletResponse response) {
        this.response = response;
        this.parameters = new HashMap<>();
    }

    public MockHttpServletRequest(Map<String, String> parameters) {
        this.response = new MockHttpServletResponse();
        this.parameters = parameters;
    }

    @Override
    public String getAuthType() {
        getAuthTypeCalled.countDown();
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        getCookiesCalled.countDown();
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String s) {
        getDateHeaderCalled.countDown();
        return 0;
    }

    @Override
    public String getHeader(String s) {
        getHeaderCalled.countDown();
        return null;
    }

    @Override
    public Enumeration<String> getHeaders(String s) {
        getHeadersCalled.countDown();
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        getHeaderNamesCalled.countDown();
        return null;
    }

    @Override
    public int getIntHeader(String s) {
        getIntHeaderCalled.countDown();
        return 0;
    }

    @Override
    public String getMethod() {
        getMethodCalled.countDown();
        return null;
    }

    @Override
    public String getPathInfo() {
        getPathInfoCalled.countDown();
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        setPathInfoCalled.countDown();
        this.pathInfo = pathInfo;
    }

    @Override
    public String getPathTranslated() {
        getPathTranslatedCalled.countDown();
        return null;
    }

    @Override
    public String getContextPath() {
        getContextPathCalled.countDown();
        return null;
    }

    @Override
    public String getQueryString() {
        getQueryStringCalled.countDown();
        return null;
    }

    @Override
    public String getRemoteUser() {
        getRemoteUserCalled.countDown();
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        isUserInRoleCalled.countDown();
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        getUserPrincipalCalled.countDown();
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        getRequestedSessionIdCalled.countDown();
        return null;
    }

    @Override
    public String getRequestURI() {
        getRequestURICalled.countDown();
        return null;
    }

    @Override
    public StringBuffer getRequestURL() {
        getRequestURLCalled.countDown();
        return null;
    }

    @Override
    public String getServletPath() {
        getServletPathCalled.countDown();
        return "foopath";
    }

    @Override
    public HttpSession getSession(boolean b) {
        getSessionCalledBooleanParam.countDown();
        return null;
    }

    @Override
    public HttpSession getSession() {
        getSessionCalledNoParams.countDown();
        return null;
    }

    @Override
    public String changeSessionId() {
        changeSessionIdCalled.countDown();
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        isRequestedSessionIdValidCalled.countDown();
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        isRequestedSessionIdFromCookieCalled.countDown();
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        isRequestedSessionIdFromURLCalled.countDown();
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        isRequestedSessionIdFromUrlCalled.countDown();
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
        authenticateCalled.countDown();
        return false;
    }

    @Override
    public void login(String s, String s1) throws ServletException {
        loginCalled.countDown();
    }

    @Override
    public void logout() throws ServletException {
        logoutCalled.countDown();
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        getPartsCalled.countDown();
        return null;
    }

    @Override
    public Part getPart(String s) throws IOException, ServletException {
        getPartCalled.countDown();
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
        upgradeCalled.countDown();
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        getAttributeCalled.countDown();
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        getAttributeNamesCalled.countDown();
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        getCharacterEncodingCalled.countDown();
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
        setCharacterEncodingCalled.countDown();
    }

    @Override
    public int getContentLength() {
        getContentLengthCalled.countDown();
        return 0;
    }

    @Override
    public long getContentLengthLong() {
        getContentLengthLongCalled.countDown();
        return 0;
    }

    @Override
    public String getContentType() {
        getContentTypeCalled.countDown();
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        getInputStreamCalled.countDown();
        return null;
    }

    @Override
    public String getParameter(String s) {
        getParameterCalled.countDown();
        return parameters.get(s);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        getParameterNamesCalled.countDown();
        return Collections.enumeration(parameters.keySet());
    }

    @Override
    public String[] getParameterValues(String s) {
        getParameterValuesCalled.countDown();
        return parameters.values().toArray(new String[parameters.values().size()]);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        getParameterMapCalled.countDown();
        //TODO: Refactor and implement if necessary
        return null;
    }

    @Override
    public String getProtocol() {
        getProtocolCalled.countDown();
        return null;
    }

    @Override
    public String getScheme() {
        getSchemeCalled.countDown();
        return null;
    }

    @Override
    public String getServerName() {
        getServerNameCalled.countDown();
        return null;
    }

    @Override
    public int getServerPort() {
        getServerPortCalled.countDown();
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        getReaderCalled.countDown();
        return null;
    }

    @Override
    public String getRemoteAddr() {
        getRemoteAddrCalled.countDown();
        return null;
    }

    @Override
    public String getRemoteHost() {
        getRemoteHostCalled.countDown();
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {
        setAttributeCalled.countDown();
    }

    @Override
    public void removeAttribute(String s) {
        removeAttributeCalled.countDown();
    }

    @Override
    public Locale getLocale() {
        getLocaleCalled.countDown();
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        getLocalesCalled.countDown();
        return null;
    }

    @Override
    public boolean isSecure() {
        isSecureCalled.countDown();
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        getRequestDispatcherCalled.countDown();
        return null;
    }

    @Override
    public String getRealPath(String s) {
        getRealPathCalled.countDown();
        return null;
    }

    @Override
    public int getRemotePort() {
        getRemotePortCalled.countDown();
        return 0;
    }

    @Override
    public String getLocalName() {
        getLocalNameCalled.countDown();
        return null;
    }

    @Override
    public String getLocalAddr() {
        getLocalAddrCalled.countDown();
        return null;
    }

    @Override
    public int getLocalPort() {
        getLocalPortCalled.countDown();
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        getServletContextCalled.countDown();
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        startAsyncCalledNoParams.countDown();
        this.asyncContext = new MockAsyncContext();
        return asyncContext;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IllegalStateException {
        startAsyncCalledTwoParams.countDown();
        this.asyncContext = new MockAsyncContext();
        return asyncContext;
    }

    @Override
    public boolean isAsyncStarted() {
        isAsyncStartedCalled.countDown();
        return asyncContext != null;
    }

    @Override
    public boolean isAsyncSupported() {
        isAsyncSupportedCalled.countDown();
        return true;
    }

    @Override
    public AsyncContext getAsyncContext() {
        getAsyncContextCalled.countDown();
        return asyncContext;
    }

    @Override
    public DispatcherType getDispatcherType() {
        getDispatcherTypeCalled.countDown();
        return null;
    }

    public class MockAsyncContext implements AsyncContext {
        private boolean completed = false;
        private boolean started = false;
        private long timeout = -1L;
        private boolean listenerAdded = false;

        @Override
        public ServletRequest getRequest() {
            return MockHttpServletRequest.this;
        }

        @Override
        public ServletResponse getResponse() {
            return MockHttpServletRequest.this.response;
        }

        @Override
        public boolean hasOriginalRequestAndResponse() {
            return false;
        }

        @Override
        public void dispatch() {

        }

        @Override
        public void dispatch(String s) {

        }

        @Override
        public void dispatch(ServletContext servletContext, String s) {

        }

        @Override
        public void complete() {
            this.completed = true;
        }

        @Override
        public void start(Runnable runnable) {
            this.started = true;
        }

        @Override
        public void addListener(AsyncListener asyncListener) {
            this.listenerAdded = true;
        }

        @Override
        public void addListener(AsyncListener asyncListener, ServletRequest servletRequest,
                                ServletResponse servletResponse) {
            this.listenerAdded = true;
        }

        @Override
        public <T extends AsyncListener> T createListener(Class<T> aClass) throws ServletException {
            return null;
        }

        @Override
        public void setTimeout(long l) {
            this.timeout = l;
        }

        @Override
        public long getTimeout() {
            return timeout;
        }
    }
}
