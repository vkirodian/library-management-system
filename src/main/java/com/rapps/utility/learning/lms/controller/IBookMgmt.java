package com.rapps.utility.learning.lms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rapps.utility.learning.lms.annotation.Authorization;
import com.rapps.utility.learning.lms.enums.AccessTypeEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.BookModel;

/**
 * Interface providing API' related to Book management.
 * 
 * @author vkirodian
 *
 */
@RequestMapping("/lms/book")
public interface IBookMgmt {

	/**
	 * Get a book for the given ID.
	 * 
	 * @param uid
	 *            Book ID
	 * @return Book
	 * @throws LmsException
	 *             Book not found
	 */
	@GetMapping(value = "books/{uid}")
	@Authorization(accessType = AccessTypeEnum.BOOK_VIEWONLY)
	BookModel getBook(String uid) throws LmsException;

	/**
	 * Get all books as per passed filter.
	 * 
	 * @return List of Books
	 */
	@GetMapping(value = "books")
	@Authorization(accessType = AccessTypeEnum.BOOK_VIEWONLY)
	List<BookModel> getBooks(BookModel filter);

	/**
	 * Add a given book
	 * 
	 * @param book
	 *            Book
	 * @throws LmsException
	 *             Mandatory field missing
	 */
	@PostMapping(value = "add")
	@Authorization(accessType = AccessTypeEnum.BOOK_MGMT)
	BookModel addBook(BookModel book) throws LmsException;

	/**
	 * Delete a book for a given ID.
	 * 
	 * @param uid
	 *            Book ID
	 * @throws LmsException
	 *             Book not found
	 */
	@DeleteMapping(value = "books/{uid}")
	@Authorization(accessType = AccessTypeEnum.BOOK_MGMT)
	void deleteBook(String uid) throws LmsException;

	/**
	 * Update a given book details.
	 * 
	 * @param book
	 *            Book
	 * @throws LmsException
	 *             Book not found
	 */
	@PutMapping(value = "update")
	@Authorization(accessType = AccessTypeEnum.BOOK_MGMT)
	BookModel updateBook(BookModel book) throws LmsException;
}
