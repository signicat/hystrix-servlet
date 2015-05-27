/*
 * Copyright (C) 2015 Signicat AS
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.signicat.hystrix.servlet;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Einar Rosenvinge &lt;einarmr@gmail.com&gt;
 */
public class TimeoutAwareHttpServletResponseTest {

    @Test
    public void require_That_addCookie_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.addCookie(null);
            assertThat(mock.addCookieCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.addCookie(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.addCookieCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_containsHeader_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.containsHeader(null);
            assertThat(mock.containsHeaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.containsHeader(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.containsHeaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_encodeURL_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.encodeURL(null);
            assertThat(mock.encodeURLCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.encodeURL(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.encodeURLCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_encodeRedirectURL_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.encodeRedirectURL(null);
            assertThat(mock.encodeRedirectURLCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.encodeRedirectURL(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.encodeRedirectURLCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_encodeUrl_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.encodeUrl(null);
            assertThat(mock.encodeUrlCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.encodeUrl(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.encodeUrlCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_encodeRedirectUrl_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.encodeRedirectUrl(null);
            assertThat(mock.encodeRedirectUrlCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.encodeRedirectUrl(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.encodeRedirectUrlCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_sendErrorTwoParams_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.sendError(0, "");
            assertThat(mock.sendErrorCalledTwoParams.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.sendError(0, "");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.sendErrorCalledTwoParams.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_sendErrorOneParam_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.sendError(0);
            assertThat(mock.sendErrorCalledOneParam.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.sendError(0);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.sendErrorCalledOneParam.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_sendRedirect_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.sendRedirect(null);
            assertThat(mock.sendRedirectCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.sendRedirect(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.sendRedirectCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setDateHeader_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.setDateHeader("", 0L);
            assertThat(mock.setDateHeaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.setDateHeader("", 0L);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setDateHeaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_addDateHeader_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.addDateHeader("", 0L);
            assertThat(mock.addDateHeaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.addDateHeader("", 0L);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.addDateHeaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setHeader_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.setHeader("", "");
            assertThat(mock.setHeaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.setHeader("", "");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setHeaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_addHeader_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.addHeader("", "");
            assertThat(mock.addHeaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.addHeader("", "");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.addHeaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setIntHeader_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.setIntHeader("", 0);
            assertThat(mock.setIntHeaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.setIntHeader("", 0);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setIntHeaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_addIntHeader_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.addIntHeader("", 0);
            assertThat(mock.addIntHeaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.addIntHeader("", 0);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.addIntHeaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setStatusTwoParams_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.setStatus(0, "");
            assertThat(mock.setStatusCalledTwoParams.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.setStatus(0, "");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setStatusCalledTwoParams.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setStatusOneParam_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.setStatus(0);
            assertThat(mock.setStatusCalledOneParam.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.setStatus(0);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setStatusCalledOneParam.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getStatus_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.getStatus();
            assertThat(mock.getStatusCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.getStatus();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getStatusCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getHeader_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.getHeader(null);
            assertThat(mock.getHeaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.getHeader(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getHeaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getHeaders_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.getHeaders(null);
            assertThat(mock.getHeadersCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.getHeaders(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getHeadersCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getHeaderNames_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.getHeaderNames();
            assertThat(mock.getHeaderNamesCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.getHeaderNames();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getHeaderNamesCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getCharacterEncoding_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.getCharacterEncoding();
            assertThat(mock.getCharacterEncodingCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.getCharacterEncoding();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getCharacterEncodingCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getContentType_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.getContentType();
            assertThat(mock.getContentTypeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.getContentType();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getContentTypeCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getOutputStream_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.getOutputStream();
            assertThat(mock.getOutputStreamCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.getOutputStream();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getOutputStreamCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getWriter_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.getWriter();
            assertThat(mock.getWriterCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.getWriter();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getWriterCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setCharacterEncoding_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.setCharacterEncoding(null);
            assertThat(mock.setCharacterEncodingCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.setCharacterEncoding(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setCharacterEncodingCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setContentLength_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.setContentLength(0);
            assertThat(mock.setContentLengthCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.setContentLength(0);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setContentLengthCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setContentLengthLong_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.setContentLengthLong(0L);
            assertThat(mock.setContentLengthLongCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.setContentLengthLong(0L);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setContentLengthLongCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setContentType_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.setContentType(null);
            assertThat(mock.setContentTypeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.setContentType(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setContentTypeCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setBufferSize_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.setBufferSize(0);
            assertThat(mock.setBufferSizeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.setBufferSize(0);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setBufferSizeCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getBufferSize_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.getBufferSize();
            assertThat(mock.getBufferSizeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.getBufferSize();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getBufferSizeCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_flushBuffer_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.flushBuffer();
            assertThat(mock.flushBufferCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.flushBuffer();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.flushBufferCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_resetBuffer_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetBuffer();
            assertThat(mock.resetBufferCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.resetBuffer();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.resetBufferCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_isCommitted_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.isCommitted();
            assertThat(mock.isCommittedCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.isCommitted();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.isCommittedCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_reset_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.reset();
            assertThat(mock.resetCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.reset();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.resetCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setLocale_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.setLocale(null);
            assertThat(mock.setLocaleCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.setLocale(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setLocaleCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getLocale_Is_Consistent() throws Exception {
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.getLocale();
            assertThat(mock.getLocaleCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletResponse mock = new MockHttpServletResponse();
            TimeoutAwareHttpServletResponse timeout = new TimeoutAwareHttpServletResponse(mock);
            timeout.resetWrapped();
            try {
                timeout.getLocale();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getLocaleCalled.getCount(), equalTo(1L));
        }
    }
}
