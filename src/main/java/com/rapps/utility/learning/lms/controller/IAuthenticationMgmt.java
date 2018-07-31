package com.rapps.utility.learning.lms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.LoginInput;
import com.rapps.utility.learning.lms.persistence.bean.User;

/**
 * Interface providing API' related to authentication service.
 * 
 * @author vkirodia
 *
 */
@RequestMapping("/lms/authentication")
public interface IAuthenticationMgmt {

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public User login(LoginInput login) throws LmsException;
}