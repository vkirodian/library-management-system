package com.rapps.utility.learning.lms.helper;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.global.SessionCache;
import com.rapps.utility.learning.lms.model.LoginInput;
import com.rapps.utility.learning.lms.model.ResetPassword;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.bean.User;
import com.rapps.utility.learning.lms.persistence.service.SessionService;
import com.rapps.utility.learning.lms.persistence.service.UserService;

/**
 * Helper for Authentication Management class.
 * 
 * @author vkirodian
 *
 */
@Component
public class AuthenticationMgmtHelper extends BaseHelper {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationMgmtHelper.class);

	@Autowired
	UserService userService;

	@Autowired
	SessionService sessionService;

	/**
	 * Authenticate user.
	 * 
	 * @param loginId
	 *            Login Id
	 * @param password
	 *            Password
	 * @return User session
	 * @throws LmsException
	 */
	public Session authenticateUser(LoginInput loginInput) throws LmsException {
		User user = userService.getUserByLoginId(loginInput.getLoginId());
		if (!user.getPassword().equals(loginInput.getPassword())) {
			LOG.error("Login failed incorrect credentials entered");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.LOGIN_FAILED);
		}
		if (user.getPasswordExpiryTms() < System.currentTimeMillis()) {
			LOG.error("User password expired");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.PASSWORD_EXPIRED);
		}
		Session session = createSession(user);
		session.getUser().setPassword(null);
		SessionCache.addSessionToCache(session);
		return session;
	}

	/**
	 * Reset password for user.
	 * 
	 * @param resetPassword
	 * @return User.
	 * @throws LmsException
	 */
	public User resetPassword(ResetPassword resetPassword) throws LmsException {
		User user = userService.getUserByLoginId(resetPassword.getLoginId());
		if (!user.getPassword().equals(resetPassword.getOldPassword())) {
			LOG.error("Incorrect credentials entered");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.LOGIN_FAILED);
		}
		validatePasswordStrength(resetPassword.getNewPassword());
		user.setPassword(resetPassword.getNewPassword());
		user.setPasswordExpiryTms(System.currentTimeMillis() + LmsConstants.PASSWORD_EXPIRY_TIME);
		return userService.saveUser(user);
	}

	public void logout() throws LmsException {
		String sessionId = super.getSessionId();
		SessionCache.removeSessionFromCache(sessionId);
		deleteSession(sessionId);
	}

	private Session createSession(User user) {
		Session session = new Session();
		session.setLoggedInIpAddress("10.1.1.1");// TODO
		session.setLastAccessTime(System.currentTimeMillis());
		session.setLoggedInTime(System.currentTimeMillis());
		session.setSessionId(UUID.randomUUID().toString());
		session.setUser(user);
		return sessionService.saveSession(session);
	}
	
	private void deleteSession(String sessionId) throws LmsException {
		Session session = sessionService.getSession(sessionId);
		sessionService.deleteSession(session);
	}

	private void validatePasswordStrength(String password) {
	}
}
