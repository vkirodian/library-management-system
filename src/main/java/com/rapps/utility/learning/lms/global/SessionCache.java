package com.rapps.utility.learning.lms.global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rapps.utility.learning.lms.persistence.bean.Session;

/**
 * A cache for active sessions.
 * 
 * @author vkirodia
 *
 */
public final class SessionCache {

	private static final Map<String, Session> SESSION_CACHE = new HashMap<>();

	private SessionCache() {
	}

	/**
	 * Get the value of session cache
	 * 
	 * @return SESSION CACHE
	 */
	public static Map<String, Session> getSessionCache() {
		return SESSION_CACHE;
	}

	/**
	 * Checks if the give session is present in the cache.
	 * 
	 * @param session
	 *            Session to be checked
	 * @return Session
	 */
	public static Session sessionExists(String sessionId) {
		return SESSION_CACHE.get(sessionId);
	}

	/**
	 * Add a session to cache.
	 * 
	 * @param session
	 *            Session to add
	 * @return
	 */
	public static Session addSessionToCache(Session session) {
		return SESSION_CACHE.put(session.getSessionId(), session);
	}

}
