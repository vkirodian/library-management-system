package com.rapps.utility.learning.lms.persistence.service;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * A utility class providing persistence layer service.
 * 
 * @author vkirodia
 *
 */
@Configuration
@EnableCaching
@EnableTransactionManagement
@EnableJpaRepositories("com.rapps.utility.learning.lms.persistence.repository")
public class PersistenceServiceProvider {

	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_USERNAME = "dbuser";
	private static final String PROPERTY_PASS = "dbpassword";
	private static final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_DIALECT = "hibernate.dialect";

	@Autowired
	Environment environment;

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lfb = new LocalContainerEntityManagerFactoryBean();
		lfb.setDataSource(dataSource());
		lfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lfb.setPackagesToScan("com.rapps.utility.learning.lms.persistence.bean");
		lfb.setJpaProperties(hibernateProps());
		return lfb;
	}

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl(environment.getProperty(PROPERTY_URL));
		ds.setUsername(environment.getProperty(PROPERTY_USERNAME));
		ds.setPassword(environment.getProperty(PROPERTY_PASS));
		ds.setDriverClassName(environment.getProperty(PROPERTY_DRIVER));
		return ds;
	}

	Properties hibernateProps() {
		Properties properties = new Properties();
		properties.setProperty(PROPERTY_DIALECT, environment.getProperty(PROPERTY_DIALECT));
		properties.setProperty(PROPERTY_SHOW_SQL, environment.getProperty(PROPERTY_SHOW_SQL));
		return properties;
	}

	@Bean
	JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
}
