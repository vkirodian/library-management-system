package com.rapps.utility.learning.lms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rapps.utility.learning.lms.annotation.Authorization;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.UserModel;

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
	 *             If session is invalid or User not found
	 */
	@GetMapping(value = "userDetails")
	@Authorization(roles = { UserRoleEnum.SUPER_ADMIN, UserRoleEnum.LIBRARIAN, UserRoleEnum.USERS })
	UserModel getUserDetails() throws LmsException;

	/**
	 * Get User.
	 * 
	 * @param uid
	 *            User ID
	 * @return User
	 * @throws LmsException
	 *             If User not found
	 */
	@GetMapping(value = "users/{uid}")
	@Authorization(roles = { UserRoleEnum.SUPER_ADMIN, UserRoleEnum.LIBRARIAN })
	UserModel getUser(String uid) throws LmsException;

	/**
	 * Get list of all users in the system.
	 * 
	 * @param filter
	 *            User Filter
	 * @return List of Users
	 * 
	 */
	@GetMapping(value = "users")
	@Authorization(roles = { UserRoleEnum.SUPER_ADMIN })
	List<UserModel> getUsers(UserModel filter);

	/**
	 * Add a new User.
	 * 
	 * @param user
	 *            User
	 * @return Added User
	 * @throws LmsException
	 *             If mandatory fields missing
	 */
	@PostMapping(value = "add")
	@Authorization(roles = { UserRoleEnum.SUPER_ADMIN })
	UserModel addUser(UserModel user) throws LmsException;

	/**
	 * Delete a User.
	 * 
	 * @param uid
	 *            User ID
	 * @throws LmsException
	 *             If User not found
	 */
	@DeleteMapping(value = "users/{uid}")
	@Authorization(roles = { UserRoleEnum.SUPER_ADMIN })
	void deleteUser(String uid) throws LmsException;

	/**
	 * Update the given user.
	 * 
	 * @param user
	 *            User
	 * @return Updated user
	 * @throws LmsException
	 *             If user does not exist
	 */

	@PutMapping(value = "update")
	@Authorization(roles = { UserRoleEnum.SUPER_ADMIN, UserRoleEnum.LIBRARIAN, UserRoleEnum.USERS })
	UserModel updateUser(UserModel user) throws LmsException;
}
