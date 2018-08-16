package com.rapps.utility.learning.lms.persistence.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rapps.utility.learning.lms.enums.IssueStatusEnum;

@Entity
@Table(name = "issue")
public class Issue {

	@Id
	@Column(name = "ISSUEID")
	private String issueId;

	@Column(name = "BOOKID")
	private String bookId;

	@Column(name = "USERID")
	private String userId;

	@Column(name = "ISSUEDATE")
	private long issueDate;

	@Column(name = "RETURNDATE")
	private long returnDate;

	@Column(name = "NOOFREISSUES")
	private int noOfReissues;

	@Column(name = "FINE")
	private int fine;

	@Column(name = "STATUS")
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
