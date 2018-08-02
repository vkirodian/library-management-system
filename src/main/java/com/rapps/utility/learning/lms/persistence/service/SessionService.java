package com.rapps.utility.learning.lms.persistence.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 * 
	 * @param sessionId
	 * @return
	 */
	public Optional<Session> getSession(String sessionId) {
		return sessionRepository.findById(sessionId);
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

}
