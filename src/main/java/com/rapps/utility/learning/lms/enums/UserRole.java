package com.rapps.utility.learning.lms.enums;

/**
 * User roles.
 * 
 * @author vkirodian
 *
 */
public enum UserRole {
	
	SUPER_ADMIN(0),
	LIBRARIAN(1),
	USERS(2),
	;
	
	private int ordinal;
	
	UserRole(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal() {
		return ordinal;
	}
}
