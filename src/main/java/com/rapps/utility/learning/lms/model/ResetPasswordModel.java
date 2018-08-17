package com.rapps.utility.learning.lms.model;

/**
 * 
 * @author vkirodian
 *
 */
public class ResetPasswordModel {

	private String loginId;
	private String newPassword;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
