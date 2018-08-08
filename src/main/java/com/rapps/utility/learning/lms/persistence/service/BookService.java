package com.rapps.utility.learning.lms.persistence.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.model.BookFilter;
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
	 * Get a book for the given ID.
	 * 
	 * @param uid
	 *            Book ID
	 * @return Book
	 * @throws LmsException
	 *             If book not found
	 */
	public Book getBookById(String uid) throws LmsException {
		Optional<Book> optional = bookRepository.findById(uid);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.BOOK_NOT_FOUND, uid);
		}
	}

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

	/**
	 * Get list of Books for given filter.
	 * 
	 * @param filter
	 *            Book Filter
	 * @return List of Books
	 */
	public List<Book> getBooksByFilter(BookFilter filter) {
		String title = filter.getTitle() == null ? "%" : filter.getTitle().toLowerCase();
		String author = filter.getAuthor() == null ? "%" : filter.getAuthor().toLowerCase();
		String category = filter.getCategory() == null ? "%" : filter.getCategory().toLowerCase();
		String language = filter.getLanguage() == null ? "%" : filter.getLanguage().toLowerCase();
		return bookRepository.findBooksByFilter(title, author, category, language);
	}
}
