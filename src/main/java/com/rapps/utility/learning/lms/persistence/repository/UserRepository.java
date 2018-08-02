package com.rapps.utility.learning.lms.persistence.repository;

import com.rapps.utility.learning.lms.persistence.bean.User;

/**
 * User Repository.
 * 
 * @author vkirodia
 *
 */
public interface UserRepository extends BaseRepository<User> {

	/**
	 * Find user for login id.
	 * 
	 * @param loginId
	 *            Login Id
	 * @return User details
	 */
	User findByLoginId(String loginId);
}
