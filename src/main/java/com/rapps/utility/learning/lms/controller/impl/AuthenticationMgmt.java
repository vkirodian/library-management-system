package com.rapps.utility.learning.lms.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rapps.utility.learning.lms.controller.IAuthenticationMgmt;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.helper.AuthenticationMgmtHelper;
import com.rapps.utility.learning.lms.model.LoginInput;
import com.rapps.utility.learning.lms.model.ResetPassword;
import com.rapps.utility.learning.lms.persistence.bean.Session;

/**
 * Implementation of authentication related API'
 * 
 * @author vkirodian
 *
 */
@RestController
public class AuthenticationMgmt implements IAuthenticationMgmt {

	@Autowired
	AuthenticationMgmtHelper authenticationMgmthelper;

	@Override
	public Session login(@RequestBody LoginInput login) throws LmsException {
		return authenticationMgmthelper.authenticateUser(login);
	}

	@Override
	public void resetPassword(@RequestBody ResetPassword resetPassword) throws LmsException {
		authenticationMgmthelper.resetPassword(resetPassword);
	}

	@Override
	public void logout() throws LmsException {
		authenticationMgmthelper.logout();
	}

}
