package com.rapps.utility.learning.lms.helper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.enums.AccessTypeEnum;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.persistence.bean.AccessRole;
import com.rapps.utility.learning.lms.persistence.service.AccessRoleService;

@RunWith(MockitoJUnitRunner.class)
public class TestAccessRoleMgmtHelper {

	@InjectMocks
	AccessRoleMgmtHelper helper;

	@Mock
	AccessRoleService service;

	@Test(expected = LmsException.class)
	public void testGetUserRolesForAccessType_NullInput() throws LmsException {
		helper.getUserRolesForAccessType(null);
	}

	@Test
	public void testGetUserRolesForAccessType_Success() throws LmsException {
		AccessRole a1 = new AccessRole();
		a1.setAccessRoleId("1");
		a1.setAccessType(AccessTypeEnum.AUTH_ADMIN);
		a1.setRole(UserRoleEnum.LIBRARIAN);
		AccessRole a2 = new AccessRole();
		a2.setAccessRoleId("2");
		a2.setAccessType(AccessTypeEnum.AUTH_ADMIN);
		a2.setRole(UserRoleEnum.SUPER_ADMIN);
		when(service.getAccessRolesForAccessType(AccessTypeEnum.AUTH_ADMIN)).thenReturn(Arrays.asList(a1, a2));
		List<UserRoleEnum> roles = helper.getUserRolesForAccessType(AccessTypeEnum.AUTH_ADMIN);
		assertEquals("", Arrays.asList(UserRoleEnum.LIBRARIAN, UserRoleEnum.SUPER_ADMIN), roles);
	}

}
