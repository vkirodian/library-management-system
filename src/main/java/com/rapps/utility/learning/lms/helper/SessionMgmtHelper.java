package com.rapps.utility.learning.lms.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.global.SessionCache;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.bean.User;
import com.rapps.utility.learning.lms.persistence.service.SessionService;
import com.rapps.utility.learning.lms.persistence.service.UserService;

/**
 * Helper for Session Management.
 * 
 * @author vkirodian
 *
 */
@Component
public class SessionMgmtHelper {

	@Autowired
	SessionService sessionService;

	@Autowired
	UserService userService;

	/**
	 * Get the user role for the user to whom the requested session id is
	 * assigned to.
	 * 
	 * @param session
	 *            Session of a user
	 * @return User Role for that user
	 * @throws LmsException
	 *             If Session or User not found
	 */
	public UserRoleEnum getRoleForUserSession(Session session) throws LmsException {
		if (session.getLastAccessTime() + LmsConstants.INACTIVITY_TIMEOUT < System.currentTimeMillis()) {
			SessionCache.removeSessionFromCache(session.getSessionId());
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.SESSION_EXPIRED);
		}
		User user = userService.getUserById(session.getUserId());
		return user.getUserRole();
	}

	/**
	 * Updates the last access time of the session.
	 * 
	 * @param sessionId
	 *            Session Id
	 * @throws LmsException
	 *             If session not found
	 */
	public void updateLastAccessTime(Session session) throws LmsException {
		session.setLastAccessTime(System.currentTimeMillis());
		sessionService.saveSession(session);
		SessionCache.addSessionToCache(session);
	}

	/**
	 * Clean all stale sessions in database on application startup.
	 */
	public void cleanSessionOnStartup() {
		sessionService.deleteAllSessions();
	}
}
