package com.rapps.utility.learning.lms.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rapps.utility.learning.lms.persistence.bean.Book;

/**
 * Book repository.
 * 
 * @author vkirodian
 *
 */
public interface BookRepository extends BaseRepository<Book> {

	/**
	 * Get list of Books as per provided filter.
	 * 
	 * @param title
	 *            Title
	 * @param author
	 *            Author
	 * @return List of Books
	 */
	@Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE %:title% AND LOWER(b.author) LIKE %:author% AND LOWER(b.category) LIKE %:category% AND LOWER(b.language) LIKE %:language% AND LOWER(b.edition) LIKE %:edition%")
	List<Book> findBooksByFilter(@Param("title") String title, @Param("author") String author,
			@Param("category") String category, @Param("language") String language, @Param("edition") String edition);
}
