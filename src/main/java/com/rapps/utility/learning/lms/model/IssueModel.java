package com.rapps.utility.learning.lms.model;

import com.rapps.utility.learning.lms.enums.IssueStatusEnum;

/**
 * Model class for Issue.
 * 
 * @author vkirodian
 *
 */
public class IssueModel {

	private String issueId;
	private String bookId;
	private String title;
	private String userId;
	private long issueDate;
	private long returnDate;
	private int noOfReissues;
	private int fine;
	private IssueStatusEnum status;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
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

	public long getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(long issueDate) {
		this.issueDate = issueDate;
	}

	public long getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(long returnDate) {
		this.returnDate = returnDate;
	}

	public int getNoOfReissues() {
		return noOfReissues;
	}

	public void setNoOfReissues(int noOfReissues) {
		this.noOfReissues = noOfReissues;
	}

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}

	public IssueStatusEnum getStatus() {
		return status;
	}

	public void setStatus(IssueStatusEnum status) {
		this.status = status;
	}

}
