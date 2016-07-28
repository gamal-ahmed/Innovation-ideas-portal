package eg.com.etisalat.contest.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the MESSAGE_TYPE database table.
 * 
 */
@Entity
@Table(name = "MESSAGE_TYPE")
public class MessageType extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MESSAGE_TYPE_ID")
	private long messageTypeId;

	private String description;

	public static final MessageType RESPONSE = new MessageType(1, "Response");

	public static final MessageType REQUEST = new MessageType(2, "Request");

	public MessageType() {
	}

	public MessageType(long messageTypeId, String description) {
		super();
		this.messageTypeId = messageTypeId;
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
		MessageType other = (MessageType) obj;
		if (messageTypeId != other.messageTypeId)
			return false;
		return true;
	}

	public String getDescription() {
		return this.description;
	}

	public long getMessageTypeId() {
		return this.messageTypeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (messageTypeId ^ (messageTypeId >>> 32));
		return result;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMessageTypeId(long messageTypeId) {
		this.messageTypeId = messageTypeId;
	}

	@Override
	public String toString() {
		return "MessageType [messageTypeId=" + messageTypeId + ", description=" + description + "]";
	}

}