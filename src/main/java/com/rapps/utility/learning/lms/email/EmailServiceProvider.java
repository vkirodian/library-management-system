package com.rapps.utility.learning.lms.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Provides configuration for email service.
 * 
 * @author vkirodian
 *
 */
@Configuration
public class EmailServiceProvider {

	private static final String FROM_ADDRESS = "smptFromAddress";
	private static final String SMTP_HOST = "smtpHost";
	private static final String SMTP_PORT = "smtpPort";
	private static final String SMTP_PASS = "smtpPassword";

	/**
	 * Returns a instantiated email session bean. SMTP Host, Port, Username,
	 * Password needs to be provided as part of environment variable.
	 * 
	 * @param environment
	 *            Environment variables from where the SMTP configurations are
	 *            to be picked up.
	 * @return Email Session
	 */
	@Bean(name = "emailSession")
	public Session getEmailSession(Environment environment) {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", environment.getProperty(SMTP_HOST));
		properties.put("mail.smtp.port", environment.getProperty(SMTP_PORT));
		Authenticator auth = new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(environment.getProperty(FROM_ADDRESS),
						environment.getProperty(SMTP_PASS));
			}
		};
		return Session.getDefaultInstance(properties, auth);
	}
}
