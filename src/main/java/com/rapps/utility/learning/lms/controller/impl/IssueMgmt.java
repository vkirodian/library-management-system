package com.rapps.utility.learning.lms.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rapps.utility.learning.lms.controller.IIssueMgmt;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.helper.IssueMgmtHelper;

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
	public void issueBook(@PathVariable("bookId") String bookId) throws LmsException {
		helper.issueBook(bookId);
	}

	@Override
	public void reIssueBook(@PathVariable("bookId") String bookId) throws LmsException {
		helper.reIssueBook(bookId);
	}

	@Override
	public void requestBook(@PathVariable("bookId") String bookId) throws LmsException {
		helper.requestBook(bookId);
	}

	@Override
	public void returnBook(@PathVariable("bookId") String bookId) throws LmsException {
		helper.returnBook(bookId);
	}

}
