package com.rapps.utility.learning.lms.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rapps.utility.learning.lms.controller.IAuthenticationMgmt;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.helper.AuthenticationMgmtHelper;
import com.rapps.utility.learning.lms.model.LoginInput;
import com.rapps.utility.learning.lms.persistence.bean.User;

/**
 * Implementation of authentication related API'
 * 
 * @author vkirodia
 *
 */
@RestController
public class AuthenticationMgmt implements IAuthenticationMgmt {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationMgmt.class);

	@Autowired
	AuthenticationMgmtHelper authenticationMgmthelper;

	@Override
	public User login(@RequestBody LoginInput login) throws LmsException {
		return authenticationMgmthelper.authenticateUser(login.getLoginId(), login.getPassword());
	}

}
