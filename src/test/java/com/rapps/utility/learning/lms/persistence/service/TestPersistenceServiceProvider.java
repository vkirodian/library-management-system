package com.rapps.utility.learning.lms.persistence.service;

import static org.mockito.Mockito.when;

import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TestPersistenceServiceProvider extends TestCase {

	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_USERNAME = "user";
	private static final String PROPERTY_PASS = "password";
	private static final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_DIALECT = "hibernate.dialect";

	@InjectMocks
	PersistenceServiceProvider provider;

	@Mock
	Environment environment;

	@Test
	public void testEntityManagerFactory() {
		when(environment.getProperty(PROPERTY_URL)).thenReturn("jdbc:mysql://127.0.0.1:3306/lms");
		when(environment.getProperty(PROPERTY_USERNAME)).thenReturn("root");
		when(environment.getProperty(PROPERTY_PASS)).thenReturn("admin");
		when(environment.getProperty(PROPERTY_DRIVER)).thenReturn("com.mysql.jdbc.Driver");
		when(environment.getProperty(PROPERTY_DIALECT)).thenReturn("org.hibernate.dialect.MySQL5Dialect");
		when(environment.getProperty(PROPERTY_SHOW_SQL)).thenReturn("false");
		LocalContainerEntityManagerFactoryBean emf = provider.entityManagerFactory();
		assertNotNull("", emf);
	}

	@Test
	public void testDataSource() {
		when(environment.getProperty(PROPERTY_URL)).thenReturn("jdbc:mysql://127.0.0.1:3306/lms");
		when(environment.getProperty(PROPERTY_USERNAME)).thenReturn("root");
		when(environment.getProperty(PROPERTY_PASS)).thenReturn("admin");
		when(environment.getProperty(PROPERTY_DRIVER)).thenReturn("com.mysql.jdbc.Driver");
		DataSource ds = provider.dataSource();
		assertNotNull("", ds);
	}

	@Test
	public void testHibernateProps() {
		when(environment.getProperty(PROPERTY_DIALECT)).thenReturn("org.hibernate.dialect.MySQL5Dialect");
		when(environment.getProperty(PROPERTY_SHOW_SQL)).thenReturn("false");
		Properties p = provider.hibernateProps();
		assertEquals("", p.getProperty(PROPERTY_DIALECT), "org.hibernate.dialect.MySQL5Dialect");
		assertEquals("", p.getProperty(PROPERTY_SHOW_SQL), "false");
	}

	@Test
	public void testTransactionManager() {
		when(environment.getProperty(PROPERTY_URL)).thenReturn("jdbc:mysql://127.0.0.1:3306/lms");
		when(environment.getProperty(PROPERTY_USERNAME)).thenReturn("root");
		when(environment.getProperty(PROPERTY_PASS)).thenReturn("admin");
		when(environment.getProperty(PROPERTY_DRIVER)).thenReturn("com.mysql.jdbc.Driver");
		when(environment.getProperty(PROPERTY_DIALECT)).thenReturn("org.hibernate.dialect.MySQL5Dialect");
		when(environment.getProperty(PROPERTY_SHOW_SQL)).thenReturn("false");
		JpaTransactionManager tm = provider.transactionManager();
		assertNotNull("", tm);
	}

}
