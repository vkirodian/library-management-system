package com.rapps.utility.learning.lms.persistence.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.persistence.bean.User;
import com.rapps.utility.learning.lms.persistence.repository.UserRepository;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestUserService extends TestCase {

	@InjectMocks
	UserService service;

	@Mock
	UserRepository userRepository;

	@Test
	public void testGetUsers() {
		service.getUsers();
	}

	@Test(expected = LmsException.class)
	public void testGetUserByLoginId_Exception() throws LmsException {
		when(userRepository.findByLoginId("admin")).thenReturn(null);
		service.getUserByLoginId("admin");
	}

	@Test
	public void testGetUserByLoginId_Success() throws LmsException {
		User u = new User();
		when(userRepository.findByLoginId("admin")).thenReturn(u);
		User acc = service.getUserByLoginId("admin");
		assertEquals("", u, acc);
	}

	@Test(expected = LmsException.class)
	public void testGetUserById_Exception() throws LmsException {
		Optional<User> o = Optional.ofNullable(null);
		when(userRepository.findById("u1")).thenReturn(o);
		service.getUserById("u1");
	}

	@Test
	public void testGetUserById_Success() throws LmsException {
		User u = new User();
		Optional<User> o = Optional.ofNullable(u);
		when(userRepository.findById("u1")).thenReturn(o);
		User acc = service.getUserById("u1");
		assertEquals("", u, acc);
	}

	@Test
	public void testSaveUser() {
		service.saveUser(new User());
	}
}
