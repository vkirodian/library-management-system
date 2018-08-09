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

	LOGOUT("/lms/authentication/logout", UserRoleEnum.getAllRoles()),
	UPDATE_PASSWORD("/lms/authentication/updatePassword", UserRoleEnum.getAllRoles()),
	USER_DETAILS("/lms/user/userDetails", UserRoleEnum.getAllRoles()),
	USERS("/lms/user/users", Arrays.asList(UserRoleEnum.SUPER_ADMIN, UserRoleEnum.LIBRARIAN)),
	USER_UPDATE("/lms/user/update", UserRoleEnum.getAllRoles()),
	BOOK_BY_ID("/lms/book/books/", UserRoleEnum.getAllRoles()),
	BOOKS_BY_FILTER("/lms/book/books", UserRoleEnum.getAllRoles()),
	BOOK_ADD("/lms/book/add", Arrays.asList(UserRoleEnum.LIBRARIAN)),
	BOOK_DELETE("/lms/book/books/", Arrays.asList(UserRoleEnum.LIBRARIAN)),
	BOOK_UPDATE("/lms/book/update", Arrays.asList(UserRoleEnum.LIBRARIAN)),
	;

	String api;
	List<UserRoleEnum> roles;

	ApiAuthorizationEnum(String api, List<UserRoleEnum> roles) {
		this.api = api;
		this.roles = roles;
	}

	private static final Map<String, List<UserRoleEnum>> apiRoleMap = new HashMap<>();

	/**
	 * Gives the list of user role allowed for this API.
	 * 
	 * @param api
	 *            API
	 * @return List of allowed user roles
	 */
	public static List<UserRoleEnum> getRolesForApi(String api) {
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
	public static boolean doesRoleExistForApi(String api, UserRoleEnum role) {
		boolean result = false;
		List<UserRoleEnum> rolesForApi = getRolesForApi(api);
		if (!CollectionUtils.isEmpty(rolesForApi)) {
			result = rolesForApi.contains(role);
		}
		return result;
	}
}
