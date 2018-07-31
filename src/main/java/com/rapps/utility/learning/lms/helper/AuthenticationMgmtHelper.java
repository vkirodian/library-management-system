package com.rapps.utility.learning.lms.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.persistence.bean.User;
import com.rapps.utility.learning.lms.persistence.service.UserService;

/**
 * Helper for Authentication Management class.
 * 
 * @author vkirodia
 *
 */
@Component
public class AuthenticationMgmtHelper {

	@Autowired
	UserService userService;

	public User authenticateUser(String loginId, String password) throws LmsException {
		User user = userService.getUserByLoginId(loginId);
		if (!user.getPassword().equals(password)) {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.LOGIN_FAILED);
		}
		return user;
	}
}
