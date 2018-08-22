package com.rapps.utility.learning.lms.model;

/**
 * Model class for Request bean.
 * 
 * @author vkirodian
 *
 */
public class RequestModel {

	private String requestId;
	private String bookId;
	private String title;
	private String userId;
	private long requestDate;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(long requestDate) {
		this.requestDate = requestDate;
	}

}
