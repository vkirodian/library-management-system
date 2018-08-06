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

}
