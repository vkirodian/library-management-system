package com.rapps.utility.learning.lms.persistence.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.repository.SessionRepository;

/**
 * Session service
 * 
 * @author vkirodia
 *
 */
@Service
public class SessionService {

	@Autowired
	SessionRepository sessionRepository;

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
		Optional<Session> sessions = sessionRepository.findById(sessionId);
		if (sessions.isPresent()) {
			return sessions.get();
		} else {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.SESSION_NOT_FOUND);
		}
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

}
