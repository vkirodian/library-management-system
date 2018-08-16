package com.rapps.utility.learning.lms.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.InventoryModel;
import com.rapps.utility.learning.lms.persistence.bean.Inventory;
import com.rapps.utility.learning.lms.persistence.repository.InventoryRepository;

/**
 * Service class for Inventory.
 * 
 * @author vkirodian
 *
 */
@Service
public class InventoryService extends BaseService<Inventory> {

	@Autowired
	InventoryRepository inventoryRepository;

	/**
	 * Find Book by ID.
	 * 
	 * @param bookId
	 *            Book ID
	 * @return Book
	 * @throws LmsException
	 *             If not found
	 */
	public InventoryModel getByBookId(String bookId) throws LmsException {
		return Converter.convertObject(super.findById(bookId), InventoryModel.class);
	}

	/**
	 * Update issued count for Book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @param count
	 *            new count
	 */
	@Transactional
	public void updateIssuedCount(String bookId, int count) {
		inventoryRepository.updateIssuedCount(bookId, count);
	}

	/**
	 * Update requested count for Book.
	 * 
	 * @param bookId
	 *            Book ID
	 * @param count
	 *            new count
	 */
	@Transactional
	public void updateRequestedCount(String bookId, int count) {
		inventoryRepository.updateRequestedCount(bookId, count);
	}
}
