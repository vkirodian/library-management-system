package com.rapps.utility.learning.lms.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestApplicationInitializerHelper extends TestCase {

	@InjectMocks
	ApplicationInitializerHelper helper;

	@Mock
	SessionMgmtHelper sessionMgmtHelper;

	@Test
	public void testOnApplicationStart() {
		helper.onApplicationStart();
	}

}
