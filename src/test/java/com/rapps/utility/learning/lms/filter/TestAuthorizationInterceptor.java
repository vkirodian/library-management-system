package com.rapps.utility.learning.lms.filter;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rapps.utility.learning.lms.enums.AccessTypeEnum;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.global.SessionCache;
import com.rapps.utility.learning.lms.helper.AccessRoleMgmtHelper;
import com.rapps.utility.learning.lms.helper.SessionMgmtHelper;
import com.rapps.utility.learning.lms.helper.TestClassWithMultipleInterfaces;
import com.rapps.utility.learning.lms.helper.TestClassWithOneInterfaces;
import com.rapps.utility.learning.lms.persistence.bean.Session;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestAuthorizationInterceptor extends TestCase {

	private static final String SESSION_ID_1 = "session_id_1";
	Session s = new Session();

	@InjectMocks
	AuthorizationInterceptor i;

	@Mock
	SessionMgmtHelper helper;
	@Mock
	AccessRoleMgmtHelper accessRoleHelper;
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	Object handler;
	@Mock
	ModelAndView modelAndView;
	@Mock
	Exception ex;
	@Mock
	HandlerMethod handlerMethod;

	@Test
	public void testAuthorizationInterceptor() {
		new AuthorizationInterceptor(helper, accessRoleHelper);
	}

	@Test
	public void testPreHandle() throws Exception {
	}

	@Test
	public void testPostHandle() throws Exception {
		i.postHandle(request, response, handler, modelAndView);
	}

	@Test
	public void testAfterCompletion() throws Exception {
		i.afterCompletion(request, response, handler, ex);
	}

	@Test(expected = IOException.class)
	public void testVerifySessionAndAuthorize_MoreInterfaces() throws Exception {
		when(handlerMethod.getMethod()).thenReturn(TestClassWithMultipleInterfaces.class.getMethod("destroy"));
		when(handlerMethod.getBean()).thenReturn(new TestClassWithMultipleInterfaces());
		i.verifySessionAndAuthorize(request, handlerMethod);
	}

	@Test(expected = IOException.class)
	public void testVerifySessionAndAuthorize_NoMethodEx() throws Exception {
		when(handlerMethod.getMethod()).thenReturn(TestClassWithOneInterfaces.class.getMethod("notInInterfaceMethod"));
		when(handlerMethod.getBean()).thenReturn(new TestClassWithOneInterfaces());
		i.verifySessionAndAuthorize(request, handlerMethod);
	}

	// @Test
	public void testVerifySessionAndAuthorize_SecurityEx() throws Exception {
		when(handlerMethod.getMethod()).thenReturn(TestClassWithOneInterfaces.class.getMethod("privateMethod"));
		when(handlerMethod.getBean()).thenReturn(new TestClassWithOneInterfaces());
		i.verifySessionAndAuthorize(request, handlerMethod);
	}

	@Test(expected = IOException.class)
	public void testVerifySessionAndAuthorize_NoAuthFound() throws Exception {
		when(handlerMethod.getMethod()).thenReturn(TestClassWithOneInterfaces.class.getMethod("methodWithoutAuth"));
		when(handlerMethod.getBean()).thenReturn(new TestClassWithOneInterfaces());
		i.verifySessionAndAuthorize(request, handlerMethod);
	}
	
	@Test
	public void testVerifySessionAndAuthorize_methodSkipSession() throws Exception {
		when(handlerMethod.getMethod())
				.thenReturn(TestClassWithOneInterfaces.class.getMethod("methodSkipSession"));
		when(handlerMethod.getBean()).thenReturn(new TestClassWithOneInterfaces());
		i.verifySessionAndAuthorize(request, handlerMethod);
	}

	@Test(expected = IOException.class)
	public void testVerifySessionAndAuthorize_SessionMissingInHeader() throws Exception {
		when(handlerMethod.getMethod()).thenReturn(TestClassWithOneInterfaces.class.getMethod("methodWithAuth"));
		when(handlerMethod.getBean()).thenReturn(new TestClassWithOneInterfaces());
		when(request.getHeader(LmsConstants.SESSION_ID)).thenReturn(null);
		i.verifySessionAndAuthorize(request, handlerMethod);
	}

	@Test(expected = IOException.class)
	public void testVerifySessionAndAuthorize_SessionMissingInCache() throws Exception {
		when(handlerMethod.getMethod()).thenReturn(TestClassWithOneInterfaces.class.getMethod("methodWithAuth"));
		when(handlerMethod.getBean()).thenReturn(new TestClassWithOneInterfaces());
		when(request.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		SessionCache.removeAllSessions();
		i.verifySessionAndAuthorize(request, handlerMethod);
	}

	@Test(expected = IOException.class)
	public void testVerifySessionAndAuthorize_UserRoleNotFound() throws Exception {
		when(handlerMethod.getMethod()).thenReturn(TestClassWithOneInterfaces.class.getMethod("methodWithAuth"));
		when(handlerMethod.getBean()).thenReturn(new TestClassWithOneInterfaces());
		when(request.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getSession());
		when(helper.getRoleForUserSession(getSession())).thenThrow(new LmsException());
		i.verifySessionAndAuthorize(request, handlerMethod);
	}

	@Test(expected = IOException.class)
	public void testVerifySessionAndAuthorize_UserUnauthorized() throws Exception {
		when(handlerMethod.getMethod()).thenReturn(TestClassWithOneInterfaces.class.getMethod("methodWithAuth"));
		when(handlerMethod.getBean()).thenReturn(new TestClassWithOneInterfaces());
		when(request.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getSession());
		when(helper.getRoleForUserSession(getSession())).thenReturn(UserRoleEnum.LIBRARIAN);
		i.verifySessionAndAuthorize(request, handlerMethod);
	}

	@Test
	public void testVerifySessionAndAuthorize_Success() throws Exception {
		when(handlerMethod.getMethod()).thenReturn(TestClassWithOneInterfaces.class.getMethod("methodWithAuth"));
		when(handlerMethod.getBean()).thenReturn(new TestClassWithOneInterfaces());
		when(request.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getSession());
		when(accessRoleHelper.getUserRolesForAccessType(AccessTypeEnum.AUTH_ALL)).thenReturn(Arrays.asList(UserRoleEnum.SUPER_ADMIN));
		when(helper.getRoleForUserSession(getSession())).thenReturn(UserRoleEnum.SUPER_ADMIN);
		i.verifySessionAndAuthorize(request, handlerMethod);
	}

	private Session getSession() {
		s.setSessionId(SESSION_ID_1);
		s.setUserId("u1");
		return s;
	}

}
