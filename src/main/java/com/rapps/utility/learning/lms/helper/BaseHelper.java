package com.rapps.utility.learning.lms.helper;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rapps.utility.learning.lms.email.EmailUtility;
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

	@Autowired
	EmailUtility emailUtility;

	/**
	 * Returns current users session Id from request header.
	 * 
	 * @return Session Id
	 * @throws LmsException
	 *             If session missing in header
	 */
	protected String getSessionId() throws LmsException {
		String sessionId = httpRequest.getHeader(LmsConstants.SESSION_ID);
		if (sessionId == null) {
			LOG.error("Session information missing in request");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.SESSION_MISSING);
		}
		return sessionId;
	}

	/**
	 * Returns the IP Address of the client from which user is logged in.
	 * 
	 * @return IP Address
	 */
	protected String getRemoteAddress() {
		return httpRequest.getRemoteAddr();
	}

	protected void sendEmail(String to, String subject, String body) throws LmsException {
		emailUtility.sendEmail(to, subject, body);
	}
}
