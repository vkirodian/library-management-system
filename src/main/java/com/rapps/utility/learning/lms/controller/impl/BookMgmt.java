package com.rapps.utility.learning.lms.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rapps.utility.learning.lms.controller.IBookMgmt;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.helper.BookMgmtHelper;
import com.rapps.utility.learning.lms.model.BookModel;

/**
 * Implementation of book management related API'
 * 
 * @author vkirodian
 *
 */
@RestController
public class BookMgmt implements IBookMgmt {

	@Autowired
	BookMgmtHelper helper;

	@Override
	public BookModel getBook(@PathVariable("uid") String uid) throws LmsException {
		return helper.getBookById(uid);
	}

	@Override
	public List<BookModel> getBooks(BookModel filter) {
		return helper.getBooksByFilter(filter);
	}

	@Override
	public BookModel addBook(@RequestBody BookModel book) throws LmsException {
		return helper.saveBook(book);
	}

	@Override
	public void deleteBook(@PathVariable("uid") String uid) throws LmsException {
		helper.deleteBook(uid);
	}

	@Override
	public BookModel updateBook(@RequestBody BookModel book) throws LmsException {
		return helper.updateBook(book);
	}

}
