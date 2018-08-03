package com.rapps.utility.learning.lms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.persistence.bean.User;

/**
 * Interface providing API' related to user service.
 * 
 * @author vkirodian
 *
 */
@RequestMapping("/lms/user")
public interface IUserMgmt {

	/**
	 * Get User details for the current logged-in user.
	 * 
	 * @return User
	 * @throws LmsException
	 */
	@GetMapping(value = "userDetails")
	public User getUserDetails() throws LmsException;

	/**
	 * Get list of all users in the system.
	 * 
	 * @return List of Users
	 * @throws LmsException
	 */
	@GetMapping(value = "users")
	public List<User> getUsers() throws LmsException;
}