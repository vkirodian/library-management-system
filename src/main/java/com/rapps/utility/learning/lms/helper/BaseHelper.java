package com.rapps.utility.learning.lms.helper;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.global.LmsConstants;

/**
 * Base class for all helper classes. Provides some basic functionality.
 * 
 * @author vkirodian
 *
 */
public class BaseHelper {

	private static final Logger LOG = LoggerFactory.getLogger(BaseHelper.class);

	@Autowired
	HttpServletRequest httpRequest;

	/**
	 * Returns current users session Id.
	 * 
	 * @return Session Id
	 * @throws LmsException
	 */
	protected String getSessionId() throws LmsException {
		String sessionId = httpRequest.getHeader(LmsConstants.SESSION_ID);
		if (sessionId == null) {
			LOG.error("Session information missing in request");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.SESSION_MISSING);
		}
		return sessionId;
	}
}
