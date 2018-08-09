package com.rapps.utility.learning.lms.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rapps.utility.learning.lms.controller.IUserMgmt;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.helper.UserMgmtHelper;
import com.rapps.utility.learning.lms.model.UserModel;

/**
 * Implementation of User management related API'.
 * 
 * @author vkirodian
 *
 */
@RestController
public class UserMgmt implements IUserMgmt {

	@Autowired
	UserMgmtHelper userMgmtHelper;

	@Override
	public UserModel getUserDetails() throws LmsException {
		return userMgmtHelper.getUserDetails();
	}

	@Override
	public List<UserModel> getUsers(UserModel filter) {
		return userMgmtHelper.getUsers(filter);
	}
	
	@Override
	public UserModel getUser(@PathVariable("uid") String uid) throws LmsException {
		return userMgmtHelper.getUser(uid);
	}

	@Override
	public UserModel updateUser(@RequestBody UserModel user) throws LmsException {
		return userMgmtHelper.updateUser(user);
	}

	@Override
	public UserModel addUser(@RequestBody UserModel user) throws LmsException {
		return userMgmtHelper.addUser(user);
	}

	@Override
	public void deleteUser(@PathVariable("uid") String uid) throws LmsException {
		userMgmtHelper.deleteUser(uid);
	}

}
