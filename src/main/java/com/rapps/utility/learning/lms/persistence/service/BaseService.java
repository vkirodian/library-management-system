package com.rapps.utility.learning.lms.persistence.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.rapps.utility.learning.lms.enums.MessagesEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.exception.LmsException.ErrorType;
import com.rapps.utility.learning.lms.persistence.repository.BaseRepository;

/**
 * A base service class that would provide some default implementation of
 * certain functionality.
 * 
 * @author vkirodian
 *
 * @param <T>
 *            Entity type of JPA repository required.
 */
public class BaseService<T> {

	@Autowired
	BaseRepository<T> repository;

	public T findById(String uid) throws LmsException {
		Optional<T> optional = repository.findById(uid);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.ENTITY_WITH_ID_NOT_FOUND, uid);
		}
	}

	/**
	 * Delete an item from database.
	 * 
	 * @param uid
	 *            ID of the item to be deleted.
	 * @throws LmsException
	 *             If item not found in database
	 */
	public void deleteById(String uid) throws LmsException {
		try {
			repository.deleteById(uid);
		} catch (EmptyResultDataAccessException e) {
			throw new LmsException(ErrorType.FAILURE, MessagesEnum.ENTITY_TO_DELETE_NOT_FOUND, uid);
		}
	}

}
