package com.rapps.utility.learning.lms.email;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import com.rapps.utility.learning.lms.exception.LmsException;

@RunWith(MockitoJUnitRunner.class)
public class TestEmailUtility {

	private static final String FROM_ADDRESS = "smptFromAddress";

	@InjectMocks
	EmailUtility util;

	@Mock
	Environment environment;

	@Test(expected = Exception.class)
	public void testSendEmail_Success() throws LmsException {
		when(environment.getProperty(FROM_ADDRESS)).thenReturn("test@lms.com");
		util.sendEmail("to@lms.com", "Test Email", "Hello Tester");
	}

}
