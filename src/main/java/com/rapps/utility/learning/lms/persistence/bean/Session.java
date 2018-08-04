package com.rapps.utility.learning.lms.persistence.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author vkirodia
 *
 */
@Entity
@Table(name = "session")
public class Session {

	@Id
	@Column(name = "SESSIONID")
	private String sessionId;

	@Column(name = "LASTACCESSTIME")
	private long lastAccessTime;

	@Column(name = "LOGGEDINIPADDRESS")
	private String loggedInIpAddress;

	@Column(name = "LOGGEDINTIME")
	private long loggedInTime;

	@Column(name = "USERID")
	private String userId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public String getLoggedInIpAddress() {
		return loggedInIpAddress;
	}

	public void setLoggedInIpAddress(String loggedInIpAddress) {
		this.loggedInIpAddress = loggedInIpAddress;
	}

	public long getLoggedInTime() {
		return loggedInTime;
	}

	public void setLoggedInTime(long loggedInTime) {
		this.loggedInTime = loggedInTime;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId)) {
			return false;
		}
		return true;
	}

}
