package com.rapps.utility.learning.lms.persistence.repository;

import com.rapps.utility.learning.lms.persistence.bean.User;

/**
 * 
 * @author vkirodia
 *
 */
public interface UserRepository extends BaseRepository<User> {

	User findByLoginId(String loginId);
}
