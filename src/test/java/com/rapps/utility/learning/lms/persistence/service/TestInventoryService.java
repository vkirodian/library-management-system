package com.rapps.utility.learning.lms.persistence.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.persistence.bean.Inventory;
import com.rapps.utility.learning.lms.persistence.repository.BaseRepository;
import com.rapps.utility.learning.lms.persistence.repository.InventoryRepository;

@RunWith(MockitoJUnitRunner.class)
public class TestInventoryService {

	@InjectMocks
	InventoryService service;

	@Mock
	InventoryRepository repo;
	@Mock
	BaseRepository<Inventory> repository;

	@Test
	public void testGetByBookId() throws LmsException {
		Inventory i = new Inventory();
		i.setBookId("b1");
		Optional<Inventory> o = Optional.ofNullable(i);
		when(repository.findById("b1")).thenReturn(o);
		service.getByBookId("b1");
	}

	@Test
	public void testUpdateIssuedCount() {
		service.updateIssuedCount("", 1);
	}

	@Test
	public void testUpdateRequestedCount() {
		service.updateRequestedCount("", 1);
	}
}
