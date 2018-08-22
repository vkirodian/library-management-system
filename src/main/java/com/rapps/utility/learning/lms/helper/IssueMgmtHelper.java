package com.rapps.utility.learning.lms.helper;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
import com.rapps.utility.learning.lms.model.UserModel;
import com.rapps.utility.learning.lms.persistence.bean.Session;
import com.rapps.utility.learning.lms.persistence.service.BookService;
import com.rapps.utility.learning.lms.persistence.service.InventoryService;
import com.rapps.utility.learning.lms.persistence.service.IssueService;
import com.rapps.utility.learning.lms.persistence.service.UserService;

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

	@Autowired
	UserService userService;

	/**
	 * Issue a Book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @return
	 * @throws LmsException
	 */
	public IssueModel issueBook(String bookId) throws LmsException {
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
		issue.setTitle(book.getTitle());
		issue.setUserId(getUserId());
		issue.setIssueDate(System.currentTimeMillis());
		issue.setReturnDate(System.currentTimeMillis() + LmsConstants.BOOK_RETURN_DURATION);
		issue.setNoOfReissues(0);
		issue.setFine(0);
		issue.setStatus(IssueStatusEnum.ISSUED);
		issueService.saveIssue(issue);
		return issue;
	}

	/**
	 * ReIssue a book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @return
	 * @throws LmsException
	 */
	public IssueModel reIssueBook(String bookId) throws LmsException {
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
		issuedBook.setTitle(book.getTitle());
		issueService.updateIssue(issuedBook);
		return issuedBook;
	}

	/**
	 * Request a Book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @return 
	 * @throws LmsException
	 */
	public RequestModel requestBook(String bookId) throws LmsException {
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
		request.setTitle(book.getTitle());
		request.setUserId(getUserId());
		request.setRequestDate(System.currentTimeMillis());
		issueService.saveRequest(request);
		return request;
	}

	/**
	 * Return an issued book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @return
	 * @throws LmsException
	 */
	public IssueModel returnBook(String bookId) throws LmsException {
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
			UserModel user = userService.getUserById(getUserId());
			final String to = user.getEmailId();
			final String subject = "Library Management System: Overdue Book Fine";
			final String body = "Book Title: " + book.getTitle() + "\nDue Date: " + new Date(issuedBook.getReturnDate())
					+ "\nOverdue by " + overDueDays + " days\nTotal fine payable: Rs." + fine;
			super.sendEmail(to, subject, body);
		}
		issuedBook.setTitle(book.getTitle());
		issuedBook.setFine(fine);
		issuedBook.setStatus(IssueStatusEnum.RETURNED);
		issueService.updateIssue(issuedBook);

		boolean decreaseIssuedCount = true;
		InventoryModel inventory = inventoryService.getByBookId(bookId);

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
			int requested = inventory.getRequested() - 1;
			inventoryService.updateRequestedCount(bookId, requested);
			decreaseIssuedCount = false;
			UserModel user = userService.getUserById(requestModel.getUserId());
			final String to = user.getEmailId();
			final String subject = "Library Management System: Requested Book available";
			final String body = "Book Title: " + book.getTitle() + "\nRequested Date: "
					+ new Date(requestModel.getRequestDate()) + "\nIs now available and issue to you on: "
					+ new Date(issue.getIssueDate()) + "\nPlease collect it from Library.";
			super.sendEmail(to, subject, body);
		}
		if (decreaseIssuedCount) {
			int issued = inventory.getIssued() - 1;
			inventoryService.updateIssuedCount(bookId, issued);
		}
		return issuedBook;
	}

	/**
	 * Send out reminder email to all users at midnight every day. For users
	 * having books past due date and for users whose books are nearing due
	 * date.
	 */
	@Scheduled(cron = "${emailReminderFrequency}")
	public void sendReminder() {
		// find books for which the return date is less than the reminder date,
		// (e.g. less than 3 days from today)
		long dueTime = System.currentTimeMillis() + LmsConstants.BOOK_DUE_REMINDERS;
		List<IssueModel> issues = issueService.findByReturnDateLessThanAndStatus(dueTime, IssueStatusEnum.ISSUED);
		for (IssueModel issue : issues) {
			try {
				String dueText;
				if (issue.getReturnDate() > System.currentTimeMillis()) {
					dueText = "about to due";
				} else {
					dueText = "overdue";
				}
				BookModel book = validateAndGetBook(issue.getBookId());
				UserModel user = userService.getUserById(issue.getUserId());
				final String to = user.getEmailId();
				final String subject = "Library Management System: Issued Book is " + dueText;
				final String body = "Book Title: " + book.getTitle() + "\nIssued Date: "
						+ new Date(issue.getIssueDate()) + "\nIs " + dueText + " and Return Date: "
						+ new Date(issue.getReturnDate()) + "\nPlease take appropriate action.";
				super.sendEmail(to, subject, body);
			} catch (LmsException e) {
				LOG.error("Error while sending due reminder", e);
			}
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
