package com.rapps.utility.learning.lms.persistence.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.BookModel;
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
		b.setTitle("My First Book");
		Optional<Book> o = Optional.ofNullable(b);
		when(bookRepository.findById("b1")).thenReturn(o);
		BookModel accb = service.getBookById("b1");
		assertEquals("", b.getTitle(), accb.getTitle());
	}

	@Test
	public void testGetAllBooks() {
		service.getAllBooks();
	}

	@Test
	public void testSaveBook() {
		BookModel b = new BookModel();
		b.setTitle("My First Book");
		b.setAuthor("Author");
		Book book = Converter.convertObject(b, Book.class);
		when(bookRepository.save(any(Book.class))).thenReturn(book);
		service.saveBook(b);
	}

	@Test
	public void testGetBooksByFilter() {
		BookModel inputBookModel1 = new BookModel();
		inputBookModel1.setAuthor("author");
		inputBookModel1.setCategory("category");
		inputBookModel1.setLanguage("language");
		inputBookModel1.setTitle("title");
		inputBookModel1.setEdition("edition");
		Book b1 = new Book();
		b1.setTitle("Title 1");
		List<Book> outputBook1 = new ArrayList<>();
		outputBook1.add(b1);
		when(bookRepository.findBooksByFilter("title", "author", "category", "language", "edition"))
				.thenReturn(outputBook1);
		List<BookModel> acBl1 = service.getBooksByFilter(inputBookModel1);

		BookModel inputBookModel2 = new BookModel();
		Book b2 = new Book();
		b2.setTitle("Title 2");
		List<Book> outputBook2 = new ArrayList<>();
		outputBook2.add(b2);
		when(bookRepository.findBooksByFilter("%", "%", "%", "%", "%")).thenReturn(outputBook2);
		List<BookModel> acBl2 = service.getBooksByFilter(inputBookModel2);

		assertEquals("", "Title 1", acBl1.get(0).getTitle());
		assertEquals("", "Title 2", acBl2.get(0).getTitle());
	}

	@Test
	public void testUpdateBook() throws LmsException {
		BookModel inputBookModel = new BookModel();
		inputBookModel.setAuthor("author");
		inputBookModel.setCategory("category");
		inputBookModel.setLanguage("language");
		inputBookModel.setTitle("title");
		inputBookModel.setEdition("edition");
		inputBookModel.setBookId("b1");

		Book b = new Book();
		b.setTitle("NMew Title");

		Book dbBook = new Book();
		dbBook.setTitle("My First Book");
		Optional<Book> o = Optional.ofNullable(b);
		when(bookRepository.findById("b1")).thenReturn(o);

		when(bookRepository.save(any(Book.class))).thenReturn(b);
		BookModel acc = service.updateBook(inputBookModel);
		assertEquals("", "NMew Title", acc.getTitle());
	}

	@Test
	public void testDeleteBook() throws LmsException {
		service.deleteBook("");
	}

	@Test(expected = LmsException.class)
	public void testDeleteBook_ItemNotFound() throws LmsException {
		doThrow(new EmptyResultDataAccessException(1)).when(bookRepository).deleteById("id");
		service.deleteBook("id");
	}
}
