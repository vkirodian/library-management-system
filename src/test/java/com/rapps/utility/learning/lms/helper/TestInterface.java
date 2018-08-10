package com.rapps.utility.learning.lms.helper;

import com.rapps.utility.learning.lms.annotation.Authorization;
import com.rapps.utility.learning.lms.enums.UserRoleEnum;

public interface TestInterface {

	void methodWithoutAuth();

	@Authorization()
	void methodWithAllowAllAuth();

	@Authorization(roles = { UserRoleEnum.SUPER_ADMIN })
	void methodWithAuth();
}
