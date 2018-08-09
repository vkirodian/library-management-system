package com.rapps.utility.learning.lms.controller.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.helper.UserMgmtHelper;
import com.rapps.utility.learning.lms.model.UserModel;

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
		mgmt.getUsers(new UserModel());
	}

	@Test
	public void testUpdateUser() throws LmsException {
		mgmt.updateUser(new UserModel());
	}
	
	@Test
	public void testGetUser() throws LmsException {
		mgmt.getUser("id");
	}
	
	@Test
	public void testAddUser() throws LmsException {
		mgmt.addUser(new UserModel());
	}
	
	@Test
	public void testDeleteUser() throws LmsException {
		mgmt.deleteUser("u1");
	}

}
