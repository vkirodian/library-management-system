package com.rapps.utility.learning.lms.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

/**
 * Enum containing mapping between API and the roles for which those API are
 * allowed.
 * 
 * @author vkirodian
 *
 */
public enum ApiAuthorizationEnum {

	LOGOUT("/lms/authentication/logout", Arrays.asList(UserRole.SUPER_ADMIN, UserRole.LIBRARIAN, UserRole.USERS)),
	USER_DETAILS("/lms/user/userDetails",Arrays.asList(UserRole.SUPER_ADMIN, UserRole.LIBRARIAN, UserRole.USERS)),
	USERS("/lms/user/users",Arrays.asList(UserRole.SUPER_ADMIN)),
	;

	String api;
	List<UserRole> roles;

	ApiAuthorizationEnum(String api, List<UserRole> roles) {
		this.api = api;
		this.roles = roles;
	}

	private static final Map<String, List<UserRole>> apiRoleMap = new HashMap<>();

	/**
	 * Gives the list of user role allowed for this API.
	 * 
	 * @param api
	 *            API
	 * @return List of allowed user roles
	 */
	public static List<UserRole> getRolesForApi(String api) {
		if (apiRoleMap.isEmpty()) {
			for (ApiAuthorizationEnum e : ApiAuthorizationEnum.values()) {
				apiRoleMap.put(e.api, e.roles);
			}
		}
		return apiRoleMap.get(api);
	}

	/**
	 * Returns if the given API is allowed for the given User role.
	 * 
	 * @param api
	 *            API
	 * @param role
	 *            User role
	 * @return true if user role can access this API.
	 */
	public static boolean doesRoleExistForApi(String api, UserRole role) {
		boolean result = false;
		List<UserRole> rolesForApi = getRolesForApi(api);
		if (!CollectionUtils.isEmpty(rolesForApi)) {
			result = rolesForApi.contains(role);
		}
		return result;
	}
}
