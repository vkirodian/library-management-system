package com.rapps.utility.learning.lms.persistence.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rapps.utility.learning.lms.enums.IssueStatusEnum;
import com.rapps.utility.learning.lms.model.IssueModel;
import com.rapps.utility.learning.lms.persistence.bean.Issue;
import com.rapps.utility.learning.lms.persistence.repository.IssueRepository;

/**
 * Service class for Issue.
 * 
 * @author vkirodian
 *
 */
@Service
public class IssueService extends BaseService<Issue> {

	@Autowired
	IssueRepository issueRepository;

	/**
	 * Save a Issue.
	 * 
	 * @param issue
	 *            Issue
	 */
	public void saveIssue(IssueModel issue) {
		issue.setIssueId(UUID.randomUUID().toString());
		issueRepository.save(Converter.convertObject(issue, Issue.class));
	}
	
	public void updateIssue(IssueModel issue) {
		issueRepository.save(Converter.convertObject(issue, Issue.class));
	}

	/**
	 * Find a issue made by a user for a book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @param userId
	 *            User ID
	 * @param status
	 *            Issue status
	 * @return Issue
	 */
	public IssueModel findByBookIdAndUserIdAndStatus(String bookId, String userId, IssueStatusEnum status) {
		Issue issue = issueRepository.findByBookIdAndUserIdAndStatus(bookId, userId, status);
		if (issue != null) {
			return Converter.convertObject(issue, IssueModel.class);
		} else {
			return null;
		}

	}
}
