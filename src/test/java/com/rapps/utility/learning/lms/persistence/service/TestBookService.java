package com.rapps.utility.learning.lms.persistence.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.BookFilter;
import com.rapps.utility.learning.lms.persistence.bean.Book;
import com.rapps.utility.learning.lms.persistence.repository.BookRepository;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestBookService extends TestCase {

	@InjectMocks
	BookService service;

	@Mock
	BookRepository bookRepository;

	@Test(expected = LmsException.class)
	public void testGetBookById_Exception() throws LmsException {
		Optional<Book> o = Optional.ofNullable(null);
		when(bookRepository.findById("b1")).thenReturn(o);
		service.getBookById("b1");
	}

	@Test
	public void testGetBookById_Success() throws LmsException {
		Book b = new Book();
		Optional<Book> o = Optional.ofNullable(b);
		when(bookRepository.findById("b1")).thenReturn(o);
		Book accb = service.getBookById("b1");
		assertEquals("", b, accb);
	}

	@Test
	public void testGetAllBooks() {
		service.getAllBooks();
	}

	@Test
	public void testSaveBook() {
		service.saveBook(new Book());
	}

	@Test
	public void testGetBooksByFilter() {
		BookFilter bf1 = new BookFilter();
		bf1.setAuthor("author");
		bf1.setCategory("category");
		bf1.setLanguage("language");
		bf1.setTitle("title");
		List<Book> lb1 = new ArrayList<>();
		lb1.add(new Book());
		when(bookRepository.findBooksByFilter("title", "author", "category", "language")).thenReturn(lb1);
		List<Book> acBl1 = service.getBooksByFilter(bf1);
		BookFilter bf2 = new BookFilter();
		List<Book> lb2 = new ArrayList<>();
		lb2.add(new Book());
		when(bookRepository.findBooksByFilter("%", "%", "%", "%")).thenReturn(lb2);
		List<Book> acBl2 = service.getBooksByFilter(bf2);

		assertEquals("", lb1, acBl1);
		assertEquals("", lb2, acBl2);
	}
}
