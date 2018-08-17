package com.rapps.utility.learning.lms.persistence.repository;

import com.rapps.utility.learning.lms.persistence.bean.Request;

/**
 * Request repository.
 * 
 * @author vkirodian
 *
 */
public interface RequestRepository extends BaseRepository<Request> {

	/**
	 * Find by Book ID and User ID.
	 * 
	 * @param bookId
	 *            Book ID
	 * @param userId
	 *            User ID
	 * @return Request
	 */
	Request findByBookIdAndUserId(String bookId, String userId);

	/**
	 * Find the oldest requester for a given book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @return Oldest Requester
	 */
	Request findTop1ByBookIdOrderByRequestDate(String bookId);
}
