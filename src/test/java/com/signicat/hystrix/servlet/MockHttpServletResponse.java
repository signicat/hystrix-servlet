package com.signicat.hystrix.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Einar Rosenvinge &lt;einarmr@gmail.com&gt;
 */
public class MockHttpServletResponse implements HttpServletResponse {

    final CountDownLatch addCookieCalled = new CountDownLatch(1);
    final CountDownLatch containsHeaderCalled = new CountDownLatch(1);
    final CountDownLatch encodeURLCalled = new CountDownLatch(1);
    final CountDownLatch encodeRedirectURLCalled = new CountDownLatch(1);
    final CountDownLatch encodeUrlCalled = new CountDownLatch(1);
    final CountDownLatch encodeRedirectUrlCalled = new CountDownLatch(1);
    final CountDownLatch sendErrorCalledTwoParams = new CountDownLatch(1);
    final CountDownLatch sendErrorCalledOneParam = new CountDownLatch(1);
    final CountDownLatch sendRedirectCalled = new CountDownLatch(1);
    final CountDownLatch setDateHeaderCalled = new CountDownLatch(1);
    final CountDownLatch addDateHeaderCalled = new CountDownLatch(1);
    final CountDownLatch setHeaderCalled = new CountDownLatch(1);
    final CountDownLatch addHeaderCalled = new CountDownLatch(1);
    final CountDownLatch setIntHeaderCalled = new CountDownLatch(1);
    final CountDownLatch addIntHeaderCalled = new CountDownLatch(1);
    final CountDownLatch setStatusCalledTwoParams = new CountDownLatch(1);
    final CountDownLatch setStatusCalledOneParam = new CountDownLatch(1);
    final CountDownLatch getStatusCalled = new CountDownLatch(1);
    final CountDownLatch getHeaderCalled = new CountDownLatch(1);
    final CountDownLatch getHeadersCalled = new CountDownLatch(1);
    final CountDownLatch getHeaderNamesCalled = new CountDownLatch(1);
    final CountDownLatch getCharacterEncodingCalled = new CountDownLatch(1);
    final CountDownLatch getContentTypeCalled = new CountDownLatch(1);
    final CountDownLatch getOutputStreamCalled = new CountDownLatch(1);
    final CountDownLatch getWriterCalled = new CountDownLatch(1);
    final CountDownLatch setCharacterEncodingCalled = new CountDownLatch(1);
    final CountDownLatch setContentLengthCalled = new CountDownLatch(1);
    final CountDownLatch setContentLengthLongCalled = new CountDownLatch(1);
    final CountDownLatch setContentTypeCalled = new CountDownLatch(1);
    final CountDownLatch setBufferSizeCalled = new CountDownLatch(1);
    final CountDownLatch getBufferSizeCalled = new CountDownLatch(1);
    final CountDownLatch flushBufferCalled = new CountDownLatch(1);
    final CountDownLatch resetBufferCalled = new CountDownLatch(1);
    final CountDownLatch isCommittedCalled = new CountDownLatch(1);
    final CountDownLatch resetCalled = new CountDownLatch(1);
    final CountDownLatch setLocaleCalled = new CountDownLatch(1);
    final CountDownLatch getLocaleCalled = new CountDownLatch(1);

    @Override
    public void addCookie(Cookie cookie) {
        addCookieCalled.countDown();
    }

    @Override
    public boolean containsHeader(String s) {
        containsHeaderCalled.countDown();
        return false;
    }

    @Override
    public String encodeURL(String s) {
        encodeURLCalled.countDown();
        return null;
    }

    @Override
    public String encodeRedirectURL(String s) {
        encodeRedirectURLCalled.countDown();
        return null;
    }

    @Override
    public String encodeUrl(String s) {
        encodeUrlCalled.countDown();
        return null;
    }

    @Override
    public String encodeRedirectUrl(String s) {
        encodeRedirectUrlCalled.countDown();
        return null;
    }

    @Override
    public void sendError(int i, String s) throws IOException {
        sendErrorCalledTwoParams.countDown();
    }

    @Override
    public void sendError(int i) throws IOException {
        sendErrorCalledOneParam.countDown();
    }

    @Override
    public void sendRedirect(String s) throws IOException {
        sendRedirectCalled.countDown();
    }

    @Override
    public void setDateHeader(String s, long l) {
        setDateHeaderCalled.countDown();
    }

    @Override
    public void addDateHeader(String s, long l) {
        addDateHeaderCalled.countDown();
    }

    @Override
    public void setHeader(String s, String s1) {
        setHeaderCalled.countDown();
    }

    @Override
    public void addHeader(String s, String s1) {
        addHeaderCalled.countDown();
    }

    @Override
    public void setIntHeader(String s, int i) {
        setIntHeaderCalled.countDown();
    }

    @Override
    public void addIntHeader(String s, int i) {
        addIntHeaderCalled.countDown();
    }

    @Override
    public void setStatus(int i) {
        setStatusCalledOneParam.countDown();
    }

    @Override
    public void setStatus(int i, String s) {
        setStatusCalledTwoParams.countDown();
    }

    @Override
    public int getStatus() {
        getStatusCalled.countDown();
        return 0;
    }

    @Override
    public String getHeader(String s) {
        getHeaderCalled.countDown();
        return null;
    }

    @Override
    public Collection<String> getHeaders(String s) {
        getHeadersCalled.countDown();
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        getHeaderNamesCalled.countDown();
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        getCharacterEncodingCalled.countDown();
        return null;
    }

    @Override
    public String getContentType() {
        getContentTypeCalled.countDown();
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        getOutputStreamCalled.countDown();
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        getWriterCalled.countDown();
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) {
        setCharacterEncodingCalled.countDown();
    }

    @Override
    public void setContentLength(int i) {
        setContentLengthCalled.countDown();
    }

    @Override
    public void setContentLengthLong(long l) {
        setContentLengthLongCalled.countDown();
    }

    @Override
    public void setContentType(String s) {
        setContentTypeCalled.countDown();
    }

    @Override
    public void setBufferSize(int i) {
        setBufferSizeCalled.countDown();
    }

    @Override
    public int getBufferSize() {
        getBufferSizeCalled.countDown();
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {
        flushBufferCalled.countDown();
    }

    @Override
    public void resetBuffer() {
        resetBufferCalled.countDown();
    }

    @Override
    public boolean isCommitted() {
        isCommittedCalled.countDown();
        return false;
    }

    @Override
    public void reset() {
        resetCalled.countDown();
    }

    @Override
    public void setLocale(Locale locale) {
        setLocaleCalled.countDown();
    }

    @Override
    public Locale getLocale() {
        getLocaleCalled.countDown();
        return null;
    }
}
