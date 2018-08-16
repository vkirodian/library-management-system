package com.rapps.utility.learning.lms.enums;

/**
 * Messages to be sent out to UI.
 * 
 * @author vkirodian
 *
 */
public enum MessagesEnum {
	
	GLOBAL_FAILURE("System error"),
	PARTIAL_FAILURE("Operation is partially successful"),
	FAILURE("Operation failed"),
	SUCCESS("Operation succeeded"),
	INFO("Additional information"),
	WARNING("Warning"),
	
	//Common Errors
	INTERNAL_ERROR("Server error. Cause {0}"),
	INPUT_PARAM_EMPTY("Input parameter {0} is mandatory for item of type {1}"),
	ENTITY_TO_DELETE_NOT_FOUND("Unable to delete item with ID {0}, not found."),
	ENTITY_WITH_ID_NOT_FOUND("Item with ID {0} not found."),
	
	//Authentication messages
	LOGIN_FAILED("Incorrect credentials entered."),
	PASSWORD_EXPIRED("Your password has expired, please reset it and login again"),
	OLD_NEW_PASWORD_SAME("Old and New password cannot be same."),
	PASSWORD_NULL_EMPTY("Password cannot be empty"),
	PASSWORD_USERNAME_SAME("Password cannot be same as Login ID."),
	PASSWORD_LENGTH_ERROR("Password should be minimum {0} character and maximum {1} characters long."),
	PASSWORD_CHARS_ERROR("Password must contain atleast one character in big and small case, a number and a special character {0}"),
	
	//Access Role Service
	NO_ACCESS_ROLE_FOR_TYPE("No Access Role was found for the provided Access Type {0}"),
	
	//User Service
	USER_NOT_FOUND("User not found."),
	CANNOT_UPDATE_LOGINID("Login ID cannot be updated for a user."),
	CANNOT_UPDATE_USER("User {0} cannot update {1} for {2}."),
	SELF_DELETION_NOT_ALLOWED("Self deletion not allowed."),
	
	//Session Service
	SESSION_NOT_FOUND("Session not found."),
	SESSION_MISSING("Session information is missing in the request or is invalid."),
	SESSION_EXPIRED("User session expired."),

	//Book Issue Service
	BOOK_OUT_OF_STOCK("Requested book {0} is out of stock, please submit a wating queue request."),
	BOOK_NOT_ISSUED("Requested book {0} is not currently issued by user {1}."),
	BOOK_ALREADY_ISSUED("Request book {0} is already issued to user."),
	REISSUE_THRESHOLD_REACHED("Re-Issue threshold reached, you have already reissued this book {0} times."),
	LONG_WAITING_LIST("Cannot re-issue book {0}, there is a waiting list for it."),
	CANNOT_ADD_TO_WAITING_BOOK_AVAILABLE("Cannot add request for waiting list, Book {0} is available."),
	;

	private String message;

	MessagesEnum(String message) {
		this.message = message;
	}

	public String getName() {
		return this.name();
	}

	public String getMessage() {
		return message;
	}

}
