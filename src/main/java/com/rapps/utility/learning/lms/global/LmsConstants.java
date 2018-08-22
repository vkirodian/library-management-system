package com.rapps.utility.learning.lms.global;

/**
 * Class containing all application constants.
 * 
 * @author vkirodian
 *
 */
public final class LmsConstants {

	public static final long PASSWORD_EXPIRY_TIME = 90 * 24 * 60 * 60 * 1000L; //3 Months
	public static final long INACTIVITY_TIMEOUT = 5 * 60 * 1000L; //5 minutes
	public static final String SESSION_ID = "sessionId";
	public static final long BOOK_RETURN_DURATION = 7 * 24 * 60 * 60 * 1000L; //7 Days
	public static final long BOOK_DUE_REMINDERS = 3 * 24 * 60 * 60 * 1000L; //3 Days
	public static final int BOOK_REISSUE_THRESHOLD = 2;
	public static final int MAX_WAITING_QUEUE = 5;
	public static final int PER_DAY_FINE = 5;

	private LmsConstants() {
	}
}
