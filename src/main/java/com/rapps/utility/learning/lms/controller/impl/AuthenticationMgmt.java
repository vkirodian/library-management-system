package com.rapps.utility.learning.lms.controller.impl;

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

	@Autowired
	AuthenticationMgmtHelper authenticationMgmthelper;

	@Override
	public User login(@RequestBody LoginInput login) throws LmsException {
		System.out.println(login.getLoginId() + " " + login.getPassword());
		return authenticationMgmthelper.authenticateUser(login.getLoginId(), login.getPassword());
	}

}
