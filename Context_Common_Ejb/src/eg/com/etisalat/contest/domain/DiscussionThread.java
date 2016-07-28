package eg.com.etisalat.contest.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DISCUSSION_THREAD database table.
 * 
 */
@Entity
@Table(name = "DISCUSSION_THREAD")
public class DiscussionThread extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DISCUSSION_THREAD_THREADID_GENERATOR", sequenceName = "SEQ_THREAD_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISCUSSION_THREAD_THREADID_GENERATOR")
	@Column(name = "THREAD_ID")
	private long threadId;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	// uni-directional many-to-one association to ThreadStatus
	@ManyToOne
	@JoinColumn(name = "THREAD_STATUS_ID")
	private ThreadStatus threadStatus;

	// uni-directional many-to-one association to User
	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "USER_ID")
	private User user;

	// bi-directional many-to-one association to ThreadMessage
	@OneToMany(mappedBy = "discussionThread", cascade = { CascadeType.ALL })
	private List<ThreadMessage> threadMessages;

	public DiscussionThread() {
		if (isNew()) {
			lastUpdateDate = new Date();
			threadStatus = ThreadStatus.CLOSED;
		}
	}

	/**
	 * @param user
	 */
	public DiscussionThread(User user) {
		this();
		this.user = user;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public long getThreadId() {
		return this.threadId;
	}

	public List<ThreadMessage> getThreadMessages() {
		return this.threadMessages;
	}

	public ThreadStatus getThreadStatus() {
		return this.threadStatus;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public boolean isNew() {
		return threadId == 0;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public void setThreadMessages(List<ThreadMessage> threadMessages) {
		this.threadMessages = threadMessages;
	}

	public void setThreadStatus(ThreadStatus threadStatus) {
		this.threadStatus = threadStatus;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "DiscussionThread [threadId=" + threadId + ", lastUpdateDate=" + lastUpdateDate + ", threadStatus=" + threadStatus + ", user=" + user + "]";
	}

}