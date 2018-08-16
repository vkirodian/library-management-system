package com.rapps.utility.learning.lms.helper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.model.BookModel;
import com.rapps.utility.learning.lms.persistence.service.BookService;

/**
 * Helper for Book management.
 * 
 * @author vkirodian
 *
 */
@Component
public class BookMgmtHelper {
	
	private static final Logger LOG = LoggerFactory.getLogger(BookMgmtHelper.class);

	@Autowired
	BookService service;

	/**
	 * Get boob by ID.
	 * 
	 * @param uid
	 *            Book ID
	 * @return Book
	 * @throws LmsException
	 *             If book not found
	 */
	public BookModel getBookById(String uid) throws LmsException {
		if (StringUtils.isEmpty(uid)) {
			LOG.error("Book ID is empty");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "ID", "Book");
		}
		return service.getBookById(uid);
	}

	/**
	 * Search book by filter.
	 * 
	 * @param filter
	 *            Filter
	 * @return List of Books
	 */
	public List<BookModel> getBooksByFilter(BookModel filter) {
		return service.getBooksByFilter(filter);
	}

	/**
	 * Save a Book.
	 * 
	 * @param book
	 *            Book
	 * @return Saved Book
	 * @throws LmsException
	 *             If mandatory fields not found
	 */
	public BookModel saveBook(BookModel book) throws LmsException {
		if (StringUtils.isEmpty(book.getTitle())) {
			LOG.error("Title is empty");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "Title", "Book");
		}
		if (StringUtils.isEmpty(book.getAuthor())) {
			LOG.error("Author is empty");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "Author", "Book");
		}
		return service.saveBook(book);
	}

	/**
	 * Delete a book
	 * 
	 * @param uid
	 *            Book ID
	 * @throws LmsException
	 */
	public void deleteBook(String uid) throws LmsException {
		if (StringUtils.isEmpty(uid)) {
			LOG.error("Book ID is empty");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "ID", "Book");
		}
		service.deleteBook(uid);
	}

	/**
	 * Update a book.
	 * 
	 * @param book
	 *            Book
	 * @return Updated book
	 * @throws LmsException
	 *             If Book not found
	 */
	public BookModel updateBook(BookModel book) throws LmsException {
		if (StringUtils.isEmpty(book.getBookId())) {
			LOG.error("Book ID is empty");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "ID", "Book");
		}
		if (StringUtils.isEmpty(book.getTitle())) {
			LOG.error("Title is empty");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "Title", "Book");
		}
		if (StringUtils.isEmpty(book.getAuthor())) {
			LOG.error("Author is empty");
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.INPUT_PARAM_EMPTY, "Author", "Book");
		}
		return service.updateBook(book);
	}
}
