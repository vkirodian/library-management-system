package com.rapps.utility.learning.lms.persistence.bean;

import org.junit.Test;

import junit.framework.TestCase;

public class TestSession extends TestCase {

	@Test
	public void testEqualsObject() {
		Session s1a = getS1Session();
		Session s1b = getS1Session();
		Session s2 = getS2Session();
		assertTrue("Session Equals failed", s1a.equals(s1a));
		assertTrue("Session Equals failed", s1a.equals(s1b));
		assertTrue("Session Equals failed", !s1a.equals(s2));
		assertTrue("Session Equals failed", !s1a.equals(new String("Session")));
		assertTrue("Session Equals failed", !s1a.equals(null));
		s1b.setSessionId(null);
		assertTrue("Session Equals failed", !s1a.equals(s1b));
		assertTrue("Session Equals failed", !s1b.equals(s1a));
		s1a.setSessionId(null);
		assertTrue("Session Equals failed", s1a.equals(s1b));
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
