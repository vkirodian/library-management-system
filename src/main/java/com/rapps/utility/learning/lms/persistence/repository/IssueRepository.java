package com.rapps.utility.learning.lms.persistence.repository;

import java.util.List;

import com.rapps.utility.learning.lms.enums.IssueStatusEnum;
import com.rapps.utility.learning.lms.persistence.bean.Issue;

/**
 * Repository for Issue.
 * 
 * @author vkirodian
 *
 */
public interface IssueRepository extends BaseRepository<Issue> {

	/**
	 * Find a issue made by a user for a book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @param userId
	 *            User ID
	 * @param status
	 *            Status
	 * @return Issue
	 */
	Issue findByBookIdAndUserIdAndStatus(String bookId, String userId, IssueStatusEnum status);

	/**
	 * Find Issue less than the return date and for a given status,
	 * 
	 * @param returnDate
	 *            Return Date
	 * @param status
	 *            Status
	 * @return List of Issue
	 */
	List<Issue> findByReturnDateLessThanAndStatus(long returnDate, IssueStatusEnum status);
}
