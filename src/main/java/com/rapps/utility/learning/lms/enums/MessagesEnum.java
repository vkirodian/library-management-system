package com.rapps.utility.learning.lms.enums;

public enum MessagesEnum implements IMessages {
	
	GLOBAL_FAILURE("System error"),
	PARTIAL_FAILURE("Operation is partially successful"),
	FAILURE("Operation failed"),
	SUCCESS("Operation succeeded"),
	INFO("Additional information"),
	WARNING("Warning"),
	
	//authentication messages
	LOGIN_FAILED("Login failed incorrect credentials entered."),
	
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
