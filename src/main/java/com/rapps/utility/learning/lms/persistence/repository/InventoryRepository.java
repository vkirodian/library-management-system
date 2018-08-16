package com.rapps.utility.learning.lms.persistence.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rapps.utility.learning.lms.persistence.bean.Inventory;

/**
 * Repository for Inventory.
 * 
 * @author vkirodian
 *
 */
public interface InventoryRepository extends BaseRepository<Inventory> {

	/**
	 * Update issued count
	 * 
	 * @param bookId
	 *            Book ID
	 * @param count
	 *            new count
	 */
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Inventory i set i.issued=:count WHERE i.bookId=:bookId")
	void updateIssuedCount(@Param("bookId") String bookId, @Param("count") int count);

	/**
	 * Updated requested count
	 * 
	 * @param bookId
	 *            Book ID
	 * @param count
	 *            new count
	 */
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Inventory i set i.requested=:count WHERE i.bookId=:bookId")
	void updateRequestedCount(@Param("bookId") String bookId, @Param("count") int count);

}
