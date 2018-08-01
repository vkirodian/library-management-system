package com.rapps.utility.learning.lms.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rapps.utility.learning.lms.persistence.bean.Book;
import com.rapps.utility.learning.lms.persistence.repository.BookRepository;

/**
 * Book service.
 * 
 * @author vkirodia
 *
 */
@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	/**
	 * Get list of all books.
	 * 
	 * @return List of all books
	 */
	public List<Book> getAllBooks() {
		return (List<Book>) bookRepository.findAll();
	}

	/**
	 * Persists the given book.
	 * 
	 * @param book
	 *            Book to be persisted
	 * @return persisted book
	 */
	@Transactional
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}
}
