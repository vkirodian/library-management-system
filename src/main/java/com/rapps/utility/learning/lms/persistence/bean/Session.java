package com.rapps.utility.learning.lms.persistence.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	@ManyToOne(targetEntity = User.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "USERID")
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", lastAccessTime=" + lastAccessTime + ", loggedInIpAddress="
				+ loggedInIpAddress + ", loggedInTime=" + loggedInTime + ", user=" + user + "]";
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
		} else if (!sessionId.equals(other.sessionId))
			return false;
		return true;
	}

}
