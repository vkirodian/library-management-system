package com.rapps.utility.learning.lms.controller.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.helper.BookMgmtHelper;
import com.rapps.utility.learning.lms.model.BookModel;

@RunWith(MockitoJUnitRunner.class)
public class TestBookMgmt {

	@InjectMocks
	BookMgmt mgmt;

	@Mock
	BookMgmtHelper helper;

	@Test
	public void testGetBook() throws LmsException {
		mgmt.getBook("");
	}

	@Test
	public void testGetBooks() {
		mgmt.getBooks(new BookModel());
	}

	@Test
	public void testAddBook() throws LmsException {
		mgmt.addBook(new BookModel());
	}

	@Test
	public void testDeleteBook() throws LmsException {
		mgmt.deleteBook("");
	}

	@Test
	public void testUpdateBook() throws LmsException {
		mgmt.updateBook(new BookModel());
	}

}
