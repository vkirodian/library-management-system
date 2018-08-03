package com.rapps.utility.learning.lms.controller.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.rapps.utility.learning.lms.controller.IUserMgmt;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.persistence.bean.User;

/**
 * Implementation of User management related API'.
 * 
 * @author vkirodian
 *
 */
@RestController
public class UserMgmt implements IUserMgmt {

	@Override
	public User getUserDetails() throws LmsException {
		return null;
	}

	@Override
	public List<User> getUsers() throws LmsException {
		return null;
	}

}
