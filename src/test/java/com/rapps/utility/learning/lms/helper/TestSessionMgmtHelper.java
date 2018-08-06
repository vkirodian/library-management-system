package com.rapps.utility.learning.lms.helper;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.bean.User;
import com.rapps.utility.learning.lms.persistence.service.SessionService;
import com.rapps.utility.learning.lms.persistence.service.UserService;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestSessionMgmtHelper extends TestCase {

	@InjectMocks
	SessionMgmtHelper helper;

	@Mock
	SessionService sessionService;
	@Mock
	UserService userService;

	@Test(expected = LmsException.class)
	public void testGetRoleForUserSession_Exception() throws LmsException {
		Session s = new Session();
		s.setLastAccessTime(0L);
		helper.getRoleForUserSession(s);
	}

	@Test
	public void testGetRoleForUserSession_Success() throws LmsException {
		Session s = new Session();
		s.setLastAccessTime(System.currentTimeMillis());
		s.setUserId("u1");
		User u = new User();
		u.setUserId("u1");
		u.setUserRole(UserRoleEnum.SUPER_ADMIN);
		when(userService.getUserById("u1")).thenReturn(u);
		UserRoleEnum r = helper.getRoleForUserSession(s);
		assertEquals("", UserRoleEnum.SUPER_ADMIN, r);
	}

	@Test
	public void testUpdateLastAccessTime() throws LmsException {
		helper.updateLastAccessTime(new Session());
	}

	@Test
	public void testCleanSessionOnStartup() {
		helper.cleanSessionOnStartup();
	}

}
