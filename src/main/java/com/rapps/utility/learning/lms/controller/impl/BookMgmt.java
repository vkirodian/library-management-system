package com.rapps.utility.learning.lms.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rapps.utility.learning.lms.controller.IBookMgmt;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.BookFilter;
import com.rapps.utility.learning.lms.persistence.bean.Book;
import com.rapps.utility.learning.lms.persistence.service.BookService;

/**
 * Implementation of book management related API'
 * 
 * @author vkirodian
 *
 */
@RestController
public class BookMgmt implements IBookMgmt {

	@Autowired
	BookService service;
	
	@Override
	public Book getBook(@PathVariable("uid") String uid) throws LmsException {
		return service.getBookById(uid);
	}

	@Override
	public List<Book> getBooks(BookFilter filter) {
		return service.getBooksByFilter(filter);
	}

	@Override
	public void addBook(@RequestBody Book book) {
	}

	@Override
	public void deleteBook(@PathVariable("uid") String uid) {
	}

	@Override
	public void updateBook(@RequestBody Book book) {
	}

}
