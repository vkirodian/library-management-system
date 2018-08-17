package com.rapps.utility.learning.lms.persistence.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rapps.utility.learning.lms.enums.IssueStatusEnum;
import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.model.IssueModel;
import com.rapps.utility.learning.lms.model.RequestModel;
import com.rapps.utility.learning.lms.persistence.bean.Issue;
import com.rapps.utility.learning.lms.persistence.bean.Request;
import com.rapps.utility.learning.lms.persistence.repository.IssueRepository;
import com.rapps.utility.learning.lms.persistence.repository.RequestRepository;

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

	@Autowired
	RequestRepository requestRepository;

	/**
	 * Save an Issue.
	 * 
	 * @param issue
	 *            Issue
	 */
	public void saveIssue(IssueModel issue) {
		issue.setIssueId(UUID.randomUUID().toString());
		issueRepository.save(Converter.convertObject(issue, Issue.class));
	}

	/**
	 * Update an Issue.
	 * 
	 * @param issue
	 *            Issue
	 */
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
	public IssueModel findIssueByBookIdAndUserIdAndStatus(String bookId, String userId, IssueStatusEnum status) {
		Issue issue = issueRepository.findByBookIdAndUserIdAndStatus(bookId, userId, status);
		if (issue != null) {
			return Converter.convertObject(issue, IssueModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Save an Request.
	 * 
	 * @param request
	 *            Request
	 */
	public void saveRequest(RequestModel request) {
		request.setRequestId(UUID.randomUUID().toString());
		requestRepository.save(Converter.convertObject(request, Request.class));
	}

	/**
	 * Update an Request.
	 * 
	 * @param request
	 *            Request
	 */
	public void updateRequest(RequestModel request) {
		requestRepository.save(Converter.convertObject(request, Request.class));
	}

	/**
	 * Find by Book ID and User ID.
	 * 
	 * @param bookId
	 *            Book ID
	 * @param userId
	 *            User ID
	 * @return Request
	 */
	public RequestModel findRequestByBookIdAndUserId(String bookId, String userId) {
		Request request = requestRepository.findByBookIdAndUserId(bookId, userId);
		if (request != null) {
			return Converter.convertObject(request, RequestModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Find the oldest requester for a given book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @return Oldest Requester
	 */
	public RequestModel findOldestRequesterForABook(String bookId) {
		Request request = requestRepository.findTop1ByBookIdOrderByRequestDate(bookId);
		if (request != null) {
			return Converter.convertObject(request, RequestModel.class);
		} else {
			return null;
		}
	}

	/**
	 * Delete request.
	 * 
	 * @param requestId
	 *            RequestID
	 * @throws LmsException
	 *             If Request not found
	 */
	public void deleteRequestById(String requestId) throws LmsException {
		try {
			requestRepository.deleteById(requestId);
		} catch (EmptyResultDataAccessException e) {
			LOG.error("Item with ID not found");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.ENTITY_TO_DELETE_NOT_FOUND, requestId);
		}
	}
}
