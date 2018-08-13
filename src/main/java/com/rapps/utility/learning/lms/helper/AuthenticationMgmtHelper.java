package com.rapps.utility.learning.lms.helper;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.global.SessionCache;
import com.rapps.utility.learning.lms.model.LoginInputModel;
import com.rapps.utility.learning.lms.model.ResetPasswordModel;
import com.rapps.utility.learning.lms.model.UserModel;
import com.rapps.utility.learning.lms.persistence.bean.Session;
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

	private static final int MAX_PASSWORD_LENGTH = 25;
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final String SPECIAL_CHARS = "@#$%^&+=";
	private static final String PASS_REQUIRED_CHARS_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[" + SPECIAL_CHARS
			+ "])(?=\\S+$).{0,}$";

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
	public Session authenticateUser(LoginInputModel loginInput) throws LmsException {
		if (StringUtils.isEmpty(loginInput.getLoginId())) {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "Login ID", "LOGIN");
		}
		UserModel user = userService.getUserByLoginId(loginInput.getLoginId());
		if (!user.getPassword().equals(loginInput.getPassword())) {
			LOG.error("Login failed incorrect credentials entered");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.LOGIN_FAILED);
		}
		if (user.getPasswordExpiryTms() < System.currentTimeMillis()) {
			LOG.error("User password expired");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.PASSWORD_EXPIRED);
		}
		Session session = createSession(user);
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
	public UserModel resetPassword(ResetPasswordModel resetPassword) throws LmsException {
		if (StringUtils.isEmpty(resetPassword.getLoginId())) {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "Login ID", "LOGIN");
		}
		UserModel user = userService.getUserByLoginId(resetPassword.getLoginId());
		validatePasswordStrength(user.getLoginId(), user.getPassword(), resetPassword.getNewPassword());
		user.setPassword(resetPassword.getNewPassword());
		user.setPasswordExpiryTms(0);
		return userService.saveUser(user);
	}

	/**
	 * Update password for user.
	 * 
	 * @param resetPassword
	 * @return User.
	 * @throws LmsException
	 */
	public UserModel updatePassword(ResetPasswordModel resetPassword) throws LmsException {
		if (StringUtils.isEmpty(resetPassword.getNewPassword())) {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "New Password",
					"Update Password");
		}
		String sessionId = super.getSessionId();
		Session session = SessionCache.sessionExists(sessionId);
		UserModel user = userService.getUserById(session.getUserId());
		validatePasswordStrength(user.getLoginId(), user.getPassword(), resetPassword.getNewPassword());
		user.setPassword(resetPassword.getNewPassword());
		user.setPasswordExpiryTms(System.currentTimeMillis() + LmsConstants.PASSWORD_EXPIRY_TIME);
		return userService.saveUser(user);
	}

	/**
	 * Forgot password.
	 * 
	 * @param loginInput
	 * @return
	 * @throws LmsException
	 */
	public void forgotPassword(LoginInputModel loginInput) {
		try {
			UserModel user = userService.getUserByLoginId(loginInput.getLoginId());
			String emailId = user.getEmailId();
			LOG.debug("Sending email to {}", emailId);// TODO send email
		} catch (LmsException e) {
			LOG.error("Error ", e);
		}

	}

	/**
	 * Logs out current user, removes session from cache and database.
	 * 
	 * @throws LmsException
	 */
	public void logout() throws LmsException {
		String sessionId = super.getSessionId();
		SessionCache.removeSessionFromCache(sessionId);
		deleteSession(sessionId);
	}

	private Session createSession(UserModel user) {
		Session session = new Session();
		session.setLoggedInIpAddress(super.getRemoteAddress());
		session.setLastAccessTime(System.currentTimeMillis());
		session.setLoggedInTime(System.currentTimeMillis());
		session.setSessionId(UUID.randomUUID().toString());
		session.setUserId(user.getUserId());
		return sessionService.saveSession(session);
	}

	private void deleteSession(String sessionId) throws LmsException {
		Session session = sessionService.getSession(sessionId);
		sessionService.deleteSession(session);
	}

	/**
	 * Validate password strength.
	 * 
	 * @param loginId
	 *            Login ID
	 * @param oldPassword
	 *            Old Password
	 * @param newPassword
	 *            New Password
	 * @throws LmsException
	 *             If strength is week
	 */
	protected void validatePasswordStrength(String loginId, String oldPassword, String newPassword)
			throws LmsException {
		if (oldPassword.equals(newPassword)) {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.OLD_NEW_PASWORD_SAME);
		}
		if (newPassword == null || newPassword.trim().isEmpty()) {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.PASSWORD_NULL_EMPTY);
		}
		if (loginId.equals(newPassword)) {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.PASSWORD_USERNAME_SAME);
		}
		if (newPassword.length() < MIN_PASSWORD_LENGTH || newPassword.length() > MAX_PASSWORD_LENGTH) {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.PASSWORD_LENGTH_ERROR, MIN_PASSWORD_LENGTH,
					MAX_PASSWORD_LENGTH);
		}
		if (!newPassword.matches(PASS_REQUIRED_CHARS_REGEX)) {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.PASSWORD_CHARS_ERROR, SPECIAL_CHARS);
		}
	}
}
