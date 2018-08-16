package com.rapps.utility.learning.lms.helper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rapps.utility.learning.lms.enums.AccessTypeEnum;
import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.persistence.bean.AccessRole;
import com.rapps.utility.learning.lms.persistence.service.AccessRoleService;

/**
 * Helper for Access Role Management.
 * 
 * @author vkirodian
 *
 */
@Component
public class AccessRoleMgmtHelper {
	
	private static final Logger LOG = LoggerFactory.getLogger(AccessRoleMgmtHelper.class);

	@Autowired
	AccessRoleService accessRoleService;

	/**
	 * Get User Roles for the given Access Type.
	 * 
	 * @param accessType
	 *            AccessType
	 * @return List of UserRoles
	 * @throws LmsException
	 *             If no Access Role found
	 */
	public List<UserRoleEnum> getUserRolesForAccessType(AccessTypeEnum accessType) throws LmsException {
		if (accessType == null) {
			LOG.error("Access type null");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "Access Type", " Access Role");
		}
		List<AccessRole> accessRoles = accessRoleService.getAccessRolesForAccessType(accessType);
		List<UserRoleEnum> userRoles = new ArrayList<>();
		for (AccessRole accessRole : accessRoles) {
			userRoles.add(accessRole.getRole());
		}
		return userRoles;
	}
}
