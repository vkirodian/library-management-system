package com.rapps.utility.learning.lms.enums;

/**
 * Messages to be sent out to UI.
 * 
 * @author vkirodian
 *
 */
public enum MessagesEnum implements IMessages {
	
	GLOBAL_FAILURE("System error"),
	PARTIAL_FAILURE("Operation is partially successful"),
	FAILURE("Operation failed"),
	SUCCESS("Operation succeeded"),
	INFO("Additional information"),
	WARNING("Warning"),
	
	//Authentication messages
	LOGIN_FAILED("Incorrect credentials entered."),
	PASSWORD_EXPIRED("Your password has expired, please reset it and login again"),
	SESSION_MISSING("Session information is missing in the request or is invalid."),
	
	
	//User Service
	USER_NOT_FOUND("User not found."),
	
	//Session Service
	SESSION_NOT_FOUND("Session not found."),
	
	//
	INTERNAL_ERROR("Server error. Cause {0}"),
	;

	private String message;

	MessagesEnum(String message) {
		this.message = message;
	}

	@Override
	public String getName() {
		return this.name();
	}

	@Override
	public String getMessage() {
		return message;
	}

}
