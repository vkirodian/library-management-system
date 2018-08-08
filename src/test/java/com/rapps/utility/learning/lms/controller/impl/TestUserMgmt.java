package com.rapps.utility.learning.lms.controller.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.helper.UserMgmtHelper;
import com.rapps.utility.learning.lms.persistence.bean.User;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestUserMgmt extends TestCase {

	@InjectMocks
	UserMgmt mgmt;

	@Mock
	UserMgmtHelper userMgmtHelper;

	@Test
	public void testGetUserDetails() throws LmsException {
		mgmt.getUserDetails();
	}

	@Test
	public void testGetUsers() throws LmsException {
		mgmt.getUsers();
	}

	@Test
	public void testUpdateUser() throws LmsException {
		mgmt.updateUser(new User());
	}

}
