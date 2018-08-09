package com.rapps.utility.learning.lms.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.BookModel;
import com.rapps.utility.learning.lms.persistence.service.BookService;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestBookMgmtHelper extends TestCase {

	@InjectMocks
	BookMgmtHelper helper;

	@Mock
	BookService service;

	@Test(expected = LmsException.class)
	public void testGetBookById_IDNull() throws LmsException {
		helper.getBookById(null);
	}

	@Test
	public void testGetBookById() throws LmsException {
		helper.getBookById("id");
	}

	@Test
	public void testGetBooksByFilter() {
		helper.getBooksByFilter(new BookModel());
	}

	@Test(expected = LmsException.class)
	public void testSaveBook_TitleMissing() throws LmsException {
		BookModel b = new BookModel();
		b.setAuthor("My First Author");
		helper.saveBook(b);
	}

	@Test(expected = LmsException.class)
	public void testSaveBook_AuthorMissing() throws LmsException {
		BookModel b = new BookModel();
		b.setTitle("My First Book");
		helper.saveBook(b);
	}

	@Test
	public void testSaveBook_Success() throws LmsException {
		BookModel b = new BookModel();
		b.setTitle("My First Book");
		b.setAuthor("My First Author");
		helper.saveBook(b);
	}

	@Test(expected = LmsException.class)
	public void testDeleteBook_IDNull() throws LmsException {
		helper.deleteBook(null);
	}

	@Test
	public void testDeleteBook() throws LmsException {
		helper.deleteBook("id");
	}

	@Test(expected = LmsException.class)
	public void testUpdateBook_IDNull() throws LmsException {
		helper.updateBook(new BookModel());
	}
	
	@Test(expected = LmsException.class)
	public void testUpdateBook_TitleNull() throws LmsException {
		BookModel m = new BookModel();
		m.setBookId("bk1");
		m.setAuthor("author");
		helper.updateBook(m);
	}
	
	@Test(expected = LmsException.class)
	public void testUpdateBook_AuthoNull() throws LmsException {
		BookModel m = new BookModel();
		m.setBookId("bk1");
		m.setTitle("Title");
		helper.updateBook(m);
	}

	@Test
	public void testUpdateBook() throws LmsException {
		BookModel m = new BookModel();
		m.setBookId("bk1");
		m.setTitle("Title");
		m.setAuthor("author");
		helper.updateBook(m);
	}
}
