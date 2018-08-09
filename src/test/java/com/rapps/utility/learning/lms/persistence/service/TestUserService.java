package com.rapps.utility.learning.lms.persistence.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rapps.utility.learning.lms.enums.UserRoleEnum;
import com.rapps.utility.learning.lms.exception.LmsException;
import com.rapps.utility.learning.lms.model.UserModel;
import com.rapps.utility.learning.lms.persistence.bean.User;
import com.rapps.utility.learning.lms.persistence.repository.UserRepository;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestUserService extends TestCase {

	@InjectMocks
	UserService service;

	@Mock
	UserRepository userRepository;

	@Test(expected = LmsException.class)
	public void testGetUserByLoginId_Exception() throws LmsException {
		when(userRepository.findByLoginId("admin")).thenReturn(null);
		service.getUserByLoginId("admin");
	}

	@Test
	public void testGetUserByLoginId_Success() throws LmsException {
		User u = new User();
		u.setUserId("u1");
		when(userRepository.findByLoginId("admin")).thenReturn(u);
		UserModel acc = service.getUserByLoginId("admin");
		assertEquals("", u.getUserId(), acc.getUserId());
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
		u.setUserId("u1");
		u.setLoginId("admin");
		Optional<User> o = Optional.ofNullable(u);
		when(userRepository.findById("u1")).thenReturn(o);
		UserModel acc = service.getUserById("u1");
		assertEquals("", u.getLoginId(), acc.getLoginId());
	}

	@Test
	public void testSaveUser() {
		UserModel m = new UserModel();
		m.setLoginId("l1");
		m.setUserId("u1");

		User u = new User();
		u.setUserId("u1");
		u.setLoginId("l1");

		when(userRepository.save(any(User.class))).thenReturn(u);
		UserModel acc = service.saveUser(m);
		assertEquals("", "l1", acc.getLoginId());
		assertEquals("", "u1", acc.getUserId());
		assertNotNull("", acc.getUserId());
	}

	@Test
	public void testUpdateUser() throws LmsException {
		when(userRepository.save(any(User.class))).thenReturn(new User());
		service.updateUser(new UserModel());
	}

	@Test
	public void testDeleteUser() throws LmsException {
		service.deleteUser("u1");
	}

	@Test
	public void testGetUsers_FilterEmpty() {
		UserModel input = new UserModel();
		User u = new User();
		u.setLoginId("admin");
		List<User> output = new ArrayList<>();
		output.add(u);
		when(userRepository.findUsersByFilter("%", "%", UserRoleEnum.getAllRoles())).thenReturn(output);
		List<UserModel> ac = service.getUsers(input);

		assertEquals("", "admin", ac.get(0).getLoginId());
	}

	@Test
	public void testGetUsers_WithFilter() {
		UserModel input = new UserModel();
		input.setLoginId("admin");
		input.setEmailId("admin@lms.com");
		input.setUserRole(UserRoleEnum.SUPER_ADMIN);
		User u = new User();
		u.setLoginId("admin");
		;
		List<User> output = new ArrayList<>();
		output.add(u);
		when(userRepository.findUsersByFilter("admin", "admin@lms.com", Arrays.asList(UserRoleEnum.SUPER_ADMIN)))
				.thenReturn(output);
		List<UserModel> acc = service.getUsers(input);

		assertEquals("", "admin", acc.get(0).getLoginId());
	}
}
