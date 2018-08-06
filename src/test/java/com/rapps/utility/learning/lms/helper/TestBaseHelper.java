package com.rapps.utility.learning.lms.helper;

import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.global.LmsConstants;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestBaseHelper extends TestCase {

	@InjectMocks
	BaseHelper helper = new BaseHelper();

	@Mock
	HttpServletRequest httpRequest;

	@Test(expected = LmsException.class)
	public void testGetSessionId_SessionNotFound() throws LmsException {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn(null);
		helper.getSessionId();
	}

	@Test
	public void testGetSessionId_Success() throws LmsException {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn("Session_id_1");
		String sessionId = helper.getSessionId();
		assertEquals("", "Session_id_1", sessionId);
	}

	@Test
	public void testGetRemoteAddress() {
		when(httpRequest.getRemoteAddr()).thenReturn("10.1.1.1");
		String ip = helper.getRemoteAddress();
		assertEquals("", "10.1.1.1", ip);
	}

}
