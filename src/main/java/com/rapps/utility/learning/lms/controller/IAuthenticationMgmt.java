package com.rapps.utility.learning.lms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.LoginInput;
import com.rapps.utility.learning.lms.model.ResetPassword;
import com.rapps.utility.learning.lms.persistence.bean.Session;

/**
 * Interface providing API' related to authentication service.
 * 
 * @author vkirodia
 *
 */
@RequestMapping("/lms/authentication")
public interface IAuthenticationMgmt {

	/**
	 * API for logging into the application.
	 * 
	 * @param login
	 *            Credentials
	 * @return
	 * @throws LmsException
	 */
	@PostMapping(value = "login")
	public Session login(LoginInput login) throws LmsException;

	/**
	 * API to reset password.
	 * 
	 * @param login
	 * @throws LmsException
	 */
	@PostMapping(value = "resetPassword")
	public void resetPassword(ResetPassword resetPassword) throws LmsException;
}
