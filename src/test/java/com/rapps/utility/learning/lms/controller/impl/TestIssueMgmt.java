package com.rapps.utility.learning.lms.controller.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.helper.IssueMgmtHelper;

@RunWith(MockitoJUnitRunner.class)
public class TestIssueMgmt {

	@InjectMocks
	IssueMgmt mgmt;

	@Mock
	IssueMgmtHelper helper;

	@Test
	public void testIssueBook() throws LmsException {
		mgmt.issueBook("");
	}

	@Test
	public void testReIssueBook() throws LmsException {
		mgmt.reIssueBook("");
	}

	@Test
	public void testAddToWaitingList() throws LmsException {
		mgmt.requestBook("");
	}

	@Test
	public void testReturnBook() throws LmsException {
		mgmt.returnBook("");
	}

}
