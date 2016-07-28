package eg.com.etisalat.contest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the COMMENT_STATUS database table.
 * 
 */
@Entity
@Table(name = "COMMENT_STATUS")
public class CommentStatus extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final CommentStatus NEW = new CommentStatus(1, "New");

	public static final CommentStatus REJECTED = new CommentStatus(2, "Rejected");

	public static List<CommentStatus> getAllCommentsStatus() {
		List<CommentStatus> allCommentsStatus = new ArrayList<CommentStatus>();
		allCommentsStatus.add(NEW);
		allCommentsStatus.add(REJECTED);
		return allCommentsStatus;
	}

	@Id
	@Column(name = "COMMENT_STATUS_ID")
	private long commentStatusId;

	private String description;

	public CommentStatus() {
	}

	public CommentStatus(long commentStatusId, String description) {
		super();
		this.commentStatusId = commentStatusId;
		this.description = description;
	}

	public long getCommentStatusId() {
		return this.commentStatusId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setCommentStatusId(long commentStatusId) {
		this.commentStatusId = commentStatusId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "CommentStatus [commentStatusId=" + commentStatusId + ", description=" + description + "]";
	}

}