package eg.com.etisalat.contest.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the THREAD_STATUS database table.
 * 
 */
@Entity
@Table(name = "THREAD_STATUS")
public class ThreadStatus extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "THREAD_STATUS_ID")
	private long threadStatusId;

	public static final ThreadStatus CLOSED = new ThreadStatus(2, "Closed");

	public static final ThreadStatus WAIT_FOR_RESPONSE = new ThreadStatus(1, "Waiting For Response");
	private String description;

	public ThreadStatus() {
	}

	/**
	 * @param threadStatusId
	 * @param description
	 */
	public ThreadStatus(long threadStatusId, String description) {
		super();
		this.threadStatusId = threadStatusId;
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ThreadStatus other = (ThreadStatus) obj;
		if (threadStatusId != other.threadStatusId)
			return false;
		return true;
	}

	public String getDescription() {
		return this.description;
	}

	public long getThreadStatusId() {
		return this.threadStatusId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (threadStatusId ^ (threadStatusId >>> 32));
		return result;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setThreadStatusId(long threadStatusId) {
		this.threadStatusId = threadStatusId;
	}

	@Override
	public String toString() {
		return "ThreadStatus [threadStatusId=" + threadStatusId + ", description=" + description + "]";
	}

}