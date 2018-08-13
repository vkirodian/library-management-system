package com.rapps.utility.learning.lms.enums;

/**
 * Access type gives the type of access required for an API access.
 * 
 * @author vkirodian
 *
 */
public enum AccessTypeEnum {

	AUTH_ALL("Authentication All Access"),		//0
	AUTH_ADMIN("Authentication Admin Access"),	//1
	
	USER_ALL("User All Access"),				//2
	USER_VIEWONLY("User View-Only Access"),		//3
	USER_MGMT("User Management Access"),		//4
	
	BOOK_VIEWONLY("Book View-Only Access"),		//5
	BOOK_MGMT("Book Management Access"),		//6
	;
	
	private String message;

	private AccessTypeEnum(String message) {
		this.message = message;
	}

	String getMessage() {
		return message;
	}

	void setMessage(String message) {
		this.message = message;
	}
	
}
