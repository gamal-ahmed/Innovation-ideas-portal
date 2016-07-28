package eg.com.etisalat.contest.domain;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CONTENT")
public class Content extends eg.com.etisalat.base.entity.BaseEntity implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "Content_ID_GENERATOR", sequenceName = "SEQ_Content_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Content_ID_GENERATOR")
	@Column(name = "ID")
	private long ContentId;

	public long getContentId() {
		return ContentId;
	}

	public void setContentId(long ContentId) {
		this.ContentId = ContentId;
	}

	@Column(name = "AR")
	private String description_AR;

	@Column(name = "EN")
	private String description_EN;

	@Column(name = "PAGE")
	private String page;

	@Column(name = "MESSAGE_ID")
	private String message_id;

	

	public String getDescription_AR() {
		return description_AR;
	}

	public void setDescription_AR(String description_AR) {
		this.description_AR = description_AR;
	}

	public String getDescription_EN() {
		return description_EN;
	}

	public void setDescription_EN(String description_EN) {
		this.description_EN = description_EN;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public boolean isSameContent(Object obj) {

		if (getClass() != obj.getClass())
			return false;
		if (obj == null)
			return false;
		if (this == obj)
			return true;

		Content other = (Content) obj;

		if (description_EN == null) {
			if (other.description_EN != null)
				return false;
		} else if (description_AR == null) {
			if (other.description_AR != null)
				return false;
		}
		if (ContentId != other.ContentId)
			return false;
		if (message_id == null) {
			if (other.message_id != null)
				return false;
		} else if (!message_id.equals(other.message_id))
			return false;
		if (message_id == null) {
			if (other.message_id != null)
				return false;
		} else if (message_id == other.message_id)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ContentId ^ (ContentId >>> 32));
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
		Content other = (Content) obj;
		if (ContentId != other.ContentId)
			return false;
		return true;
	}
	@Override
	public boolean isNew() {
		return ContentId == 0;
	}
}
