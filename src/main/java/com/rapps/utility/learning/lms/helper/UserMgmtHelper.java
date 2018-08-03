package com.rapps.utility.learning.lms.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rapps.utility.learning.lms.exception.LmsException;
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
}
