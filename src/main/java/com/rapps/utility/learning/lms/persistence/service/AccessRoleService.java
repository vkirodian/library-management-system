package com.rapps.utility.learning.lms.persistence.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rapps.utility.learning.lms.enums.AccessTypeEnum;
import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.persistence.bean.AccessRole;
import com.rapps.utility.learning.lms.persistence.repository.AccessRoleRepository;

/**
 * Access Role Service.
 * 
 * @author vkirodian
 *
 */
@Service
public class AccessRoleService extends BaseService<AccessRole> {

	private static final Logger LOG = LoggerFactory.getLogger(AccessRoleService.class);

	@Autowired
	AccessRoleRepository accessRoleRepo;

	/**
	 * Get Access Roles for the given Access Type.
	 * 
	 * @param accessType
	 *            AccessType
	 * @return List of AccessRole
	 * @throws LmsException
	 *             If no Access Role found
	 */
	@Cacheable(value = "accessrole", key = "#accessType")
	public List<AccessRole> getAccessRolesForAccessType(AccessTypeEnum accessType) throws LmsException {
		List<AccessRole> accessRoles = accessRoleRepo.findByAccessType(accessType);
		if (CollectionUtils.isEmpty(accessRoles)) {
			LOG.error("No Access role for access type");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.NO_ACCESS_ROLE_FOR_TYPE, accessType);
		}
		return accessRoles;
	}
}
