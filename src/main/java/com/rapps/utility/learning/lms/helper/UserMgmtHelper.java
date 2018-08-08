package com.rapps.utility.learning.lms.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.bean.User;
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

	@Autowired
	SessionService sessionService;

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationMgmtHelper authenticationMgmtHelper;

	/**
	 * Get User details.
	 * 
	 * @return User
	 * @throws LmsException
	 */
	public User getUserDetails() throws LmsException {
		String sessionId = super.getSessionId();
		Session session = sessionService.getSession(sessionId);
		String userId = session.getUserId();
		return userService.getUserById(userId);
	}

	/**
	 * Returns list of users in the system.
	 * 
	 * @return List of User
	 */
	public List<User> getUsers() {
		return userService.getUsers();
	}

	/**
	 * Update User.
	 * 
	 * @param user
	 *            User
	 * @return Updated User
	 * @throws LmsException
	 */
	public User updateUser(User user) throws LmsException {
		User dbUser = userService.getUserById(user.getUserId());
		User loggedInUser = getUserDetails();

		if (loggedInUser.getUserRole() != UserRoleEnum.SUPER_ADMIN) {
			//Non admin user cannot update other users.
			if (!user.getUserId().equals(loggedInUser.getUserId())) {
				throw new LmsException(ErrorType.FAILURE, MessagesEnum.CANNOT_UPDATE_USER, loggedInUser.getLoginId(),
						"User Details", dbUser.getLoginId());
			}

			// User role can be updated by an admin user only.
			if (user.getUserRole() != null) {
				throw new LmsException(ErrorType.FAILURE, MessagesEnum.CANNOT_UPDATE_USER, loggedInUser.getLoginId(),
						"User Role", dbUser.getLoginId());
			} else {
				user.setUserRole(dbUser.getUserRole());
			}
		}

		// Login Id should not be updated
		if (!dbUser.getLoginId().equals(user.getLoginId())) {
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
}
