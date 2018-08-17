package com.rapps.utility.learning.lms.email;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

@RunWith(MockitoJUnitRunner.class)
public class TestEmailServiceProvider {

	private static final String FROM_ADDRESS = "smptFromAddress";
	private static final String SMTP_HOST = "smtpHost";
	private static final String SMTP_PORT = "smtpPort";
	private static final String SMTP_PASS = "smtpPassword";

	@InjectMocks
	EmailServiceProvider provider;

	@Mock
	Environment environment;

	@Test
	public void testGetEmailSession() {
		when(environment.getProperty(SMTP_HOST)).thenReturn("server");
		when(environment.getProperty(SMTP_PORT)).thenReturn("1234");
		when(environment.getProperty(FROM_ADDRESS)).thenReturn("test@lms.com");
		when(environment.getProperty(SMTP_PASS)).thenReturn("password");
		assertNotNull("", provider.getEmailSession());
	}

}
