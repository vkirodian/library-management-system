package com.rapps.utility.learning.lms.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Autowired
	UserRepository userRepository;
	
	public User getUserByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
}
