package com.rapps.utility.learning.lms.exception;

import java.text.MessageFormat;

import com.rapps.utility.learning.lms.enums.IMessages;
import com.rapps.utility.learning.lms.enums.MessagesEnum;

/**
 * Custom exception class for LMS.
 * 
 * @author vkirodian
 *
 */
@SuppressWarnings("serial")
public class LmsException extends Exception {

	private final ErrorType errorType;
	private final String errorReason;
	private final String errorReasonCode;
	private final String errorMessage;

	public LmsException(ErrorType errorType, IMessages reasonCode, Object... objects) {
		super(getErrorMessage(errorType, reasonCode, objects));
		replaceQuotes(objects);
		this.errorType = errorType;
		this.errorMessage = getErrorMessage(errorType, reasonCode, objects);
		this.errorReasonCode = reasonCode.getName();
		this.errorReason = formatErrorReason(reasonCode.getMessage(), objects);
		super.printStackTrace();
	}

	private static String getErrorMessage(final ErrorType errorType, final IMessages reasonCode,
			final Object... objects) {
		final String error = MessagesEnum.valueOf(errorType.name()).getMessage();
		final String reason = formatErrorReason(reasonCode.getMessage(), objects);
		return error + ": " + reason;
	}

	private static String formatErrorReason(final String errorReason, final Object... objects) {
		return MessageFormat.format(errorReason, objects);
	}

	private Object[] replaceQuotes(final Object... objects) {
		final CharSequence c1 = "\"";
		final CharSequence c2 = "\\\"";
		for (int itt = 0; itt < objects.length; itt++) {
			if (objects[itt] == null) {
				objects[itt] = "";
			}
			objects[itt] = objects[itt].toString().replace(c1, c2);
		}
		return objects;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public String getErrorReasonCode() {
		return errorReasonCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public enum ErrorType {
		GLOBAL_FAILURE(-3), PARTIAL_FAILURE(-2), FAILURE(-1), SUCCESS(0), INFO(1), WARNING(2);

		private ErrorType(int code) {
			this.code = code;
		}

		public int getNumValue() {
			return code;
		}

		private int code;
	}
}
