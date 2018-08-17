package com.rapps.utility.learning.lms.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class EmailServiceProvider {

	private static final String FROM_ADDRESS = "smptFromAddress";
	private static final String SMTP_HOST = "smtpHost";
	private static final String SMTP_PORT = "smtpPort";
	private static final String SMTP_PASS = "smtpPassword";

	@Autowired
	Environment environment;

	@Bean(name = "emailSession")
	public Session getEmailSession() {
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
