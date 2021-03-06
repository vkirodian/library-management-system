package com.rapps.utility.learning.lms.helper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.global.SessionCache;
import com.rapps.utility.learning.lms.model.UserModel;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.service.SessionService;
import com.rapps.utility.learning.lms.persistence.service.UserService;

/**
 * Helper for User Management class.
 * 
 * @author vkirodian
 *
 */
@Component
public class UserMgmtHelper extends BaseHelper {

	private static final Logger LOG = LoggerFactory.getLogger(UserMgmtHelper.class);

	@Autowired
	SessionService sessionService;

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationMgmtHelper authenticationMgmtHelper;

	/**
	 * Get Logged-in User details.
	 * 
	 * @return User
	 * @throws LmsException
	 */
	public UserModel getUserDetails() throws LmsException {
		String sessionId = super.getSessionId();
		Session session = SessionCache.sessionExists(sessionId);
		if (session == null) {
			LOG.error("Session does not exist");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.SESSION_NOT_FOUND);
		}
		String userId = session.getUserId();
		return userService.getUserById(userId);
	}

	/**
	 * Returns list of users in the system.
	 * 
	 * @param filter
	 *            User Filter
	 * @return List of User
	 */
	public List<UserModel> getUsers(UserModel filter) {
		return userService.getUsers(filter);
	}

	/**
	 * Update User.
	 * 
	 * @param user
	 *            User
	 * @return Updated User
	 * @throws LmsException
	 */
	public UserModel updateUser(UserModel user) throws LmsException {
		UserModel dbUser = userService.getUserById(user.getUserId());
		UserModel loggedInUser = getUserDetails();

		if (loggedInUser.getUserRole() != UserRoleEnum.SUPER_ADMIN) {
			// Non admin user cannot update other users.
			if (!user.getUserId().equals(loggedInUser.getUserId())) {
				LOG.error("Non admin user cannot update other users");
				throw new LmsException(ErrorType.FAILURE, MessagesEnum.CANNOT_UPDATE_USER, loggedInUser.getLoginId(),
						"User Details", dbUser.getLoginId());
			}

			// User role can be updated by an admin user only.
			if (user.getUserRole() != null) {
				LOG.error("User role can be updated by an admin user only");
				throw new LmsException(ErrorType.FAILURE, MessagesEnum.CANNOT_UPDATE_USER, loggedInUser.getLoginId(),
						"User Role", dbUser.getLoginId());
			} else {
				user.setUserRole(dbUser.getUserRole());
			}
		}

		// Login Id should not be updated
		if (user.getLoginId() != null && !dbUser.getLoginId().equals(user.getLoginId())) {
			LOG.error("Login ID cannot be updated");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.CANNOT_UPDATE_LOGINID);
		}

		// If password is updated perform password strength validation, else
		// keep password and password expiry as it is.
		if (!StringUtils.isEmpty(user.getPassword())) {
			authenticationMgmtHelper.validatePasswordStrength(user.getLoginId(), dbUser.getPassword(),
					user.getPassword());
			user.setPasswordExpiryTms(System.currentTimeMillis() + LmsConstants.PASSWORD_EXPIRY_TIME);
		} else {
			user.setPassword(dbUser.getPassword());
			user.setPasswordExpiryTms(dbUser.getPasswordExpiryTms());
		}

		return userService.updateUser(user);
	}

	/**
	 * Add User.
	 * 
	 * @param user
	 *            User
	 * @return Added User
	 * @throws LmsException
	 *             If mandatory field missing
	 */
	public UserModel addUser(UserModel user) throws LmsException {
		if (StringUtils.isEmpty(user.getLoginId())) {
			LOG.error("Login ID missing");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "Login ID", "User");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			LOG.error("Password missing");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "Password", "User");
		}
		if (user.getUserRole() == null) {
			LOG.error("User role missing");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "User Role", "User");
		}
		return userService.saveUser(user);
	}

	/**
	 * Delete User.
	 * 
	 * @param uid
	 *            User Id
	 * @throws LmsException
	 *             If User not found
	 */
	public void deleteUser(String uid) throws LmsException {
		if (StringUtils.isEmpty(uid)) {
			LOG.error("User ID missing");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "ID", "Book");
		}
		UserModel loggedInUser = getUserDetails();
		if (loggedInUser.getUserId().equals(uid)) {
			LOG.error("Self deletion not allowed");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.SELF_DELETION_NOT_ALLOWED);
		}
		userService.deleteUser(uid);
	}

	/**
	 * Get User by ID.
	 * 
	 * @param uid
	 *            User ID
	 * @return User
	 * @throws LmsException
	 *             If User not found
	 */
	public UserModel getUser(String uid) throws LmsException {
		if (StringUtils.isEmpty(uid)) {
			LOG.error("User ID is empty");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "ID", "User");
		}
		return userService.getUserById(uid);
	}
}
