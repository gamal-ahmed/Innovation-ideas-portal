package eg.com.etisalat.contest.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the THREAD_MESSAGE database table.
 * 
 */
@Entity
@Table(name = "THREAD_MESSAGE")
public class ThreadMessage extends eg.com.etisalat.base.entity.BaseEntity implements Serializable, Comparable<ThreadMessage> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "THREAD_MESSAGE_MESSAGEID_GENERATOR", sequenceName = "SEQ_MESSAGE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "THREAD_MESSAGE_MESSAGEID_GENERATOR")
	@Column(name = "MESSAGE_ID")
	private long messageId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MESSAGE_DATE")
	private Date messageDate;

	@Column(name = "MESSAGE_TEXT")
	private String messageText;

	// bi-directional many-to-one association to DiscussionThread
	@ManyToOne
	@JoinColumn(name = "THREAD_ID")
	private DiscussionThread discussionThread;

	// uni-directional many-to-one association to MessageType
	@ManyToOne
	@JoinColumn(name = "MESSAGE_TYPE_ID")
	private MessageType messageType;

	public ThreadMessage() {
	}

	@Override
	public int compareTo(ThreadMessage o) {

		return this.messageDate.compareTo(o.getMessageDate());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ThreadMessage other = (ThreadMessage) obj;
		if (messageId != other.messageId)
			return false;
		return true;
	}

	public DiscussionThread getDiscussionThread() {
		return this.discussionThread;
	}

	public Date getMessageDate() {
		return this.messageDate;
	}

	public long getMessageId() {
		return this.messageId;
	}

	public String getMessageText() {
		return this.messageText;
	}

	public MessageType getMessageType() {
		return this.messageType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (messageId ^ (messageId >>> 32));
		return result;
	}

	public void setDiscussionThread(DiscussionThread discussionThread) {
		this.discussionThread = discussionThread;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	@Override
	public String toString() {
		return "ThreadMessage [messageId=" + messageId + ", messageDate=" + messageDate + ", messageText=" + messageText + ", messageType=" + messageType + "]";
	}

}