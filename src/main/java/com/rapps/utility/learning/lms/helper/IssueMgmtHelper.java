package com.rapps.utility.learning.lms.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rapps.utility.learning.lms.enums.IssueStatusEnum;
import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.global.LmsConstants;
import com.rapps.utility.learning.lms.global.SessionCache;
import com.rapps.utility.learning.lms.model.BookModel;
import com.rapps.utility.learning.lms.model.InventoryModel;
import com.rapps.utility.learning.lms.model.IssueModel;
import com.rapps.utility.learning.lms.model.RequestModel;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.service.BookService;
import com.rapps.utility.learning.lms.persistence.service.InventoryService;
import com.rapps.utility.learning.lms.persistence.service.IssueService;

/**
 * Helper class for Issue management.
 * 
 * @author vkirodian
 *
 */
@Component
public class IssueMgmtHelper extends BaseHelper {

	private static final Logger LOG = LoggerFactory.getLogger(IssueMgmtHelper.class);

	@Autowired
	BookService bookService;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	IssueService issueService;

	/**
	 * Issue a Book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @throws LmsException
	 */
	public void issueBook(String bookId) throws LmsException {
		BookModel book = validateAndGetBook(bookId);
		IssueModel dbIssuedBook = issueService.findIssueByBookIdAndUserIdAndStatus(bookId, getUserId(),
				IssueStatusEnum.ISSUED);
		if (dbIssuedBook != null) {
			LOG.error("Book already issued in users name");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.BOOK_ALREADY_ISSUED, book.getTitle());
		}
		InventoryModel inventory = inventoryService.getByBookId(bookId);
		if (inventory.getTotal() == inventory.getIssued()) {
			LOG.error("All books are issued");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.BOOK_OUT_OF_STOCK, book.getTitle());
		}
		int count = inventory.getIssued() + 1;
		inventoryService.updateIssuedCount(bookId, count);

		IssueModel issue = new IssueModel();
		issue.setBookId(book.getBookId());
		issue.setUserId(getUserId());
		issue.setIssueDate(System.currentTimeMillis());
		issue.setReturnDate(System.currentTimeMillis() + LmsConstants.BOOK_RETURN_DURATION);
		issue.setNoOfReissues(0);
		issue.setFine(0);
		issue.setStatus(IssueStatusEnum.ISSUED);
		issueService.saveIssue(issue);
	}

	/**
	 * ReIssue a book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @throws LmsException
	 */
	public void reIssueBook(String bookId) throws LmsException {
		BookModel book = validateAndGetBook(bookId);
		InventoryModel inventory = inventoryService.getByBookId(bookId);
		IssueModel issuedBook = issueService.findIssueByBookIdAndUserIdAndStatus(bookId, getUserId(),
				IssueStatusEnum.ISSUED);
		if (issuedBook == null) {
			LOG.error("Book currently not issue in users name");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.BOOK_NOT_ISSUED, book.getTitle(), "User");
		}
		if (issuedBook.getNoOfReissues() >= LmsConstants.BOOK_REISSUE_THRESHOLD) {
			LOG.error("Reissue limit reached");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.REISSUE_THRESHOLD_REACHED,
					issuedBook.getNoOfReissues());
		}
		if (inventory.getRequested() >= LmsConstants.MAX_WAITING_QUEUE) {
			LOG.error("Cannot reissue book, long wating queue for this book");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.LONG_WAITING_LIST, issuedBook.getNoOfReissues());
		}
		issuedBook.setNoOfReissues(issuedBook.getNoOfReissues() + 1);
		issuedBook.setReturnDate(System.currentTimeMillis() + LmsConstants.BOOK_RETURN_DURATION);
		issueService.updateIssue(issuedBook);
	}

	/**
	 * Request a Book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @throws LmsException
	 */
	public void requestBook(String bookId) throws LmsException {
		BookModel book = validateAndGetBook(bookId);
		IssueModel issuedBook = issueService.findIssueByBookIdAndUserIdAndStatus(bookId, getUserId(),
				IssueStatusEnum.ISSUED);
		if (issuedBook != null) {
			LOG.error("Book already issued in users name");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.BOOK_ALREADY_ISSUED, book.getTitle());
		}
		RequestModel dbRequest = issueService.findRequestByBookIdAndUserId(bookId, getUserId());
		if (dbRequest != null) {
			LOG.error("Book already requested in users name");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.BOOK_ALREADY_REQUESTED, book.getTitle());
		}
		InventoryModel inventory = inventoryService.getByBookId(bookId);
		if (inventory.getTotal() > inventory.getIssued()) {
			LOG.error("Book are available, please issue the book");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.CANNOT_ADD_TO_WAITING_BOOK_AVAILABLE,
					book.getTitle());
		}
		int count = inventory.getRequested() + 1;
		inventoryService.updateRequestedCount(bookId, count);

		RequestModel request = new RequestModel();
		request.setBookId(bookId);
		request.setUserId(getUserId());
		request.setRequestDate(System.currentTimeMillis());
		issueService.saveRequest(request);
	}

	/**
	 * Return an issued book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @throws LmsException
	 */
	public void returnBook(String bookId) throws LmsException {
		BookModel book = validateAndGetBook(bookId);
		IssueModel issuedBook = issueService.findIssueByBookIdAndUserIdAndStatus(bookId, getUserId(),
				IssueStatusEnum.ISSUED);
		if (issuedBook == null) {
			LOG.error("Book currently not issue in users name");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.BOOK_NOT_ISSUED, book.getTitle(), "User");
		}
		long currentTime = System.currentTimeMillis();
		int fine = 0;
		if (issuedBook.getReturnDate() < currentTime) {
			long overDue = currentTime - issuedBook.getReturnDate();
			int overDueDays = (int) (overDue / (24 * 60 * 60 * 1000));
			fine = LmsConstants.PER_DAY_FINE * overDueDays;
			// TODO send fine email
		}
		issuedBook.setFine(fine);
		issuedBook.setStatus(IssueStatusEnum.RETURNED);
		issueService.updateIssue(issuedBook);

		RequestModel requestModel = issueService.findOldestRequesterForABook(bookId);
		if (requestModel != null) {
			IssueModel issue = new IssueModel();
			issue.setBookId(requestModel.getBookId());
			issue.setUserId(requestModel.getUserId());
			issue.setIssueDate(System.currentTimeMillis());
			issue.setReturnDate(System.currentTimeMillis() + LmsConstants.BOOK_RETURN_DURATION);
			issue.setNoOfReissues(0);
			issue.setFine(0);
			issue.setStatus(IssueStatusEnum.ISSUED);
			issueService.saveIssue(issue);
			issueService.deleteRequestById(requestModel.getRequestId());
			// TODO Send email to waiting list user.
		}
	}

	private BookModel validateAndGetBook(String bookId) throws LmsException {
		if (StringUtils.isEmpty(bookId)) {
			LOG.error("Book ID is empty");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "Book ID", "Book");
		}
		return bookService.getBookById(bookId);
	}

	private String getUserId() throws LmsException {
		String sessionId = super.getSessionId();
		Session session = SessionCache.sessionExists(sessionId);
		if (session == null) {
			LOG.error("Session not found");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.SESSION_NOT_FOUND);
		}
		return session.getUserId();
	}
}
