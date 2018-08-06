package com.rapps.utility.learning.lms.persistence.bean;

import org.junit.Test;

import com.rapps.utility.learning.lms.enums.UserRoleEnum;

import junit.framework.TestCase;

public class TestAllBeans extends TestCase {

	@Test
	public void testUserBean() {
		User u = new User();
		u.setEmailId("lms@lms.com");
		u.setLoginId("admin");
		u.setPassword("Admin@123");
		u.setPasswordExpiryTms(123456L);
		u.setUserId("u1");
		u.setUserRole(UserRoleEnum.SUPER_ADMIN);

		assertEquals("User bean failed", "lms@lms.com", u.getEmailId());
		assertEquals("User bean failed", "admin", u.getLoginId());
		assertEquals("User bean failed", "Admin@123", u.getPassword());
		assertEquals("User bean failed", 123456L, u.getPasswordExpiryTms());
		assertEquals("User bean failed", "u1", u.getUserId());
		assertEquals("User bean failed", UserRoleEnum.SUPER_ADMIN, u.getUserRole());
	}

	@Test
	public void testBookBean() {
		Book b = new Book();
		b.setAuthor("author");
		b.setBookId("b1");
		b.setCategory("category");
		b.setDescription("desc");
		b.setEdition("edition");
		b.setLanguage("english");
		b.setTitle("book of eli");

		assertEquals("Book bean failed", "author", b.getAuthor());
		assertEquals("Book bean failed", "b1", b.getBookId());
		assertEquals("Book bean failed", "category", b.getCategory());
		assertEquals("Book bean failed", "desc", b.getDescription());
		assertEquals("Book bean failed", "edition", b.getEdition());
		assertEquals("Book bean failed", "english", b.getLanguage());
		assertEquals("Book bean failed", "book of eli", b.getTitle());
	}

}
