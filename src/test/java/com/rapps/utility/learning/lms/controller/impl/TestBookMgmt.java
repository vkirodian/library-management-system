package com.rapps.utility.learning.lms.controller.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.BookFilter;
import com.rapps.utility.learning.lms.persistence.bean.Book;
import com.rapps.utility.learning.lms.persistence.service.BookService;

@RunWith(MockitoJUnitRunner.class)
public class TestBookMgmt {

	@InjectMocks
	BookMgmt mgmt;

	@Mock
	BookService service;

	@Test
	public void testGetBook() throws LmsException {
		mgmt.getBook("");
	}

	@Test
	public void testGetBooks() {
		mgmt.getBooks(new BookFilter());
	}

	@Test
	public void testAddBook() {
		mgmt.addBook(new Book());
	}

	@Test
	public void testDeleteBook() {
		mgmt.deleteBook("");
	}

	@Test
	public void testUpdateBook() {
		mgmt.updateBook(new Book());
	}

}
