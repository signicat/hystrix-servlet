package com.signicat.hystrix.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
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
 * In our implementation, we let the Servlet Container time things out _before_ Hystrix does (basically just a design
 * choice, but the other way around wouldn't work any better). So we're passing a Request and a Response to the wrapped
 * Servlet, and after some time, the Servlet Container will time out if processing takes too long. In that case, we do
 * not want the wrapped Servlet to still be able to tamper with the actual, real Request or Response. Thus, we use this
 * wrapper, and we call {@link #resetWrapped()} when the Servlet Container times out or completes. Any subsequent calls
 * to the Request or Response from the wrapped servlet will throw an exception.
 *
 * @author Einar Rosenvinge &lt;einarmr@gmail.com&gt;
 * @see TimeoutAwareHttpServletResponse
 */
class TimeoutAwareHttpServletRequest implements HttpServletRequest {

    private HttpServletRequest wr;

    TimeoutAwareHttpServletRequest(HttpServletRequest wrappedRequest) {
        this.wr = wrappedRequest;
    }

    synchronized void resetWrapped() {
        this.wr = null;
    }

    private void checkAndThrow() {
        if (wr == null) {
            throw new IllegalStateException("Response already committed to client.");
        }
    }

    @Override
    public synchronized String getAuthType() {
        checkAndThrow();
        return wr.getAuthType();
    }

    @Override
    public synchronized Cookie[] getCookies() {
        checkAndThrow();
        return wr.getCookies();
    }

    @Override
    public synchronized long getDateHeader(String name) {
        checkAndThrow();
        return wr.getDateHeader(name);
    }

    @Override
    public synchronized String getHeader(String name) {
        checkAndThrow();
        return wr.getHeader(name);
    }

    @Override
    public synchronized Enumeration<String> getHeaders(String name) {
        checkAndThrow();
        return wr.getHeaders(name);
    }

    @Override
    public synchronized Enumeration<String> getHeaderNames() {
        checkAndThrow();
        return wr.getHeaderNames();
    }

    @Override
    public synchronized int getIntHeader(String name) {
        checkAndThrow();
        return wr.getIntHeader(name);
    }

    @Override
    public synchronized String getMethod() {
        checkAndThrow();
        return wr.getMethod();
    }

    @Override
    public synchronized String getPathInfo() {
        checkAndThrow();
        return wr.getPathInfo();
    }

    @Override
    public synchronized String getPathTranslated() {
        checkAndThrow();
        return wr.getPathTranslated();
    }

    @Override
    public synchronized String getContextPath() {
        checkAndThrow();
        return wr.getContextPath();
    }

    @Override
    public synchronized String getQueryString() {
        checkAndThrow();
        return wr.getQueryString();
    }

    @Override
    public synchronized String getRemoteUser() {
        checkAndThrow();
        return wr.getRemoteUser();
    }

    @Override
    public synchronized boolean isUserInRole(String role) {
        checkAndThrow();
        return wr.isUserInRole(role);
    }

    @Override
    public synchronized Principal getUserPrincipal() {
        checkAndThrow();
        return wr.getUserPrincipal();
    }

    @Override
    public synchronized String getRequestedSessionId() {
        checkAndThrow();
        return wr.getRequestedSessionId();
    }

    @Override
    public synchronized String getRequestURI() {
        checkAndThrow();
        return wr.getRequestURI();
    }

    @Override
    public synchronized StringBuffer getRequestURL() {
        checkAndThrow();
        return wr.getRequestURL();
    }

    @Override
    public synchronized String getServletPath() {
        checkAndThrow();
        return wr.getServletPath();
    }

    @Override
    public synchronized HttpSession getSession(boolean create) {
        checkAndThrow();
        return wr.getSession(create);
    }

    @Override
    public synchronized HttpSession getSession() {
        checkAndThrow();
        return wr.getSession();
    }

    @Override
    public synchronized String changeSessionId() {
        checkAndThrow();
        return wr.changeSessionId();
    }

    @Override
    public synchronized boolean isRequestedSessionIdValid() {
        checkAndThrow();
        return wr.isRequestedSessionIdValid();
    }

    @Override
    public synchronized boolean isRequestedSessionIdFromCookie() {
        checkAndThrow();
        return wr.isRequestedSessionIdFromCookie();
    }

    @Override
    public synchronized boolean isRequestedSessionIdFromURL() {
        checkAndThrow();
        return wr.isRequestedSessionIdFromURL();
    }

    @SuppressWarnings("deprecation")
    @Override
    public synchronized boolean isRequestedSessionIdFromUrl() {
        checkAndThrow();
        return wr.isRequestedSessionIdFromUrl();
    }

    @Override
    public synchronized boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        checkAndThrow();
        return wr.authenticate(response);
    }

    @Override
    public synchronized void login(String username, String password) throws ServletException {
        checkAndThrow();
        wr.login(username, password);
    }

    @Override
    public synchronized void logout() throws ServletException {
        checkAndThrow();
        wr.logout();
    }

    @Override
    public synchronized Collection<Part> getParts() throws IOException, ServletException {
        checkAndThrow();
        return wr.getParts();
    }

    @Override
    public synchronized Part getPart(String name) throws IOException, ServletException {
        checkAndThrow();
        return wr.getPart(name);
    }

    @Override
    public synchronized <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass)
            throws IOException, ServletException {
        checkAndThrow();
        return wr.upgrade(handlerClass);
    }

    @Override
    public synchronized Object getAttribute(String name) {
        checkAndThrow();
        return wr.getAttribute(name);
    }

    @Override
    public synchronized Enumeration<String> getAttributeNames() {
        checkAndThrow();
        return wr.getAttributeNames();
    }

    @Override
    public synchronized String getCharacterEncoding() {
        checkAndThrow();
        return wr.getCharacterEncoding();
    }

    @Override
    public synchronized void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        checkAndThrow();
        wr.setCharacterEncoding(env);
    }

    @Override
    public synchronized int getContentLength() {
        checkAndThrow();
        return wr.getContentLength();
    }

    @Override
    public synchronized long getContentLengthLong() {
        checkAndThrow();
        return wr.getContentLengthLong();
    }

    @Override
    public synchronized String getContentType() {
        checkAndThrow();
        return wr.getContentType();
    }

    @Override
    public synchronized ServletInputStream getInputStream() throws IOException {
        checkAndThrow();
        return wr.getInputStream();
    }

    @Override
    public synchronized String getParameter(String name) {
        checkAndThrow();
        return wr.getParameter(name);
    }

    @Override
    public synchronized Enumeration<String> getParameterNames() {
        checkAndThrow();
        return wr.getParameterNames();
    }

    @Override
    public synchronized String[] getParameterValues(String name) {
        checkAndThrow();
        return wr.getParameterValues(name);
    }

    @Override
    public synchronized Map<String, String[]> getParameterMap() {
        checkAndThrow();
        return wr.getParameterMap();
    }

    @Override
    public synchronized String getProtocol() {
        checkAndThrow();
        return wr.getProtocol();
    }

    @Override
    public synchronized String getScheme() {
        checkAndThrow();
        return wr.getScheme();
    }

    @Override
    public synchronized String getServerName() {
        checkAndThrow();
        return wr.getServerName();
    }

    @Override
    public synchronized int getServerPort() {
        checkAndThrow();
        return wr.getServerPort();
    }

    @Override
    public synchronized BufferedReader getReader() throws IOException {
        checkAndThrow();
        return wr.getReader();
    }

    @Override
    public synchronized String getRemoteAddr() {
        checkAndThrow();
        return wr.getRemoteAddr();
    }

    @Override
    public synchronized String getRemoteHost() {
        checkAndThrow();
        return wr.getRemoteHost();
    }

    @Override
    public synchronized void setAttribute(String name, Object o) {
        checkAndThrow();
        wr.setAttribute(name, o);
    }

    @Override
    public synchronized void removeAttribute(String name) {
        checkAndThrow();
        wr.removeAttribute(name);
    }

    @Override
    public synchronized Locale getLocale() {
        checkAndThrow();
        return wr.getLocale();
    }

    @Override
    public synchronized Enumeration<Locale> getLocales() {
        checkAndThrow();
        return wr.getLocales();
    }

    @Override
    public synchronized boolean isSecure() {
        checkAndThrow();
        return wr.isSecure();
    }

    @Override
    public synchronized RequestDispatcher getRequestDispatcher(String path) {
        checkAndThrow();
        return wr.getRequestDispatcher(path);
    }

    @SuppressWarnings("deprecation")
    @Override
    public synchronized String getRealPath(String path) {
        checkAndThrow();
        return wr.getRealPath(path);
    }

    @Override
    public synchronized int getRemotePort() {
        checkAndThrow();
        return wr.getRemotePort();
    }

    @Override
    public synchronized String getLocalName() {
        checkAndThrow();
        return wr.getLocalName();
    }

    @Override
    public synchronized String getLocalAddr() {
        checkAndThrow();
        return wr.getLocalAddr();
    }

    @Override
    public synchronized int getLocalPort() {
        checkAndThrow();
        return wr.getLocalPort();
    }

    @Override
    public synchronized ServletContext getServletContext() {
        checkAndThrow();
        return wr.getServletContext();
    }

    @Override
    public synchronized AsyncContext startAsync() throws IllegalStateException {
        checkAndThrow();
        return wr.startAsync();
    }

    @Override
    public synchronized AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IllegalStateException {
        checkAndThrow();
        return wr.startAsync(servletRequest, servletResponse);
    }

    @Override
    public synchronized boolean isAsyncStarted() {
        checkAndThrow();
        return wr.isAsyncStarted();
    }

    @Override
    public synchronized boolean isAsyncSupported() {
        checkAndThrow();
        return wr.isAsyncSupported();
    }

    @Override
    public synchronized AsyncContext getAsyncContext() {
        checkAndThrow();
        return wr.getAsyncContext();
    }

    @Override
    public synchronized DispatcherType getDispatcherType() {
        checkAndThrow();
        return wr.getDispatcherType();
    }

    @Override
    public synchronized boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeoutAwareHttpServletRequest)) {
            return false;
        }
        TimeoutAwareHttpServletRequest that = (TimeoutAwareHttpServletRequest) o;
        return !(wr != null ? !wr.equals(that.wr) : that.wr != null);
    }

    @Override
    public synchronized int hashCode() {
        return wr != null ? wr.hashCode() : 0;
    }

    @Override
    public synchronized String toString() {
        return this.getClass().getName() + " " + ((wr == null) ? "" : wr.toString());
    }
}
