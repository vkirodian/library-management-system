package com.rapps.utility.learning.lms.helper;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationInitializerHelper.class);

	@Autowired
	SessionMgmtHelper sessionMgmtHelper;

	/**
	 * Actions to be performed on application startup.
	 */
	@PostConstruct
	public void onApplicationStart() {
		LOG.debug("Cleaning session cache on start-up");
		sessionMgmtHelper.cleanSessionOnStartup();
	}
}
