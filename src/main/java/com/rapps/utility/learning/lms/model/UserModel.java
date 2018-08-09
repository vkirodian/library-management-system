package com.rapps.utility.learning.lms.model;

import com.rapps.utility.learning.lms.enums.UserRoleEnum;

/**
 * Model class for User.
 * 
 * @author vkirodian
 *
 */
public class UserModel {

	private String userId;
	private String loginId;
	private String password;
	private long passwordExpiryTms;
	private String emailId;
	private UserRoleEnum userRole;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPasswordExpiryTms() {
		return passwordExpiryTms;
	}

	public void setPasswordExpiryTms(long passwordExpiryTms) {
		this.passwordExpiryTms = passwordExpiryTms;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public UserRoleEnum getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoleEnum userRole) {
		this.userRole = userRole;
	}

}
