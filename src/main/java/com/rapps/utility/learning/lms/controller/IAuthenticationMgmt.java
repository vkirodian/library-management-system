package com.rapps.utility.learning.lms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rapps.utility.learning.lms.annotation.Authorization;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.LoginInputModel;
import com.rapps.utility.learning.lms.model.ResetPasswordModel;
import com.rapps.utility.learning.lms.persistence.bean.Session;

/**
 * Interface providing API' related to authentication service.
 * 
 * @author vkirodian
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
	@Authorization()
	Session login(LoginInputModel login) throws LmsException;

	/**
	 * API to reset password.
	 * 
	 * @param login
	 * @throws LmsException
	 */
	@PostMapping(value = "resetPassword")
	@Authorization(roles = { UserRoleEnum.SUPER_ADMIN })
	void resetPassword(ResetPasswordModel resetPassword) throws LmsException;

	/**
	 * API to update password.
	 * 
	 * @param login
	 * @throws LmsException
	 */
	@PostMapping(value = "updatePassword")
	@Authorization(roles = { UserRoleEnum.SUPER_ADMIN })
	void updatePassword(ResetPasswordModel resetPassword) throws LmsException;

	/**
	 * API for forgot password.
	 * 
	 * @param login
	 * @throws LmsException
	 */
	@PostMapping(value = "forgotPassword")
	@Authorization()
	void forgotPassword(LoginInputModel login) throws LmsException;

	/**
	 * Logs out current user.
	 * 
	 * @throws LmsException
	 */
	@GetMapping(value = "logout")
	@Authorization(roles = { UserRoleEnum.SUPER_ADMIN, UserRoleEnum.LIBRARIAN, UserRoleEnum.USERS })
	void logout() throws LmsException;
}
