package com.rapps.utility.learning.lms.persistence.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.repository.SessionRepository;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestSessionService extends TestCase {

	@InjectMocks
	SessionService service;

	@Mock
	SessionRepository sessionRepository;

	@Test(expected = LmsException.class)
	public void testGetSession_Exception() throws LmsException {
		Optional<Session> s = Optional.ofNullable(null);
		when(sessionRepository.findById("s1")).thenReturn(s);
		service.getSession("s1");
	}

	@Test
	public void testGetSession_Success() throws LmsException {
		Session s = new Session();
		Optional<Session> o = Optional.ofNullable(s);
		when(sessionRepository.findById("s1")).thenReturn(o);
		Session acc = service.getSession("s1");
		assertEquals("", s, acc);
	}

	@Test
	public void testSaveSession() {
		service.saveSession(new Session());
	}

	@Test
	public void testDeleteSession() {
		service.deleteSession(new Session());
	}

	@Test
	public void testDeleteAllSessions() {
		service.deleteAllSessions();
	}

}
