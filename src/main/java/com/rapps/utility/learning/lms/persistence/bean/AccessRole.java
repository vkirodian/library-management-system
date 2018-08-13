package com.rapps.utility.learning.lms.persistence.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rapps.utility.learning.lms.enums.AccessTypeEnum;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;

@Entity
@Table(name = "access_role")
public class AccessRole {

	@Id
	@Column(name = "ACCESSROLEID")
	private String accessRoleId;

	@Column(name = "ACCESSTYPE")
	private AccessTypeEnum accessType;

	@Column(name = "ROLE")
	private UserRoleEnum role;

	public String getAccessRoleId() {
		return accessRoleId;
	}

	public void setAccessRoleId(String accessRoleId) {
		this.accessRoleId = accessRoleId;
	}

	public AccessTypeEnum getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessTypeEnum accessType) {
		this.accessType = accessType;
	}

	public UserRoleEnum getRole() {
		return role;
	}

	public void setRole(UserRoleEnum role) {
		this.role = role;
	}

}
