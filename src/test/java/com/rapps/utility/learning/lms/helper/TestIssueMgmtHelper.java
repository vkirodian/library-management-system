package com.rapps.utility.learning.lms.helper;

import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.enums.IssueStatusEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
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

@RunWith(MockitoJUnitRunner.class)
public class TestIssueMgmtHelper {

	@InjectMocks
	IssueMgmtHelper helper;

	@Mock
	BookService bookService;
	@Mock
	InventoryService inventoryService;
	@Mock
	IssueService issueService;
	@Mock
	HttpServletRequest httpRequest;

	@Test(expected = LmsException.class)
	public void testIssueBook_BookIDNull() throws LmsException {
		helper.issueBook(null);
	}

	@Test(expected = LmsException.class)
	public void testIssueBook_SessionNull() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn("s1");
		SessionCache.removeAllSessions();
		helper.issueBook("b1");
	}

	@Test(expected = LmsException.class)
	public void testIssueBook_AlreadyIssued() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		when(issueService.findIssueByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED))
				.thenReturn(new IssueModel());
		helper.issueBook("b1");
	}

	@Test(expected = LmsException.class)
	public void testIssueBook_OutOfStock() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		InventoryModel i = new InventoryModel();
		i.setBookId("b1");
		i.setIssued(5);
		i.setTotal(5);
		when(inventoryService.getByBookId("b1")).thenReturn(i);
		helper.issueBook("b1");
	}

	@Test
	public void testIssueBook_Success() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		InventoryModel i = new InventoryModel();
		i.setBookId("b1");
		i.setIssued(3);
		i.setTotal(5);
		when(inventoryService.getByBookId("b1")).thenReturn(i);
		helper.issueBook("b1");
	}

	@Test(expected = LmsException.class)
	public void testReIssueBook_NotAlreadyIssued() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		helper.reIssueBook("b1");
	}

	@Test(expected = LmsException.class)
	public void testReIssueBook_MaxReIssue() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		IssueModel issue = new IssueModel();
		issue.setNoOfReissues(LmsConstants.BOOK_REISSUE_THRESHOLD + 1);
		when(issueService.findIssueByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED)).thenReturn(issue);
		helper.reIssueBook("b1");
	}

	@Test(expected = LmsException.class)
	public void testReIssueBook_Waiting() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		IssueModel issue = new IssueModel();
		issue.setNoOfReissues(0);
		when(issueService.findIssueByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED)).thenReturn(issue);
		InventoryModel i = new InventoryModel();
		i.setBookId("b1");
		i.setIssued(5);
		i.setTotal(5);
		i.setRequested(LmsConstants.MAX_WAITING_QUEUE);
		when(inventoryService.getByBookId("b1")).thenReturn(i);
		helper.reIssueBook("b1");
	}

	@Test
	public void testReIssueBook_Success() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		IssueModel issue = new IssueModel();
		issue.setNoOfReissues(0);
		when(issueService.findIssueByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED)).thenReturn(issue);
		InventoryModel i = new InventoryModel();
		i.setBookId("b1");
		when(inventoryService.getByBookId("b1")).thenReturn(i);
		helper.reIssueBook("b1");
	}

	@Test(expected = LmsException.class)
	public void testRequestBook_AlreadyIssued() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		when(issueService.findIssueByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED))
				.thenReturn(new IssueModel());
		helper.requestBook("b1");
	}
	
	@Test(expected = LmsException.class)
	public void testRequestBook_AlreadyRequested() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		InventoryModel i = new InventoryModel();
		i.setBookId("b1");
		i.setIssued(3);
		i.setTotal(5);
		when(inventoryService.getByBookId("b1")).thenReturn(i);
		when(issueService.findRequestByBookIdAndUserId("b1", "u1")).thenReturn(new RequestModel());
		helper.requestBook("b1");
	}

	@Test(expected = LmsException.class)
	public void testRequestBook_BookAvailable() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		InventoryModel i = new InventoryModel();
		i.setBookId("b1");
		i.setIssued(3);
		i.setTotal(5);
		when(inventoryService.getByBookId("b1")).thenReturn(i);
		helper.requestBook("b1");
	}

	@Test
	public void testRequestBook_Success() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		InventoryModel i = new InventoryModel();
		i.setBookId("b1");
		i.setIssued(5);
		i.setTotal(5);
		when(inventoryService.getByBookId("b1")).thenReturn(i);
		helper.requestBook("b1");
	}

	@Test(expected = LmsException.class)
	public void testReturnBook_NotIssued() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		helper.returnBook("b1");
	}

	@Test
	public void testReturnBook_SuccessWithFine_AndWithRequest() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		IssueModel issue = new IssueModel();
		issue.setReturnDate(0);
		when(issueService.findIssueByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED)).thenReturn(issue);
		RequestModel reqModel = new RequestModel();
		reqModel.setRequestId("r1");
		reqModel.setBookId("b1");
		reqModel.setUserId("u1");
		when(issueService.findOldestRequesterForABook("b1")).thenReturn(reqModel);
		helper.returnBook("b1");
	}

	@Test
	public void testReturnBook_SuccessWithOutFine_AndWithoutRequest() throws LmsException {
		when(bookService.getBookById("b1")).thenReturn(new BookModel());
		getSession();
		IssueModel issue = new IssueModel();
		issue.setReturnDate(System.currentTimeMillis() + LmsConstants.BOOK_RETURN_DURATION);
		when(issueService.findIssueByBookIdAndUserIdAndStatus("b1", "u1", IssueStatusEnum.ISSUED)).thenReturn(issue);
		helper.returnBook("b1");
	}

	private void getSession() {
		when(httpRequest.getHeader(LmsConstants.SESSION_ID)).thenReturn("s1");
		Session s1 = new Session();
		s1.setSessionId("s1");
		s1.setUserId("u1");
		SessionCache.removeAllSessions();
		SessionCache.addSessionToCache(s1);
	}
}
