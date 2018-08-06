package com.rapps.utility.learning.lms.helper;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.model.LoginInputModel;
import com.rapps.utility.learning.lms.model.ResetPasswordModel;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.bean.User;
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

	@Test
	public void testAuthenticateUser_Success() throws LmsException {
		User user = new User();
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
	public void testAuthenticateUser_IncorrectCreds() throws LmsException {
		User user = new User();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD + " error");
		user.setPasswordExpiryTms(System.currentTimeMillis() + EXTRA_TIME);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		helper.authenticateUser(getLoginInput());
	}

	@Test(expected = LmsException.class)
	public void testAuthenticateUser_ExpiredPassword() throws LmsException {
		User user = new User();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		user.setPasswordExpiryTms(System.currentTimeMillis() - EXTRA_TIME);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		helper.authenticateUser(getLoginInput());
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_OldPasswordIncorrect() throws LmsException {
		User user = new User();
		user.setLoginId(LOGIN);
		user.setPassword("admin");
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		helper.resetPassword(getResetPassword());
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_OldNewPasswordSame() throws LmsException {
		User user = new User();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword(PSWD);
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_NewPasswordNull() throws LmsException {
		User user = new User();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword(null);
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_NewPasswordEmpty() throws LmsException {
		User user = new User();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword("   ");
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_LoginIdNewPasswordSame() throws LmsException {
		User user = new User();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword(LOGIN);
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_LessThanMinLength() throws LmsException {
		User user = new User();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword("pass");
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_MoreThanMaxLength() throws LmsException {
		User user = new User();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword("mypasswordisverylongthatitdoesnotfitinthedatabase");
		helper.resetPassword(reset);
	}

	@Test(expected = LmsException.class)
	public void testResetPassword_RegexFailed() throws LmsException {
		User user = new User();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		ResetPasswordModel reset = getResetPassword();
		reset.setNewPassword("simplepassword");
		helper.resetPassword(reset);
	}

	@Test
	public void testResetPassword_Success() throws LmsException {
		User user = new User();
		user.setLoginId(LOGIN);
		user.setPassword(PSWD);
		when(userService.getUserByLoginId(LOGIN)).thenReturn(user);
		helper.resetPassword(getResetPassword());
	}

	@Test
	public void testLogout_Success() throws LmsException {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		when(sessionService.getSession(SESSION_ID_1)).thenReturn(new Session());
		helper.logout();
	}

	@Test(expected = LmsException.class)
	public void testLogout_Exception() throws LmsException {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(SESSION_ID_1);
		when(sessionService.getSession(SESSION_ID_1))
				.thenThrow(new LmsException(ErrorType.FAILURE, MessagesEnum.FAILURE));
		helper.logout();
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
		r.setOldPassword(PSWD);
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
