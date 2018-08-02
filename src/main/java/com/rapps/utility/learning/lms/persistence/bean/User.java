package com.rapps.utility.learning.lms.persistence.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "user")
@JsonInclude(Include.NON_NULL)
public class User {

	@Id
	@Column(name = "USERID")
	private String userId;

	@Column(name = "LOGINID")
	private String loginId;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "PASSWORDEXPIRYTMS")
	private long passwordExpiryTms;

	@Column(name = "EMAILID")
	private String emailId;

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

	@Override
	public String toString() {
		return "User [userId=" + userId + ", loginId=" + loginId + ", password=" + password + ", passwordExpiryTms="
				+ passwordExpiryTms + ", emailId=" + emailId + "]";
	}
}
