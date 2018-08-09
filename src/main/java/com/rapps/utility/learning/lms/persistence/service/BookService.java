package com.rapps.utility.learning.lms.persistence.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.BookModel;
import com.rapps.utility.learning.lms.persistence.bean.Book;
import com.rapps.utility.learning.lms.persistence.repository.BookRepository;

/**
 * Book service.
 * 
 * @author vkirodia
 *
 */
@Service
public class BookService extends BaseService<Book> {

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
	public BookModel getBookById(String uid) throws LmsException {
		return Converter.convertObject(super.findById(uid), BookModel.class);
	}

	/**
	 * Get list of all books.
	 * 
	 * @return List of all books
	 */
	public List<BookModel> getAllBooks() {
		return Converter.convertList(bookRepository.findAll(), BookModel.class);
	}

	/**
	 * Persists the given book.
	 * 
	 * @param book
	 *            Book to be persisted
	 * @return persisted book
	 * @throws LmsException
	 */
	@Transactional
	public BookModel saveBook(BookModel book) {
		book.setBookId(UUID.randomUUID().toString());
		return Converter.convertObject(bookRepository.save(Converter.convertObject(book, Book.class)), BookModel.class);
	}

	/**
	 * Get list of Books for given filter.
	 * 
	 * @param filter
	 *            Book Filter
	 * @return List of Books
	 */
	public List<BookModel> getBooksByFilter(BookModel filter) {
		String title = filter.getTitle() == null ? "%" : filter.getTitle().toLowerCase();
		String author = filter.getAuthor() == null ? "%" : filter.getAuthor().toLowerCase();
		String category = filter.getCategory() == null ? "%" : filter.getCategory().toLowerCase();
		String language = filter.getLanguage() == null ? "%" : filter.getLanguage().toLowerCase();
		String edition = filter.getEdition() == null ? "%" : filter.getEdition().toLowerCase();
		return Converter.convertList(bookRepository.findBooksByFilter(title, author, category, language, edition),
				BookModel.class);
	}

	/**
	 * Delete Book.
	 * 
	 * @param uid
	 *            Book Id
	 * @throws LmsException
	 *             Book not found
	 */
	@Transactional
	public void deleteBook(String uid) throws LmsException {
		super.deleteById(uid);
	}

	/**
	 * Update Book.
	 * 
	 * @param book
	 *            Book
	 * @return Updated Book
	 * @throws LmsException
	 */
	@Transactional
	public BookModel updateBook(BookModel book) throws LmsException {
		getBookById(book.getBookId());
		return Converter.convertObject(bookRepository.save(Converter.convertObject(book, Book.class)), BookModel.class);
	}
}
