package com.rapps.utility.learning.lms.helper;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;

public class BaseHelper {

	private static final Logger LOG = LoggerFactory.getLogger(BaseHelper.class);

	@Autowired
	HttpServletRequest httpRequest;

	protected String getSessionId() throws LmsException {
		String sessionId = httpRequest.getHeader("sessionId");
		if (sessionId == null) {
			LOG.error("Session information missing in request");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.SESSION_MISSING);
		}
		return sessionId;
	}
}
