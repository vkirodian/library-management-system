package com.rapps.utility.learning.lms.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.GenericOutput;

import ch.qos.logback.core.status.ErrorStatus;

/**
 * Provide centralized exception handling across all @RequestMapping methods
 * through @ExceptionHandler methods. It wraps the {@link LmsException} thrown
 * be underlying controller to {@link ErrorStatus}
 * 
 * @author vkirodia
 *
 */
@ControllerAdvice
public class LmsExceptionFilter extends ResponseEntityExceptionHandler {

	@ExceptionHandler(LmsException.class)
	protected ResponseEntity<Object> handleLmsException(LmsException ex) {
		GenericOutput errorStatus = new GenericOutput();
		errorStatus.setErrorMsg(ex.getErrorMessage());
		errorStatus.setErrorReason(ex.getErrorReason());
		errorStatus.setErrorReasonCode(ex.getErrorReasonCode());
		errorStatus.setErrorType(ex.getErrorType());
		return buildResponseEntity(errorStatus);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		GenericOutput errorStatus = new GenericOutput();
		errorStatus.setErrorMsg("HTTP message not readable");
		errorStatus.setErrorReason(ex.getMessage());
		errorStatus.setErrorReasonCode(MessagesEnum.INTERNAL_ERROR.name());
		errorStatus.setErrorType(LmsException.ErrorType.GLOBAL_FAILURE);
		return buildResponseEntity(errorStatus);
	}

	private ResponseEntity<Object> buildResponseEntity(GenericOutput error) {
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}