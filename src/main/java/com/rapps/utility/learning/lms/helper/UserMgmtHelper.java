package com.rapps.utility.learning.lms.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.persistence.bean.User;
import com.rapps.utility.learning.lms.persistence.service.UserService;

/**
 * Helper for User Management class.
 * 
 * @author vkirodian
 *
 */
@Component
public class UserMgmtHelper {

	private static final Logger LOG = LoggerFactory.getLogger(UserMgmtHelper.class);

	@Autowired
	UserService userService;
	
	public User getUserDetails() throws LmsException {
		return userService.getUserByLoginId("");
	}
}
