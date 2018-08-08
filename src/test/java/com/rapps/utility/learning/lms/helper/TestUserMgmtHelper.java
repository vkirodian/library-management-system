package com.rapps.utility.learning.lms.helper;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.bean.User;
import com.rapps.utility.learning.lms.persistence.service.SessionService;
import com.rapps.utility.learning.lms.persistence.service.UserService;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestUserMgmtHelper extends TestCase {

	@InjectMocks
	UserMgmtHelper helper;

	@Mock
	SessionService sessionService;
	@Mock
	UserService userService;
	@Mock
	HttpServletRequest httpRequest;
	@Mock
	AuthenticationMgmtHelper authenticationMgmtHelper;

	@Test(expected = LmsException.class)
	public void testGetUserDetails_Exception() throws LmsException {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(null);
		helper.getUserDetails();
	}

	@Test
	public void testGetUserDetails_Success() throws LmsException {
		Session s = new Session();
		s.setSessionId("Session_id_1");
		s.setUserId("u1");
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn("Session_id_1");
		when(sessionService.getSession("Session_id_1")).thenReturn(s);
		when(userService.getUserById("u1")).thenReturn(new User());
		User u = helper.getUserDetails();
		assertNotNull("", u);
	}

	@Test
	public void testGetUsers() {
		List<User> users = Arrays.asList(new User(), new User());
		when(userService.getUsers()).thenReturn(users);
	}

	@Test(expected = LmsException.class)
	public void testUpdateUser_UserNotFound() throws LmsException {
		User u = new User();
		u.setUserId("u1");
		when(userService.getUserById("u1")).thenThrow(new LmsException());
		helper.updateUser(u);
	}

	@Test(expected = LmsException.class)
	public void testUpdateUser_NonAdminUpdatingOtherUser() throws LmsException {
		User methodInput = new User();
		methodInput.setUserId("u1");
		methodInput.setLoginId("l1");

		User loggedInUser = new User();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.LIBRARIAN);

		when(userService.getUserById("u1")).thenReturn(methodInput);
		getSession();
		when(userService.getUserById("lg1")).thenReturn(loggedInUser);

		helper.updateUser(methodInput);
	}

	@Test(expected = LmsException.class)
	public void testUpdateUser_NonAdminUpdatingRole() throws LmsException {
		User methodInput = new User();
		methodInput.setUserId("lg1");
		methodInput.setLoginId("logged");
		methodInput.setUserRole(UserRoleEnum.USERS);

		User loggedInUser = new User();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.LIBRARIAN);

		getSession();
		when(userService.getUserById("lg1")).thenReturn(loggedInUser);

		helper.updateUser(methodInput);
	}

	@Test(expected = LmsException.class)
	public void testUpdateUser_LoginIdUpdated() throws LmsException {
		User methodInput = new User();
		methodInput.setUserId("lg1");
		methodInput.setLoginId("logged_1");

		User loggedInUser = new User();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.LIBRARIAN);

		getSession();
		when(userService.getUserById("lg1")).thenReturn(loggedInUser);

		helper.updateUser(methodInput);
	}

	@Test
	public void testUpdateUser_PasswordEmpty() throws LmsException {
		User methodInput = new User();
		methodInput.setUserId("lg1");
		methodInput.setLoginId("logged");
		methodInput.setEmailId("newemail.lms.com");

		User loggedInUser = new User();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.LIBRARIAN);

		getSession();
		when(userService.getUserById("lg1")).thenReturn(loggedInUser);
		when(userService.updateUser(methodInput)).thenReturn(methodInput);

		User actual = helper.updateUser(methodInput);
		assertEquals("", actual.getEmailId(), "newemail.lms.com");

	}

	@Test
	public void testUpdateUser_NonAdminUserSuccess() throws LmsException {
		User methodInput = new User();
		methodInput.setUserId("lg1");
		methodInput.setLoginId("logged");
		methodInput.setEmailId("newemail.lms.com");
		methodInput.setPassword("newpassword");

		User loggedInUser = new User();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.LIBRARIAN);

		getSession();
		when(userService.getUserById("lg1")).thenReturn(loggedInUser);
		when(userService.updateUser(methodInput)).thenReturn(methodInput);

		User actual = helper.updateUser(methodInput);
		assertEquals("", actual.getEmailId(), "newemail.lms.com");
		assertEquals("", actual.getPassword(), "newpassword");
		assertTrue("", actual.getPasswordExpiryTms() > System.currentTimeMillis());
	}

	@Test
	public void testUpdateUser_AdminUserSuccess() throws LmsException {
		User methodInput = new User();
		methodInput.setUserId("u1");
		methodInput.setLoginId("logged");
		methodInput.setEmailId("newemail.lms.com");
		methodInput.setUserRole(UserRoleEnum.USERS);

		User loggedInUser = new User();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.SUPER_ADMIN);

		getSession();
		when(userService.getUserById("u1")).thenReturn(methodInput);
		when(userService.getUserById("lg1")).thenReturn(loggedInUser);
		when(userService.updateUser(methodInput)).thenReturn(methodInput);

		User actual = helper.updateUser(methodInput);
		assertEquals("", actual.getEmailId(), "newemail.lms.com");
		assertEquals("", actual.getUserRole(), UserRoleEnum.USERS);
	}

	private Session getSession() throws LmsException {
		Session s = new Session();
		s.setSessionId("s1");
		s.setUserId("lg1");
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn("s1");
		when(sessionService.getSession("s1")).thenReturn(s);
		return s;
	}
}
