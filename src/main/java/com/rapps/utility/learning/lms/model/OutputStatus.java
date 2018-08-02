package com.rapps.utility.learning.lms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rapps.utility.learning.lms.exception.LmsException;

/**
 * Class holding the status of the output.
 * 
 * @author vkirodia
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutputStatus {

	private LmsException.ErrorType errorType;
	private String errorMsg;
	private String errorReason;
	private String errorReasonCode;
	private String[] messageParams;

	public LmsException.ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(LmsException.ErrorType errorType) {
		this.errorType = errorType;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	public String getErrorReasonCode() {
		return errorReasonCode;
	}

	public void setErrorReasonCode(String errorReasonCode) {
		this.errorReasonCode = errorReasonCode;
	}

	public String[] getMessageParams() {
		return messageParams;
	}

	public void setMessageParams(String[] messageParams) {
		this.messageParams = messageParams;
	}

}
