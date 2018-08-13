package com.rapps.utility.learning.lms.persistence.repository;

import java.util.List;

import com.rapps.utility.learning.lms.enums.AccessTypeEnum;
import com.rapps.utility.learning.lms.persistence.bean.AccessRole;

/**
 * Access Role Repository.
 * 
 * @author vkirodian
 *
 */
public interface AccessRoleRepository extends BaseRepository<AccessRole> {

	/**
	 * Get list of Access Role by Access Type.
	 * 
	 * @param accessType
	 *            AccessType.
	 * @return List of AcccessRole
	 */
	List<AccessRole> findByAccessType(AccessTypeEnum accessType);
}
