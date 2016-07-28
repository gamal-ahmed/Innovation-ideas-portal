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
 * The persistent class for the BLOG_COMMENT database table.
 * 
 */
@Entity
@Table(name = "BLOG_COMMENT")
public class BlogComment extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "BLOG_COMMENT_COMMENTID_GENERATOR", sequenceName = "SEQ_COMMENT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BLOG_COMMENT_COMMENTID_GENERATOR")
	@Column(name = "COMMENT_ID")
	private long commentId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMMENT_DATE")
	private Date commentDate;

	@Column(name = "COMMENT_TEXT")
	private String commentText;

	@Column(name = "COMMENT_TITLE")
	private String commentTitle;

	// uni-directional many-to-one association to CommentStatus
	@ManyToOne
	@JoinColumn(name = "COMMENT_STATUS_ID")
	private CommentStatus commentStatus;

	// bi-directional many-to-one association to IdeaBlog
	@ManyToOne
	@JoinColumn(name = "BLOG_ID")
	private IdeaBlog ideaBlog;

	// bi-directional many-to-one association to IdeaBlog
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	public BlogComment() {
		if (isNew()) {
			commentStatus = CommentStatus.NEW;
			commentDate = new Date();
		}
	}

	public BlogComment(User user) {
		this.user = user;
	}

	public BlogComment(User user, IdeaBlog ideaBlog) {
		this();
		this.ideaBlog = ideaBlog;
		this.user = user;
	}

	public Date getCommentDate() {
		return this.commentDate;
	}

	public long getCommentId() {
		return this.commentId;
	}

	public CommentStatus getCommentStatus() {
		return this.commentStatus;
	}

	public String getCommentText() {
		return this.commentText;
	}

	public String getCommentTitle() {
		return this.commentTitle;
	}

	public IdeaBlog getIdeaBlog() {
		return this.ideaBlog;
	}

	public User getUser() {
		return user;
	}

	@Override
	public boolean isNew() {
		return commentId == 0;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public void setCommentStatus(CommentStatus commentStatus) {
		this.commentStatus = commentStatus;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public void setCommentTitle(String commentTitle) {
		this.commentTitle = commentTitle;
	}

	public void setIdeaBlog(IdeaBlog ideaBlog) {
		this.ideaBlog = ideaBlog;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "BlogComment [commentId=" + commentId + ", commentDate=" + commentDate + ", commentText=" + commentText + ", commentTitle=" + commentTitle
				+ ", commentStatus=" + commentStatus + ", user=" + user + "]";
	}

}