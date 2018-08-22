package com.rapps.utility.learning.lms.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rapps.utility.learning.lms.controller.IIssueMgmt;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.helper.IssueMgmtHelper;
import com.rapps.utility.learning.lms.model.IssueModel;
import com.rapps.utility.learning.lms.model.RequestModel;

/**
 * Implementing class for API related to Issuing Mgmt.
 * 
 * @author vkirodian
 *
 */
@RestController
public class IssueMgmt implements IIssueMgmt {

	@Autowired
	IssueMgmtHelper helper;

	@Override
	public IssueModel issueBook(@PathVariable("bookId") String bookId) throws LmsException {
		return helper.issueBook(bookId);
	}

	@Override
	public IssueModel reIssueBook(@PathVariable("bookId") String bookId) throws LmsException {
		return helper.reIssueBook(bookId);
	}

	@Override
	public RequestModel requestBook(@PathVariable("bookId") String bookId) throws LmsException {
		return helper.requestBook(bookId);
	}

	@Override
	public IssueModel returnBook(@PathVariable("bookId") String bookId) throws LmsException {
		return helper.returnBook(bookId);
	}

}
