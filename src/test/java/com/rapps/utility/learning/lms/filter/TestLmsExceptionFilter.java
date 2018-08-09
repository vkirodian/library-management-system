package com.rapps.utility.learning.lms.filter;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.model.OutputStatus;

import junit.framework.TestCase;

public class TestLmsExceptionFilter extends TestCase {

	LmsExceptionFilter filter = new LmsExceptionFilter();

	@Test
	public void testHandleLmsException() {
		LmsException ex = new LmsException(ErrorType.FAILURE, MessagesEnum.FAILURE);
		ResponseEntity<Object> actual = filter.handleLmsException(ex);
		OutputStatus status = (OutputStatus) actual.getBody();
		assertEquals("", status.getErrorType(), ErrorType.FAILURE);
		assertEquals("", status.getErrorMsg(), "Operation failed: Operation failed");
		assertEquals("", status.getErrorReason(), "Operation failed");
		assertEquals("", status.getErrorReasonCode(), "FAILURE");
	}

	@Test
	public void testHandleIoException() {
		LmsException ex = new LmsException(ErrorType.FAILURE, MessagesEnum.FAILURE);
		ResponseEntity<Object> actual = filter.handleIoException(ex);
		OutputStatus status = (OutputStatus) actual.getBody();
		assertEquals("", status.getErrorType(), ErrorType.FAILURE);
		assertEquals("", status.getErrorMsg(), "Operation failed: Operation failed");
		assertEquals("", status.getErrorReason(), "Operation failed");
		assertEquals("", status.getErrorReasonCode(), "FAILURE");
	}

	@Test
	public void testHandleHttpMessageNotReadable() {
		HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Error");
		ResponseEntity<Object> actual = filter.handleHttpMessageNotReadable(ex, null, null, null);
		OutputStatus status = (OutputStatus) actual.getBody();
		assertEquals("", status.getErrorType(), ErrorType.GLOBAL_FAILURE);
		assertEquals("", status.getErrorMsg(), "HTTP message not readable");
		assertEquals("", status.getErrorReason(), "Error");
		assertEquals("", status.getErrorReasonCode(), "INTERNAL_ERROR");
	}

}
