package com.rapps.utility.learning.lms.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User roles.
 * 
 * @author vkirodian
 *
 */
public enum UserRoleEnum {

	SUPER_ADMIN, 
	LIBRARIAN, 
	USERS;

	UserRoleEnum() {
	}

	public static List<UserRoleEnum> getAllRoles() {
		return Arrays.asList(UserRoleEnum.values());
	}

	public static List<UserRoleEnum> getNonAdminRoles() {
		ArrayList<UserRoleEnum> nonAdminRoles = new ArrayList<>(getAllRoles());
		nonAdminRoles.remove(SUPER_ADMIN);
		return nonAdminRoles;
	}

	public static List<UserRoleEnum> getBasicAccessUserRoles() {
		ArrayList<UserRoleEnum> basicAccessUserRoles = new ArrayList<>(getNonAdminRoles());
		basicAccessUserRoles.remove(LIBRARIAN);
		return basicAccessUserRoles;
	}
}
