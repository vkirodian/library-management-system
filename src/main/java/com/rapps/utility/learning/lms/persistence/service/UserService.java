package com.rapps.utility.learning.lms.persistence.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.persistence.bean.User;
import com.rapps.utility.learning.lms.persistence.repository.UserRepository;

/**
 * User service.
 * 
 * @author vkirodia
 *
 */
@Service
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;

	/**
	 * Gets list of all users in the system.
	 * 
	 * @return List of User
	 */
	public List<User> getUsers() {
		return (List<User>) userRepository.findAll();
	}

	/**
	 * Get User for login id.
	 * 
	 * @param loginId
	 *            Login Id
	 * @return User details
	 * @throws LmsException
	 *             If user not found
	 */
	public User getUserByLoginId(String loginId) throws LmsException {
		User user = userRepository.findByLoginId(loginId);
		if (user == null) {
			LOG.error("User not found");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.USER_NOT_FOUND);
		}
		return user;
	}
	
	public User getUserById(String userId) throws LmsException {
		Optional<User> users = userRepository.findById(userId);
		if (users.isPresent()) {
			return users.get();
		} else {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.USER_NOT_FOUND);
		}
	}

	/**
	 * Save user.
	 * 
	 * @param user
	 *            User
	 * @return Saved user
	 */
	@Transactional
	public User saveUser(User user) {
		return userRepository.save(user);
	}
}
