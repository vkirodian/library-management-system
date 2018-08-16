package com.rapps.utility.learning.lms.model;

/**
 * Model class for inventory.
 * 
 * @author vkirodian
 *
 */
public class InventoryModel {

	private String bookId;
	private int total;
	private int issued;
	private int requested;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getIssued() {
		return issued;
	}

	public void setIssued(int issued) {
		this.issued = issued;
	}

	public int getRequested() {
		return requested;
	}

	public void setRequested(int requested) {
		this.requested = requested;
	}

}
