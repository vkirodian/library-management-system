package com.rapps.utility.learning.lms.enums;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

public class TestUserRoleEnum extends TestCase {

	private static final Logger LOG = LoggerFactory.getLogger(TestUserRoleEnum.class);

	@Test
	public void testGetAllRoles() {
		LOG.info("****ALL ROLES********");
		List<UserRoleEnum> roles = UserRoleEnum.getAllRoles();
		for (UserRoleEnum role : roles) {
			LOG.info("{}", role);
		}
	}

	@Test
	public void testGetNonAdminRoles() {
		LOG.info("****NON ADMIN*******");
		List<UserRoleEnum> roles = UserRoleEnum.getNonAdminRoles();
		for (UserRoleEnum role : roles) {
			LOG.info("{}", role);
		}
	}

	@Test
	public void testGetBasicAccessUserRoles() {
		LOG.info("*****BASIC USER******");
		List<UserRoleEnum> roles = UserRoleEnum.getBasicAccessUserRoles();
		for (UserRoleEnum role : roles) {
			LOG.info("{}", role);
		}
	}

}
