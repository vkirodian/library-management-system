package com.rapps.utility.learning.lms.controller.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.helper.AuthenticationMgmtHelper;
import com.rapps.utility.learning.lms.model.LoginInputModel;
import com.rapps.utility.learning.lms.model.ResetPasswordModel;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestAuthenticationMgmt extends TestCase {

	@InjectMocks
	AuthenticationMgmt mgmt;

	@Mock
	AuthenticationMgmtHelper authenticationMgmthelper;

	@Test
	public void testLogin() throws LmsException {
		mgmt.login(new LoginInputModel());
	}

	@Test
	public void testResetPassword() throws LmsException {
		mgmt.resetPassword(new ResetPasswordModel());
	}

	@Test
	public void testUpdatePassword() throws LmsException {
		mgmt.updatePassword(new ResetPasswordModel());
	}

	@Test
	public void testForgotPassword() throws LmsException {
		mgmt.forgotPassword(new LoginInputModel());
	}

	@Test
	public void testLogout() throws LmsException {
		mgmt.logout();
	}

}
