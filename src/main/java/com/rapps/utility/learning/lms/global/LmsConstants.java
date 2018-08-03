package com.rapps.utility.learning.lms.global;

import java.util.Arrays;
import java.util.List;

/**
 * Class containing all application constants.
 * 
 * @author vkirodian
 *
 */
public class LmsConstants {

	public static final long PASSWORD_EXPIRY_TIME = 90 * 24 * 60 * 60 * 1000L; //3 Months
	public static final String SESSION_ID = "sessionId";
	public static final List<String> URL_SKIP_SESSION_VERIFICATION = Arrays.asList("/lms/authentication/login","/lms/authentication/resetPassword");

	private LmsConstants() {
	}
}
