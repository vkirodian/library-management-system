package com.rapps.utility.learning.lms.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * A Base repository extending {@link CrudRepository} where the ID is set as
 * String.
 * 
 * @author vkirodian
 *
 * @param <T>
 *            Type of bean for which the repository has been made.
 */
@NoRepositoryBean
public interface BaseRepository<T> extends CrudRepository<T, String> {

}
