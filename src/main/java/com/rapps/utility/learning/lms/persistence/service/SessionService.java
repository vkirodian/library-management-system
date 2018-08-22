package com.rapps.utility.learning.lms.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.repository.SessionRepository;

/**
 * Session service
 * 
 * @author vkirodia
 *
 */
@Service
public class SessionService extends BaseService<Session> {

	@Autowired
	SessionRepository sessionRepository;

	/**
	 * Get List of session with last access time less than the passed time.
	 * 
	 * @param lastAccessTime
	 *            Access time
	 * @return List of Session
	 */
	public List<Session> findByLastAccessTimeLessThan(long lastAccessTime) {
		return sessionRepository.findByLastAccessTimeLessThan(lastAccessTime);
	}

	/**
	 * Get session details for given session id.
	 * 
	 * @param sessionId
	 *            Sessoin Id
	 * @return Session
	 * @throws LmsException
	 *             Session not found for given Id
	 */
	public Session getSession(String sessionId) throws LmsException {
		return super.findById(sessionId);
	}

	/**
	 * Persist user session.
	 * 
	 * @param session
	 *            Session
	 * @return Session
	 */
	@Transactional
	public Session saveSession(Session session) {
		return sessionRepository.save(session);
	}

	/**
	 * Deletes the session.
	 * 
	 * @param session
	 *            Session
	 */
	@Transactional
	public void deleteSession(Session session) {
		sessionRepository.delete(session);
	}

	/**
	 * Delete all sessions
	 */
	public void deleteAllSessions() {
		sessionRepository.deleteAll();
	}

}
