package com.rapps.utility.learning.lms.persistence.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.model.UserModel;
import com.rapps.utility.learning.lms.persistence.bean.User;
import com.rapps.utility.learning.lms.persistence.repository.UserRepository;

/**
 * User service.
 * 
 * @author vkirodia
 *
 */
@Service
public class UserService extends BaseService<User> {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;

	/**
	 * Gets list of all users in the system.
	 * 
	 * @param filter
	 *            User Filter
	 * @return List of User
	 */
	public List<UserModel> getUsers(UserModel filter) {
		String loginId = filter.getLoginId() == null ? "%" : filter.getLoginId().toLowerCase();
		String emailId = filter.getEmailId() == null ? "%" : filter.getEmailId().toLowerCase();
		List<UserRoleEnum> role = filter.getUserRole() != null ? Arrays.asList(filter.getUserRole()) : UserRoleEnum.getAllRoles();
		return Converter.convertList(userRepository.findUsersByFilter(loginId, emailId, role), UserModel.class);
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
	public UserModel getUserByLoginId(String loginId) throws LmsException {
		User user = userRepository.findByLoginId(loginId);
		if (user == null) {
			LOG.error("User not found");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.USER_NOT_FOUND);
		}
		return Converter.convertObject(user, UserModel.class);
	}

	/**
	 * User user by ID
	 * 
	 * @param userId
	 *            User ID
	 * @return User
	 * @throws LmsException
	 *             if User not found
	 */
	@Cacheable(value = "user", key = "#userId")
	public UserModel getUserById(String userId) throws LmsException {
		return Converter.convertObject(super.findById(userId), UserModel.class);
	}

	/**
	 * Save user.
	 * 
	 * @param user
	 *            User
	 * @return Saved user
	 */
	@Transactional
	@CachePut(value = "user", key = "#user.userId")
	public UserModel saveUser(UserModel user) {
		user.setUserId(UUID.randomUUID().toString());
		user.setPasswordExpiryTms(System.currentTimeMillis());
		return Converter.convertObject(userRepository.save(Converter.convertObject(user, User.class)), UserModel.class);
	}

	/**
	 * Update user.
	 * 
	 * @param user
	 *            User
	 * @return Updated user
	 */
	@Transactional
	@CachePut(value = "user", key = "#user.userId")
	public UserModel updateUser(UserModel user) {
		return Converter.convertObject(userRepository.save(Converter.convertObject(user, User.class)), UserModel.class);
	}

	/**
	 * Delete user
	 * 
	 * @param uid
	 *            User Id
	 * @throws LmsException
	 *             If User not found
	 */
	@Transactional
	@CacheEvict(value = "user")
	public void deleteUser(String uid) throws LmsException {
		super.deleteById(uid);
	}
}
