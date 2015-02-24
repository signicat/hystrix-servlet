package com.signicat.hystrix.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * In our implementation, we let the Servlet Container time things out _before_ Hystrix does (basically just a design
 * choice, but the other way around wouldn't work any better). So we're passing a Request and a Response to the wrapped
 * Servlet, and after some time, the Servlet Container will time out if processing takes too long. In that case, we do
 * not want the wrapped Servlet to still be able to tamper with the actual, real Request or Response. Thus, we use this
 * wrapper, and we call {@link #resetWrapped()} when the Servlet Container times out or completes. Any subsequent calls
 * to the Request or Response from the wrapped servlet will throw an exception.
 *
 * @author Einar Rosenvinge &lt;einros@signicat.com&gt;
 * @see TimeoutAwareHttpServletRequest
 */
class TimeoutAwareHttpServletResponse implements HttpServletResponse {

    private HttpServletResponse wr;

    TimeoutAwareHttpServletResponse(HttpServletResponse wrappedResponse) {
        this.wr = wrappedResponse;
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
    public synchronized void addCookie(Cookie cookie) {
        checkAndThrow();
        wr.addCookie(cookie);
    }

    @Override
    public synchronized boolean containsHeader(String name) {
        checkAndThrow();
        return wr.containsHeader(name);
    }

    @Override
    public synchronized String encodeURL(String url) {
        checkAndThrow();
        return wr.encodeURL(url);
    }

    @Override
    public synchronized String encodeRedirectURL(String url) {
        checkAndThrow();
        return wr.encodeRedirectURL(url);
    }

    @SuppressWarnings("deprecation")
    @Override
    public synchronized String encodeUrl(String url) {
        checkAndThrow();
        return wr.encodeUrl(url);
    }

    @SuppressWarnings("deprecation")
    @Override
    public synchronized String encodeRedirectUrl(String url) {
        checkAndThrow();
        return wr.encodeRedirectUrl(url);
    }

    @Override
    public synchronized void sendError(int sc, String msg) throws IOException {
        checkAndThrow();
        wr.sendError(sc, msg);
    }

    @Override
    public synchronized void sendError(int sc) throws IOException {
        checkAndThrow();
        wr.sendError(sc);
    }

    @Override
    public synchronized void sendRedirect(String location) throws IOException {
        checkAndThrow();
        wr.sendRedirect(location);

    }

    @Override
    public synchronized void setDateHeader(String name, long date) {
        checkAndThrow();
        wr.setDateHeader(name, date);
    }

    @Override
    public synchronized void addDateHeader(String name, long date) {
        checkAndThrow();
        wr.addDateHeader(name, date);
    }

    @Override
    public synchronized void setHeader(String name, String value) {
        checkAndThrow();
        wr.setHeader(name, value);
    }

    @Override
    public synchronized void addHeader(String name, String value) {
        checkAndThrow();
        wr.addHeader(name, value);
    }

    @Override
    public synchronized void setIntHeader(String name, int value) {
        checkAndThrow();
        wr.setIntHeader(name, value);
    }

    @Override
    public synchronized void addIntHeader(String name, int value) {
        checkAndThrow();
        wr.addIntHeader(name, value);
    }

    @Override
    public synchronized void setStatus(int sc) {
        checkAndThrow();
        wr.setStatus(sc);
    }

    @SuppressWarnings("deprecation")
    @Override
    public synchronized void setStatus(int sc, String sm) {
        checkAndThrow();
        wr.setStatus(sc, sm);
    }

    @Override
    public synchronized int getStatus() {
        checkAndThrow();
        return wr.getStatus();
    }

    @Override
    public synchronized String getHeader(String name) {
        checkAndThrow();
        return wr.getHeader(name);
    }

    @Override
    public synchronized Collection<String> getHeaders(String name) {
        checkAndThrow();
        return wr.getHeaders(name);
    }

    @Override
    public synchronized Collection<String> getHeaderNames() {
        checkAndThrow();
        return wr.getHeaderNames();
    }

    @Override
    public synchronized String getCharacterEncoding() {
        checkAndThrow();
        return wr.getCharacterEncoding();
    }

    @Override
    public synchronized String getContentType() {
        checkAndThrow();
        return wr.getContentType();
    }

    @Override
    public synchronized ServletOutputStream getOutputStream() throws IOException {
        checkAndThrow();
        return wr.getOutputStream();
    }

    @Override
    public synchronized PrintWriter getWriter() throws IOException {
        checkAndThrow();
        return wr.getWriter();
    }

    @Override
    public synchronized void setCharacterEncoding(String charset) {
        checkAndThrow();
        wr.setCharacterEncoding(charset);
    }

    @Override
    public synchronized void setContentLength(int len) {
        checkAndThrow();
        wr.setContentLength(len);

    }

    @Override
    public synchronized void setContentLengthLong(long len) {
        checkAndThrow();
        wr.setContentLengthLong(len);
    }

    @Override
    public synchronized void setContentType(String type) {
        checkAndThrow();
        wr.setContentType(type);
    }

    @Override
    public synchronized void setBufferSize(int size) {
        checkAndThrow();
        wr.setBufferSize(size);
    }

    @Override
    public synchronized int getBufferSize() {
        checkAndThrow();
        return wr.getBufferSize();
    }

    @Override
    public synchronized void flushBuffer() throws IOException {
        checkAndThrow();
        wr.flushBuffer();
    }

    @Override
    public synchronized void resetBuffer() {
        checkAndThrow();
        wr.resetBuffer();
    }

    @Override
    public synchronized boolean isCommitted() {
        checkAndThrow();
        return wr.isCommitted();
    }

    @Override
    public synchronized void reset() {
        checkAndThrow();
        wr.reset();
    }

    @Override
    public synchronized void setLocale(Locale loc) {
        checkAndThrow();
        wr.setLocale(loc);
    }

    @Override
    public synchronized Locale getLocale() {
        checkAndThrow();
        return wr.getLocale();
    }
}
