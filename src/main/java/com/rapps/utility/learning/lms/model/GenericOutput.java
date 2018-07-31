package com.rapps.utility.learning.lms.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericOutput {

	private LmsException.ErrorType errorType;
	private String errorMsg;
	private String errorReason;
	private String errorReasonCode;
	private String[] messageParams;

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
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

	@Override
	public String toString() {
		return "ErrorStatus [errorType=" + errorType + ", errorMsg=" + errorMsg + ", errorReason=" + errorReason
				+ ", errorReasonCode=" + errorReasonCode + ", messageParams=" + Arrays.toString(messageParams) + "]";
	}
}