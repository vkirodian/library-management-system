package com.rapps.utility.learning.lms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rapps.utility.learning.lms.annotation.Authorization;
import com.rapps.utility.learning.lms.enums.AccessTypeEnum;
import com.rapps.utility.learning.lms.exception.LmsException;

/**
 * Interface providing API' related to issuing management.
 * 
 * @author vkirodian
 *
 */
@RequestMapping("/lms/issue")
public interface IIssueMgmt {

	/**
	 * Issue a Book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @throws LmsException
	 *             If book not found.
	 */
	@GetMapping(value = "issue/{bookId}")
	@Authorization(accessType = AccessTypeEnum.ISSUE_USERS)
	void issueBook(String bookId) throws LmsException;

	/**
	 * Re-Issue a Book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @throws LmsException
	 *             If book not found.
	 */
	@GetMapping(value = "reissue/{bookId}")
	@Authorization(accessType = AccessTypeEnum.ISSUE_USERS)
	void reIssueBook(String bookId) throws LmsException;

	/**
	 * Add self to waiting list for a Book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @throws LmsException
	 *             If book not found.
	 */
	@GetMapping(value = "waiting/{bookId}")
	@Authorization(accessType = AccessTypeEnum.ISSUE_USERS)
	void requestBook(String bookId) throws LmsException;

	/**
	 * Return a Book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @throws LmsException
	 *             If book not found.
	 */
	@GetMapping(value = "return/{bookId}")
	@Authorization(accessType = AccessTypeEnum.ISSUE_USERS)
	void returnBook(String bookId) throws LmsException;
}
