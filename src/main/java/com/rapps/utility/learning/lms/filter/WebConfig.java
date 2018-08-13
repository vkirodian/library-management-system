package com.rapps.utility.learning.lms.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rapps.utility.learning.lms.helper.AccessRoleMgmtHelper;
import com.rapps.utility.learning.lms.helper.SessionMgmtHelper;

/**
 * Defines callback methods to customize the Java-based configuration for Spring
 * MVC enabled via {@code WebMvcConfigurer}.
 * 
 * @author vkirodian
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	SessionMgmtHelper sessionMgmtHelper;

	@Autowired
	AccessRoleMgmtHelper accessRoleHelper;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthorizationInterceptor(sessionMgmtHelper, accessRoleHelper));
	}

}
