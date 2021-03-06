package com.rapps.utility.learning.lms.helper;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.email.EmailUtility;
import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.global.SessionCache;
import com.rapps.utility.learning.lms.model.LoginInputModel;
import com.rapps.utility.learning.lms.model.ResetPasswordModel;
import com.rapps.utility.learning.lms.model.UserModel;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.service.SessionService;
import com.rapps.utility.learning.lms.persistence.service.UserService;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestAuthenticationMgmtHelper extends TestCase {

	private static final String LOGIN = "admin";
	private static final String PSWD = "Admin@123";
	private static final String NEWPSWD = "Admin@1234";
	private static final String SESSION_ID_1 = "session_id_1";
	private static final long EXTRA_TIME = 60 * 60 * 1000L;

	@InjectMocks
	AuthenticationMgmtHelper helper = new AuthenticationMgmtHelper();

	@Mock
	UserService userService;
	@Mock
	SessionService sessionService;
	@Mock
	HttpServletRequest httpRequest;
	@Mock
	EmailUtility emailUtility;

	@Test
	public void testAuthenticateUser_Success() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		user.setPasswordExpiryTms(System.currentTimeMillis() + EXTRA_TIME);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		when(httpRequest.getRemoteAddr()).thenReturn("10.1.1.1");
		when(sessionService.saveSession(any(Session.class))).thenReturn(getSession());
		Session sactual = helper.authenticateUser(getLoginInput());
		assertEquals("", getSession(), sactual);
	}

	@Test(expected = LmsException.class)
	public void testAuthenticateUser_LoginIdEmpty() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		user.setPasswordExpiryTms(System.currentTimeMillis() + EXTRA_TIME);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		when(httpRequest.getRemoteAddr()).thenReturn("10.1.1.1");
		when(sessionService.saveSession(any(Session.class))).thenReturn(getSession());
		LoginInputModel m = getLoginInput();
		m.setLoginId(null);
		Session sactual = helper.authenticateUser(m);
		assertEquals("", getSession(), sactual);
	}

	@Test(expected = LmsException.class)
	public void testAuthenticateUser_IncorrectCreds() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD + " error");
		user.setPasswordExpiryTms(System.currentTimeMillis() + EXTRA_TIME);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		helper.authenticateUser(getLoginInput());
	}

	@Test(expected = LmsException.class)
	public void testAuthenticateUser_ExpiredPassword() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		user.setPasswordExpiryTms(System.currentTimeMillis() - EXTRA_TIME);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		helper.authenticateUser(getLoginInput());
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_OldNewPasswordSame() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword(PSWD);
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getSession());
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		UserModel lgU = new UserModel();
		lgU.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.getUserById("u1")).thenReturn(lgU);
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_NewPasswordNull() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword(null);
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getSession());
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		UserModel lgU = new UserModel();
		lgU.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.getUserById("u1")).thenReturn(lgU);
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_NewPasswordEmpty() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword("   ");
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getSession());
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		UserModel lgU = new UserModel();
		lgU.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.getUserById("u1")).thenReturn(lgU);
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_LoginIdNewPasswordSame() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword(LOGIN);
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getSession());
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		UserModel lgU = new UserModel();
		lgU.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.getUserById("u1")).thenReturn(lgU);
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_LessThanMinLength() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword("pass");
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getSession());
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		UserModel lgU = new UserModel();
		lgU.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.getUserById("u1")).thenReturn(lgU);
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_MoreThanMaxLength() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword("mypasswordisverylongthatitdoesnotfitinthedatabase");
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getSession());
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		UserModel lgU = new UserModel();
		lgU.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.getUserById("u1")).thenReturn(lgU);
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_RegexFailed() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword("simplepassword");
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getSession());
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		UserModel lgU = new UserModel();
		lgU.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.getUserById("u1")).thenReturn(lgU);
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_LoginIdEmpty() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel m = getResetPassword();
		m.setLoginId(null);
		helper.resetPassword(m);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_SessionNotInCache() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		SessionCache.removeAllSessions();
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		UserModel lgU = new UserModel();
		lgU.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.getUserById("u1")).thenReturn(lgU);
		helper.resetPassword(getResetPassword());
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_NonAdmin() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		SessionCache.removeAllSessions();
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		SessionCache.addSessionToCache(getSession());
		UserModel lgU = new UserModel();
		lgU.setUserRole(UserRoleEnum.LIBRARIAN);
		when(userService.getUserById("u1")).thenReturn(lgU);
		helper.resetPassword(getResetPassword());
	}

	@Test
	public void testResetPassword_Success() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		SessionCache.removeAllSessions();
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		SessionCache.addSessionToCache(getSession());
		UserModel lgU = new UserModel();
		lgU.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.getUserById("u1")).thenReturn(lgU);
		helper.resetPassword(getResetPassword());
	}

	@Test(expected = LmsException.class)
	public void testUpdatePassword_NewPasswordEmpty() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		user.setUserId("u1");
		SessionCache.removeAllSessions();
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		SessionCache.addSessionToCache(getSession());
		when(userService.getUserById("u1")).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setLoginId(null);
		reset.setNewPassword(null);
		helper.updatePassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testUpdatePassword_SessionNotInCache() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		user.setUserId("u1");
		SessionCache.removeAllSessions();
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		when(userService.getUserById("u1")).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setLoginId(null);
		helper.updatePassword(reset);
	}

	@Test
	public void testUpdatePassword_Success() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		user.setUserId("u1");
		SessionCache.removeAllSessions();
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		SessionCache.addSessionToCache(getSession());
		when(userService.getUserById("u1")).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setLoginId(null);
		helper.updatePassword(reset);
	}

	@Test
	public void testLogout_Success() throws LmsException {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		when(sessionService.getSession(SESSION_ID_1)).thenReturn(new Session());
		helper.logout();
	}

	@Test
	public void testLogout_Exception() throws LmsException {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		when(sessionService.getSession(SESSION_ID_1))
				.thenThrow(new LmsException(ErrorType.FAILURE, MessagesEnum.PASSWORD_LENGTH_ERROR, null, 1));
		try {
			helper.logout();
		} catch (LmsException e) {
			assertEquals("", ErrorType.FAILURE.getNumValue(), e.getErrorType().getNumValue());
			assertEquals("", "Password should be minimum  character and maximum 1 characters long.",
					e.getErrorReason());
			assertEquals("", "PASSWORD_LENGTH_ERROR", e.getErrorReasonCode());
			assertEquals("", "Operation failed: Password should be minimum  character and maximum 1 characters long.",
					e.getErrorMessage());
		}
	}

	@Test
	public void testForgotPassword() throws LmsException {
		UserModel user = new UserModel();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		user.setUserId("u1");
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		helper.forgotPassword(getLoginInput());

		when(userService.getUserByLoginId(LOGIN)).thenThrow(new LmsException(ErrorType.FAILURE, MessagesEnum.FAILURE));
		helper.forgotPassword(getLoginInput());
	}

	private LoginInputModel getLoginInput() {
		LoginInputModel model = new LoginInputModel();
		model.setLoginId(LOGIN);
		model.setPassword(PSWD);
		return model;
	}

	private ResetPasswordModel getResetPassword() {
		ResetPasswordModel r = new ResetPasswordModel();
		r.setLoginId(LOGIN);
		r.setNewPassword(NEWPSWD);
		return r;
	}

	private Session getSession() {
		Session s = new Session();
		s.setLastAccessTime(123456789L);
		s.setLoggedInIpAddress("10.1.1.1");
		s.setLoggedInTime(1234566L);
		s.setSessionId(SESSION_ID_1);
		s.setUserId("u1");
		return s;
	}
}
