package com.rapps.utility.learning.lms.helper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.global.SessionCache;
import com.rapps.utility.learning.lms.model.UserModel;
import com.rapps.utility.learning.lms.persistence.bean.Session;
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

	private static final Logger LOG = LoggerFactory.getLogger(SessionMgmtHelper.class);

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
			LOG.error("Session expired");
			clearUserSession(session);
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.SESSION_EXPIRED);
		}
		UserModel user = userService.getUserById(session.getUserId());
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
	public void updateLastAccessTime(Session session) {
		session.setLastAccessTime(System.currentTimeMillis());
		sessionService.saveSession(session);
		SessionCache.addSessionToCache(session);
	}

	/**
	 * Check for all the session in the database if they are past expiry time,
	 * if so removes them from the cache and database. This method executes as
	 * per cron configuration {@code clearStaleSessionFrequency}.
	 */
	@Scheduled(cron = "${clearStaleSessionFrequency}")
	public void clearStaleSessions() {
		long expiredLastAccessTime = System.currentTimeMillis() - LmsConstants.INACTIVITY_TIMEOUT;
		List<Session> expriredSessions = sessionService.findByLastAccessTimeLessThan(expiredLastAccessTime);
		for (Session session : expriredSessions) {
			try {
				clearUserSession(session);
			} catch (LmsException e) {
				LOG.error("Error while clearing session", e);
			}
		}
	}

	/**
	 * Delete given session from cache and database.
	 * 
	 * @param session
	 *            Session
	 * @throws LmsException
	 *             If Session not found
	 */
	public void clearUserSession(Session session) throws LmsException {
		SessionCache.removeSessionFromCache(session.getSessionId());
		sessionService.deleteById(session.getSessionId());
	}

	/**
	 * Clean all stale sessions in database on application startup.
	 */
	public void cleanSessionOnStartup() {
		sessionService.deleteAllSessions();
	}
}
