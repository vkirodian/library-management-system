package com.rapps.utility.learning.lms.helper;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Helper class for application initialization.
 * 
 * @author vkirodian
 *
 */
@Component
public class ApplicationInitializerHelper {

	@Autowired
	SessionMgmtHelper sessionMgmtHelper;

	/**
	 * Actions to be performed on application startup.
	 */
	@PostConstruct
	public void onApplicationStart() {
		sessionMgmtHelper.cleanSessionOnStartup();
	}
}
