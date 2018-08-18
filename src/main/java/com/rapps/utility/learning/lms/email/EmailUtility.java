package com.rapps.utility.learning.lms.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;

/**
 * Provides a mechanism to send email.
 * 
 * @author vkirodian
 *
 */
@Service
public class EmailUtility {

	private static final Logger LOG = LoggerFactory.getLogger(EmailUtility.class);
	private static final String FROM_ADDRESS = "smptFromAddress";

	@Autowired
	Environment environment;

	@Autowired
	@Qualifier("emailSession")
	Session session;

	/**
	 * Send simple email.
	 * 
	 * @param to
	 *            Recipient
	 * @param subject
	 *            Subject
	 * @param body
	 *            Body/Content
	 * @throws LmsException
	 *             If email sending fails
	 */
	public void sendEmail(String to, String subject, String body) throws LmsException {
		LOG.debug("sending email");
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(environment.getProperty(FROM_ADDRESS)));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (MessagingException mex) {
			LOG.error("Error while sending email", mex);
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.EMAIL_SENDING_ERROR);
		}
	}
}
