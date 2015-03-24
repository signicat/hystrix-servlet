package com.signicat.hystrix.servlet;

import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Einar Rosenvinge &lt;einros@signicat.com&gt;
 */
public class TimeoutAwareHttpServletRequestTest {

    @Test
    public void require_That_getAuthType_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getAuthType();
            assertThat(mock.getAuthTypeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getAuthType();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getAuthTypeCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getCookies_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getCookies();
            assertThat(mock.getCookiesCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getCookies();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getCookiesCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getDateHeader_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getDateHeader("");
            assertThat(mock.getDateHeaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getDateHeader("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getDateHeaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getHeader_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getHeader("");
            assertThat(mock.getHeaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getHeader("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getHeaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getHeaders_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getHeaders("");
            assertThat(mock.getHeadersCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getHeaders("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getHeadersCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getHeaderNames_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getHeaderNames();
            assertThat(mock.getHeaderNamesCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
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
    public void require_That_getIntHeader_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getIntHeader("");
            assertThat(mock.getIntHeaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getIntHeader("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getIntHeaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getMethod_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getMethod();
            assertThat(mock.getMethodCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getMethod();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getMethodCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getPathInfo_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getPathInfo();
            assertThat(mock.getPathInfoCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getPathInfo();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getPathInfoCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getPathTranslated_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getPathTranslated();
            assertThat(mock.getPathTranslatedCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getPathTranslated();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getPathTranslatedCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getContextPath_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getContextPath();
            assertThat(mock.getContextPathCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getContextPath();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getContextPathCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getQueryString_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getQueryString();
            assertThat(mock.getQueryStringCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getQueryString();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getQueryStringCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getRemoteUser_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getRemoteUser();
            assertThat(mock.getRemoteUserCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getRemoteUser();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getRemoteUserCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_isUserInRole_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.isUserInRole("");
            assertThat(mock.isUserInRoleCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.isUserInRole("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.isUserInRoleCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getUserPrincipal_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getUserPrincipal();
            assertThat(mock.getUserPrincipalCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getUserPrincipal();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getUserPrincipalCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getRequestedSessionId_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getRequestedSessionId();
            assertThat(mock.getRequestedSessionIdCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getRequestedSessionId();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getRequestedSessionIdCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getRequestURI_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getRequestURI();
            assertThat(mock.getRequestURICalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getRequestURI();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getRequestURICalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getRequestURL_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getRequestURL();
            assertThat(mock.getRequestURLCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getRequestURL();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getRequestURLCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getServletPath_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getServletPath();
            assertThat(mock.getServletPathCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getServletPath();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getServletPathCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getSessionNoParams_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getSession();
            assertThat(mock.getSessionCalledNoParams.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getSession();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getSessionCalledNoParams.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getSession_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getSession(true);
            assertThat(mock.getSessionCalledBooleanParam.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getSession(true);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getSessionCalledBooleanParam.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_changeSessionId_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.changeSessionId();
            assertThat(mock.changeSessionIdCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.changeSessionId();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.changeSessionIdCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_isRequestedSessionIdValid_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.isRequestedSessionIdValid();
            assertThat(mock.isRequestedSessionIdValidCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.isRequestedSessionIdValid();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.isRequestedSessionIdValidCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_isRequestedSessionIdFromCookie_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.isRequestedSessionIdFromCookie();
            assertThat(mock.isRequestedSessionIdFromCookieCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.isRequestedSessionIdFromCookie();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.isRequestedSessionIdFromCookieCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_isRequestedSessionIdFromURL_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.isRequestedSessionIdFromURL();
            assertThat(mock.isRequestedSessionIdFromURLCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.isRequestedSessionIdFromURL();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.isRequestedSessionIdFromURLCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_isRequestedSessionIdFromUrl_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.isRequestedSessionIdFromUrl();
            assertThat(mock.isRequestedSessionIdFromUrlCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.isRequestedSessionIdFromUrl();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.isRequestedSessionIdFromUrlCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_authenticate_Is_Consistent() throws IOException, ServletException, InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.authenticate(null);
            assertThat(mock.authenticateCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.authenticate(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.authenticateCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_login_Is_Consistent() throws ServletException, InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.login("", "");
            assertThat(mock.loginCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.login("", "");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.loginCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_logout_Is_Consistent() throws ServletException, InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.logout();
            assertThat(mock.logoutCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.logout();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.logoutCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getParts_Is_Consistent() throws IOException, ServletException, InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getParts();
            assertThat(mock.getPartsCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getParts();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getPartsCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getPart_Is_Consistent() throws IOException, ServletException, InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getPart("");
            assertThat(mock.getPartCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getPart("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getPartCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_upgrade_Is_Consistent() throws IOException, ServletException, InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.upgrade(null);
            assertThat(mock.upgradeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.upgrade(null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.upgradeCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getAttribute_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getAttribute("");
            assertThat(mock.getAttributeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getAttribute("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getAttributeCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getAttributeNames_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getAttributeNames();
            assertThat(mock.getAttributeNamesCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getAttributeNames();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getAttributeNamesCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getCharacterEncoding_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getCharacterEncoding();
            assertThat(mock.getCharacterEncodingCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
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
    public void require_That_setCharacterEncoding_Is_Consistent()
            throws UnsupportedEncodingException, InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.setCharacterEncoding("");
            assertThat(mock.setCharacterEncodingCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.setCharacterEncoding("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setCharacterEncodingCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getContentLength_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getContentLength();
            assertThat(mock.getContentLengthCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getContentLength();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getContentLengthCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getContentLengthLong_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getContentLengthLong();
            assertThat(mock.getContentLengthLongCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getContentLengthLong();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getContentLengthLongCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getContentType_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getContentType();
            assertThat(mock.getContentTypeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
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
    public void require_That_getInputStream_Is_Consistent() throws IOException, InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getInputStream();
            assertThat(mock.getInputStreamCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getInputStream();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getInputStreamCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getParameter_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getParameter("");
            assertThat(mock.getParameterCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getParameter("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getParameterCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getParameterNames_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getParameterNames();
            assertThat(mock.getParameterNamesCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getParameterNames();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getParameterNamesCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getParameterValues_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getParameterValues("");
            assertThat(mock.getParameterValuesCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getParameterValues("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getParameterValuesCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getParameterMap_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getParameterMap();
            assertThat(mock.getParameterMapCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getParameterMap();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getParameterMapCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getProtocol_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getProtocol();
            assertThat(mock.getProtocolCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getProtocol();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getProtocolCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getScheme_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getScheme();
            assertThat(mock.getSchemeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getScheme();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getSchemeCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getServerName_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getServerName();
            assertThat(mock.getServerNameCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getServerName();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getServerNameCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getServerPort_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getServerPort();
            assertThat(mock.getServerPortCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getServerPort();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getServerPortCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getReader_Is_Consistent() throws IOException, InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getReader();
            assertThat(mock.getReaderCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getReader();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getReaderCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getRemoteAddr_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getRemoteAddr();
            assertThat(mock.getRemoteAddrCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getRemoteAddr();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getRemoteAddrCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getRemoteHost_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getRemoteHost();
            assertThat(mock.getRemoteHostCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getRemoteHost();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getRemoteHostCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_setAttribute_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.setAttribute(null, null);
            assertThat(mock.setAttributeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.setAttribute(null, null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.setAttributeCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_removeAttribute_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.removeAttribute("");
            assertThat(mock.removeAttributeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.removeAttribute("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.removeAttributeCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getLocale_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getLocale();
            assertThat(mock.getLocaleCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getLocale();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getLocaleCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getLocales_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getLocales();
            assertThat(mock.getLocalesCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getLocales();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getLocalesCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_isSecure_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.isSecure();
            assertThat(mock.isSecureCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.isSecure();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.isSecureCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getRequestDispatcher_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getRequestDispatcher("");
            assertThat(mock.getRequestDispatcherCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getRequestDispatcher("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getRequestDispatcherCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getRealPath_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getRealPath("");
            assertThat(mock.getRealPathCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getRealPath("");
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getRealPathCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getRemotePort_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getRemotePort();
            assertThat(mock.getRemotePortCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getRemotePort();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getRemotePortCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getLocalName_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getLocalName();
            assertThat(mock.getLocalNameCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getLocalName();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getLocalNameCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getLocalAddr_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getLocalAddr();
            assertThat(mock.getLocalAddrCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getLocalAddr();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getLocalAddrCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getLocalPort_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getLocalPort();
            assertThat(mock.getLocalPortCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getLocalPort();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getLocalPortCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getServletContext_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getServletContext();
            assertThat(mock.getServletContextCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getServletContext();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getServletContextCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_startAsyncNoParams_Is_Consistent() throws IllegalStateException, InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.startAsync();
            assertThat(mock.startAsyncCalledNoParams.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.startAsync();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.startAsyncCalledNoParams.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_startAsyncTwoParams_Is_Consistent()
            throws IllegalStateException, InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.startAsync(null, null);
            assertThat(mock.startAsyncCalledTwoParams.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.startAsync(null, null);
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.startAsyncCalledTwoParams.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_isAsyncStarted_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.isAsyncStarted();
            assertThat(mock.isAsyncStartedCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.isAsyncStarted();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.isAsyncStartedCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_isAsyncSupported_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.isAsyncSupported();
            assertThat(mock.isAsyncSupportedCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.isAsyncSupported();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.isAsyncSupportedCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getAsyncContext_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getAsyncContext();
            assertThat(mock.getAsyncContextCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getAsyncContext();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getAsyncContextCalled.getCount(), equalTo(1L));
        }
    }

    @Test
    public void require_That_getDispatcherType_Is_Consistent() throws InterruptedException {
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.getDispatcherType();
            assertThat(mock.getDispatcherTypeCalled.await(60, TimeUnit.SECONDS), is(true));
        }
        {
            MockHttpServletRequest mock = new MockHttpServletRequest(new MockHttpServletResponse());
            TimeoutAwareHttpServletRequest timeout = new TimeoutAwareHttpServletRequest(mock);
            timeout.resetWrapped();
            try {
                timeout.getDispatcherType();
                fail("Should not have worked since we have nulled wrapped request.");
            } catch (IllegalStateException ignored) {
            }
            assertThat(mock.getDispatcherTypeCalled.getCount(), equalTo(1L));
        }
    }
}
