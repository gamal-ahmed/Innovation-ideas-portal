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
 * The persistent class for the IDEA_BLOG database table.
 * 
 */
@Entity
@Table(name = "IDEA_BLOG")
public class IdeaBlog extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "IDEA_BLOG_BLOGID_GENERATOR", sequenceName = "SEQ_BLOG_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IDEA_BLOG_BLOGID_GENERATOR")
	@Column(name = "BLOG_ID")
	private long blogId;

	@Column(name = "BLOG_TEXT")
	private String blogText;

	@Column(name = "BLOG_TITLE")
	private String blogTitle;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "NEGATIVE_RATING")
	private int negativeRating;

	@Column(name = "TOTAL_COMMENTS")
	private int totalComments;

	@Column(name = "POSITIVE_RATING")
	private int positiveRating;

	// bi-directional many-to-one association to BlogComment
	@OneToMany(mappedBy = "ideaBlog", cascade = { CascadeType.REMOVE, CascadeType.REFRESH })
	private List<BlogComment> blogComments;

	// uni-directional many-to-one association to Idea
	// @OneToOne(cascade = { CascadeType.REFRESH })
	@ManyToOne
	@JoinColumn(name = "IDEA_ID")
	private Idea idea;

	@OneToMany(mappedBy = "ideaBlog", cascade = { CascadeType.REMOVE, CascadeType.REFRESH })
	private List<Vote> votes;

	public IdeaBlog() {
		if (isNew()) {
			createdDate = new Date();
			lastUpdateDate = new Date();
			positiveRating = 0;
			negativeRating = 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdeaBlog other = (IdeaBlog) obj;
		if (blogId != other.blogId)
			return false;
		return true;
	}

	public List<BlogComment> getBlogComments() {
		return this.blogComments;
	}

	public long getBlogId() {
		return this.blogId;
	}

	public String getBlogText() {
		return this.blogText;
	}

	public String getBlogTitle() {
		return this.blogTitle;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public Idea getIdea() {
		return this.idea;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public int getNegativeRating() {
		return this.negativeRating;
	}

	public int getPositiveRating() {
		return this.positiveRating;
	}

	/**
	 * @return the totalComments
	 */
	public int getTotalComments() {
		return totalComments;
	}

	/**
	 * @return the votes
	 */
	public List<Vote> getVotes() {
		return votes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (blogId ^ (blogId >>> 32));
		return result;
	}

	@Override
	public boolean isNew() {
		return blogId == 0;
	}

	public void negativeRate() {
		this.negativeRating++;
	}

	public void positiveRate() {
		this.positiveRating++;
	}

	public void setBlogComments(List<BlogComment> blogComments) {
		this.blogComments = blogComments;
	}

	public void setBlogId(long blogId) {
		this.blogId = blogId;
	}

	public void setBlogText(String blogText) {
		this.blogText = blogText;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public void setNegativeRating(int negativeRating) {
		this.negativeRating = negativeRating;
	}

	public void setPositiveRating(int positiveRating) {
		this.positiveRating = positiveRating;
	}

	/**
	 * @param totalComments
	 *            the totalComments to set
	 */
	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}

	/**
	 * @param votes
	 *            the votes to set
	 */
	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "IdeaBlog [blogId=" + blogId + ", blogText=" + blogText + ", blogTitle=" + blogTitle + ", createdDate=" + createdDate + ", lastUpdateDate="
				+ lastUpdateDate + ", negativeRating=" + negativeRating + ", totalComments=" + totalComments + ", positiveRating=" + positiveRating + ", idea="
				+ idea + "]";
	}

}