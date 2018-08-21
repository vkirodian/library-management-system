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
import com.rapps.utility.learning.lms.global.SessionCache;
import com.rapps.utility.learning.lms.model.UserModel;
import com.rapps.utility.learning.lms.persistence.bean.Session;
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
		when(userService.getUserById("u1")).thenReturn(new UserModel());
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(s);
		UserModel u = helper.getUserDetails();
		assertNotNull("", u);
	}

	@Test
	public void testGetUsers() {
		UserModel filter = new UserModel();
		filter.setLoginId("admin");
		List<UserModel> users = Arrays.asList(new UserModel(), new UserModel());
		when(userService.getUsers(filter)).thenReturn(users);
		List<UserModel> accUsers = helper.getUsers(filter);
		assertEquals("", users, accUsers);
	}

	@Test(expected = LmsException.class)
	public void testUpdateUser_UserNotFound() throws LmsException {
		UserModel u = new UserModel();
		u.setUserId("u1");
		when(userService.getUserById("u1")).thenThrow(new LmsException());
		helper.updateUser(u);
	}

	@Test(expected = LmsException.class)
	public void testUpdateUser_NonAdminUpdatingOtherUser() throws LmsException {
		UserModel methodInput = new UserModel();
		methodInput.setUserId("u1");
		methodInput.setLoginId("l1");

		UserModel loggedInUser = new UserModel();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.LIBRARIAN);

		when(userService.getUserById("u1")).thenReturn(methodInput);
		Session s = getSession();
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(s);
		when(userService.getUserById("lg1")).thenReturn(loggedInUser);

		helper.updateUser(methodInput);
	}

	@Test(expected = LmsException.class)
	public void testUpdateUser_NonAdminUpdatingRole() throws LmsException {
		UserModel methodInput = new UserModel();
		methodInput.setUserId("lg1");
		methodInput.setLoginId("logged");
		methodInput.setUserRole(UserRoleEnum.USERS);

		UserModel loggedInUser = new UserModel();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.LIBRARIAN);

		Session s = getSession();
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(s);
		when(userService.getUserById("lg1")).thenReturn(loggedInUser);

		helper.updateUser(methodInput);
	}

	@Test(expected = LmsException.class)
	public void testUpdateUser_LoginIdUpdated() throws LmsException {
		UserModel methodInput = new UserModel();
		methodInput.setUserId("lg1");
		methodInput.setLoginId("logged_1");

		UserModel loggedInUser = new UserModel();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.LIBRARIAN);

		Session s = getSession();
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(s);
		when(userService.getUserById("lg1")).thenReturn(loggedInUser);

		helper.updateUser(methodInput);
	}

	@Test
	public void testUpdateUser_PasswordEmpty() throws LmsException {
		UserModel methodInput = new UserModel();
		methodInput.setUserId("lg1");
		methodInput.setLoginId("logged");
		methodInput.setEmailId("newemail.lms.com");

		UserModel loggedInUser = new UserModel();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.LIBRARIAN);

		when(userService.getUserById("lg1")).thenReturn(loggedInUser);
		when(userService.updateUser(methodInput)).thenReturn(methodInput);

		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn("s1");
		Session s = new Session();
		s.setSessionId("s1");
		s.setUserId("lg1");
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(s);

		UserModel actual = helper.updateUser(methodInput);
		assertEquals("", actual.getEmailId(), "newemail.lms.com");

	}

	@Test
	public void testUpdateUser_NonAdminUserSuccess() throws LmsException {
		UserModel methodInput = new UserModel();
		methodInput.setUserId("lg1");
		methodInput.setLoginId("logged");
		methodInput.setEmailId("newemail.lms.com");
		methodInput.setPassword("newpassword");

		UserModel loggedInUser = new UserModel();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.LIBRARIAN);

		when(userService.getUserById("lg1")).thenReturn(loggedInUser);
		when(userService.updateUser(methodInput)).thenReturn(methodInput);

		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn("s1");
		Session s = new Session();
		s.setSessionId("s1");
		s.setUserId("lg1");
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(s);
		
		UserModel actual = helper.updateUser(methodInput);
		assertEquals("", actual.getEmailId(), "newemail.lms.com");
		assertEquals("", actual.getPassword(), "newpassword");
		assertTrue("", actual.getPasswordExpiryTms() > System.currentTimeMillis());
	}

	@Test
	public void testUpdateUser_AdminUserSuccess() throws LmsException {
		UserModel methodInput = new UserModel();
		methodInput.setUserId("u1");
		methodInput.setEmailId("newemail.lms.com");
		methodInput.setUserRole(UserRoleEnum.USERS);

		UserModel loggedInUser = new UserModel();
		loggedInUser.setUserId("lg1");
		loggedInUser.setLoginId("logged");
		loggedInUser.setUserRole(UserRoleEnum.SUPER_ADMIN);

		when(userService.getUserById("u1")).thenReturn(methodInput);
		when(userService.getUserById("lg1")).thenReturn(loggedInUser);
		when(userService.updateUser(methodInput)).thenReturn(methodInput);

		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn("s1");
		Session s = new Session();
		s.setSessionId("s1");
		s.setUserId("lg1");
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(s);
		
		UserModel actual = helper.updateUser(methodInput);
		assertEquals("", actual.getEmailId(), "newemail.lms.com");
		assertEquals("", actual.getUserRole(), UserRoleEnum.USERS);
	}

	@Test(expected = LmsException.class)
	public void testAddUser_LoginIdEmpty() throws LmsException {
		UserModel u = new UserModel();
		u.setPassword("AdmiN@123");
		u.setUserRole(UserRoleEnum.SUPER_ADMIN);
		helper.addUser(u);
	}

	@Test(expected = LmsException.class)
	public void testAddUser_PasswordEmpty() throws LmsException {
		UserModel u = new UserModel();
		u.setLoginId("admin");
		u.setUserRole(UserRoleEnum.SUPER_ADMIN);
		helper.addUser(u);
	}

	@Test(expected = LmsException.class)
	public void testAddUser_RoleEmpty() throws LmsException {
		UserModel u = new UserModel();
		u.setLoginId("admin");
		u.setPassword("AdmiN@123");
		helper.addUser(u);
	}

	@Test
	public void testAddUser_Success() throws LmsException {
		UserModel u = new UserModel();
		u.setLoginId("admin");
		u.setPassword("AdmiN@123");
		u.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.saveUser(u)).thenReturn(u);
		UserModel acc = helper.addUser(u);
		assertEquals("", acc, u);
	}

	@Test(expected = LmsException.class)
	public void testGetUser_IdNull() throws LmsException {
		helper.getUser(null);
	}

	@Test
	public void testGetUser_IdSuccess() throws LmsException {
		UserModel u = new UserModel();
		u.setLoginId("admin");
		u.setPassword("AdmiN@123");
		u.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.getUserById("uid")).thenReturn(u);
		UserModel acc = helper.getUser("uid");
		assertEquals("", acc, u);
	}

	@Test(expected = LmsException.class)
	public void testDeleteUser_IdNull() throws LmsException {
		helper.deleteUser(null);
	}

	@Test(expected = LmsException.class)
	public void testDeleteUser_SelfDelete() throws LmsException {
		Session s = new Session();
		s.setSessionId("Session_id_1");
		s.setUserId("u1");

		UserModel u = new UserModel();
		u.setUserId("u1");
		u.setLoginId("admin");
		u.setPassword("AdmiN@123");
		u.setUserRole(UserRoleEnum.SUPER_ADMIN);

		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn("Session_id_1");
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(s);
		when(userService.getUserById("u1")).thenReturn(u);
		helper.deleteUser("u1");
	}

	@Test
	public void testDeleteUser_Success() throws LmsException {
		Session s = new Session();
		s.setSessionId("Session_id_1");
		s.setUserId("u1");

		UserModel u = new UserModel();
		u.setUserId("u1");
		u.setLoginId("admin");
		u.setPassword("AdmiN@123");
		u.setUserRole(UserRoleEnum.SUPER_ADMIN);

		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn("Session_id_1");
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(s);
		when(userService.getUserById("u1")).thenReturn(u);
		helper.deleteUser("u2");
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
