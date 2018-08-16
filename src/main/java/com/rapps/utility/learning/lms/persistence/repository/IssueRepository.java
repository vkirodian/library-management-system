package com.rapps.utility.learning.lms.persistence.repository;

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
	 * @return Issue
	 */
	Issue findByBookIdAndUserIdAndStatus(String bookId, String userId, IssueStatusEnum status);
}
