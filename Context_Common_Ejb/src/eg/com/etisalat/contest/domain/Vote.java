package eg.com.etisalat.contest.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the VOTE database table.
 * 
 */
@Entity
public class Vote extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "VOTE_VOTEID_GENERATOR", sequenceName = "SEQ_VOTE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VOTE_VOTEID_GENERATOR")
	@Column(name = "VOTE_ID")
	private long voteId;

	@Column(name = "VOTE_VALUE")
	private int voteValue;

	// uni-directional many-to-one association to IdeaBlog
	@ManyToOne()
	@JoinColumn(name = "BLOG_ID")
	private IdeaBlog ideaBlog;

	// uni-directional many-to-one association to User
	@ManyToOne()
	@JoinColumn(name = "USER_ID")
	private User user;

	public Vote() {
	}

	public Vote(User user, IdeaBlog blog) {
		this();
		this.user = user;
		this.ideaBlog = blog;
	}

	public IdeaBlog getIdeaBlog() {
		return this.ideaBlog;
	}

	public User getUser() {
		return this.user;
	}

	public long getVoteId() {
		return this.voteId;
	}

	public int getVoteValue() {
		return this.voteValue;
	}

	public void setIdeaBlog(IdeaBlog ideaBlog) {
		this.ideaBlog = ideaBlog;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setVoteId(long voteId) {
		this.voteId = voteId;
	}

	public void setVoteValue(int voteValue) {
		this.voteValue = voteValue;
	}

	@Override
	public String toString() {
		return "Vote [voteId=" + voteId + ", voteValue=" + voteValue + ", ideaBlog=" + ideaBlog + ", user=" + user + "]";
	}

}