package com.rapps.utility.learning.lms.persistence.repository;

import java.util.List;

import com.rapps.utility.learning.lms.persistence.bean.Session;

/**
 * Session repository.
 * 
 * @author vkirodian
 *
 */
public interface SessionRepository extends BaseRepository<Session> {

	/**
	 * Get List of session with last access time less than the passed time.
	 * 
	 * @param lastAccessTime
	 *            Access time
	 * @return List of Session
	 */
	List<Session> findByLastAccessTimeLessThan(long lastAccessTime);
}
