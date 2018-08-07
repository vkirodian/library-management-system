package com.rapps.utility.learning.lms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.BookFilter;
import com.rapps.utility.learning.lms.persistence.bean.Book;

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
	 *            Book Uid
	 * @return
	 * @throws LmsException
	 *             Book not found
	 */
	@GetMapping(value = "books/{uid}")
	Book getBook(String uid) throws LmsException;

	/**
	 * Get all books as per passed filter.
	 * 
	 * @return List of Books
	 */
	@GetMapping(value = "books")
	List<Book> getBooks(BookFilter filter);

	/**
	 * Add a given book
	 * 
	 * @param book
	 *            Book
	 */
	@PostMapping(value = "add")
	void addBook(Book book);

	/**
	 * Delete a book for a given ID.
	 * 
	 * @param uid
	 *            Book uid
	 */
	@DeleteMapping(value = "books/{uid}")
	void deleteBook(String uid);

	/**
	 * Update a given book details.
	 * 
	 * @param book
	 *            Book
	 */
	@PutMapping(value = "update")
	void updateBook(Book book);
}
