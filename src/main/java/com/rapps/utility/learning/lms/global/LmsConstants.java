package com.rapps.utility.learning.lms.global;

/**
 * Class containing all application constants.
 * 
 * @author vkirodian
 *
 */
public class LmsConstants {

	public static final long PASSWORD_EXPIRY_TIME = 90 * 24 * 60 * 60 * 1000L; //3 Months
	public static final long INACTIVITY_TIMEOUT = 1 * 60 * 1000L; //5 minutes
	public static final String SESSION_ID = "sessionId";

	private LmsConstants() {
	}
}
