package com.rapps.utility.learning.lms.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.persistence.bean.User;

/**
 * User Repository.
 * 
 * @author vkirodian
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

	@Query("SELECT u FROM User u WHERE LOWER(u.loginId) LIKE %:loginId% AND LOWER(u.emailId) LIKE %:emailId% AND u.userRole IN (:userRole)")
	List<User> findUsersByFilter(@Param("loginId") String loginId, @Param("emailId") String emailId,
			@Param("userRole") List<UserRoleEnum> userRole);
}
