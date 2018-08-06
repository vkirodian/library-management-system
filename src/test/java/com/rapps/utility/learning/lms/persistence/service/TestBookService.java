package com.rapps.utility.learning.lms.persistence.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.persistence.bean.Book;
import com.rapps.utility.learning.lms.persistence.repository.BookRepository;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestBookService extends TestCase {

	@InjectMocks
	BookService service;
	
	@Mock
	BookRepository bookRepository;

	@Test
	public void testGetAllBooks() {
		service.getAllBooks();
	}

	@Test
	public void testSaveBook() {
		service.saveBook(new Book());
	}

}
