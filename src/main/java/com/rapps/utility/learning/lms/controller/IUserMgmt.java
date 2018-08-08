package com.rapps.utility.learning.lms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	User getUserDetails() throws LmsException;

	/**
	 * Get list of all users in the system.
	 * 
	 * @return List of Users
	 * @throws LmsException
	 */
	@GetMapping(value = "users")
	List<User> getUsers() throws LmsException;

	/**
	 * Update the given user.
	 * 
	 * @param user
	 *            User
	 * @return Updated user
	 * @throws LmsException
	 *             If user does not exist
	 */

	@PutMapping(value = "updateUser")
	User updateUser(User user) throws LmsException;
}
