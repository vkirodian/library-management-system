package com.rapps.utility.learning.lms.helper;

import com.rapps.utility.learning.lms.annotation.Authorization;
import com.rapps.utility.learning.lms.enums.AccessTypeEnum;

public interface TestInterface {

	void methodWithoutAuth();

	@Authorization(accessType = AccessTypeEnum.AUTH_ALL, skipSession = true)
	void methodSkipSession();

	@Authorization(accessType = AccessTypeEnum.AUTH_ALL)
	void methodWithAuth();
}
