package com.rapps.utility.learning.lms.global;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;

import com.rapps.utility.learning.lms.persistence.bean.Session;

public class TestSessionCache {

	@Test
	public void testGetSessionCache() {
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getS1Session());
		Map<String, Session> sessionMap = SessionCache.getSessionCache();
		assertEquals("getSessionCache returned incorrect size", 1, sessionMap.keySet().size());

		SessionCache.addSessionToCache(getS2Session());
		sessionMap = SessionCache.getSessionCache();
		assertEquals("getSessionCache returned incorrect size", 2, sessionMap.keySet().size());

		Session sActual = sessionMap.get("s1");
		assertEquals("getSessionCache failed", getS1Session(), sActual);
	}

	@Test
	public void testSessionExists() {
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getS1Session());
		Session sExist = SessionCache.sessionExists(getS1Session().getSessionId());
		Session sNotExist = SessionCache.sessionExists(getS2Session().getSessionId());
		assertNotNull("sessionExist failed", sExist);
		assertNull("sessionExist failed", sNotExist);
	}

	@Test
	public void testAddSessionToCache() {
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getS1Session());
		Session s1 = SessionCache.sessionExists(getS1Session().getSessionId());
		assertNotNull("addSessionToCache failed", s1);
	}

	@Test
	public void testRemoveSessionFromCache() {
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(getS1Session());
		SessionCache.addSessionToCache(getS2Session());
		SessionCache.removeSessionFromCache(getS1Session().getSessionId());
		Map<String, Session> sessionMap = SessionCache.getSessionCache();
		assertEquals("removeSessionFromCache failed", 1, sessionMap.keySet().size());
		assertNull("removeSessionFromCache failed", SessionCache.sessionExists(getS1Session().getSessionId()));
	}
	
	@Test
	public void testRemoveAllSessions() {
		SessionCache.addSessionToCache(getS1Session());
		SessionCache.addSessionToCache(getS2Session());
		SessionCache.removeAllSessions();
		Map<String, Session> sessionMap = SessionCache.getSessionCache();
		assertEquals("removeAllSessions failed", 0, sessionMap.keySet().size());
	}

	private static Session getS1Session() {
		Session s1 = new Session();
		s1.setSessionId("s1");
		s1.setUserId("u1");
		return s1;
	}

	private static Session getS2Session() {
		Session s2 = new Session();
		s2.setSessionId("s2");
		s2.setUserId("u2");
		return s2;
	}
}
