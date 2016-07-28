package eg.com.etisalat.contest.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
 * The persistent class for the IDEA database table.
 * 
 */
@Entity
@Table(name = "IDEA")
public class Idea extends eg.com.etisalat.base.entity.BaseEntity implements
		Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "IDEA_IDEAID_GENERATOR", sequenceName = "SEQ_IDEA_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IDEA_IDEAID_GENERATOR")
	@Column(name = "IDEA_ID")
	private long ideaId;

	@Column(name = "IDEA_DESCRIPTION")
	private String ideaDescription;

	@Column(name = "IDEA_NAME")
	private String ideaName;

	@Column(name = "LINK_1")
	private String link1;

	@Column(name = "LINK_2")
	private String link2;

	@Column(name = "LINK_3")
	private String link3;

	@Column(name = "LINK_4")
	private String link4;

	@Column(name = "LINK_5")
	private String link5;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUBMIT_DATE")
	private Date submitDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	// uni-directional many-to-one association to Category
	@ManyToOne()
	@JoinColumn(name = "CHALLENGE_ID")
	private Challenge challenge;

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	// uni-directional many-to-one association to IdeaBlog
	// @OneToOne(mappedBy = "idea", cascade = { CascadeType.ALL })
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(name = "BLOG_ID")
	private IdeaBlog ideaBlog;

	// uni-directional many-to-one association to IdeaStatus
	@ManyToOne
	@JoinColumn(name = "IDEA_STATUS_ID")
	private IdeaStatus ideaStatus;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	public Idea() {
		if (isNew()) {
			submitDate = new Date();
			updateDate = submitDate;
		}
	}

	public Challenge getChallenge() {
		return this.challenge;
	}

	public IdeaBlog getIdeaBlog() {
		return this.ideaBlog;
	}

	public String getIdeaDescription() {
		return this.ideaDescription;
	}

	public long getIdeaId() {
		return this.ideaId;
	}

	public String getIdeaName() {
		return this.ideaName;
	}

	public IdeaStatus getIdeaStatus() {
		return this.ideaStatus;
	}

	public Idea getIdenticalCopy() throws ClassNotFoundException, IOException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			Idea deepCopy = (Idea) ois.readObject();
			return deepCopy;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String getLink1() {
		return this.link1;
	}

	public String getLink2() {
		return this.link2;
	}

	public String getLink3() {
		return this.link3;
	}

	public String getLink4() {
		return link4;
	}

	public String getLink5() {
		return link5;
	}

	public Date getSubmitDate() {
		return this.submitDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((challenge == null) ? 0 : challenge.hashCode());
		result = prime * result
				+ ((ideaBlog == null) ? 0 : ideaBlog.hashCode());
		result = prime * result
				+ ((ideaDescription == null) ? 0 : ideaDescription.hashCode());
		result = prime * result + (int) (ideaId ^ (ideaId >>> 32));
		result = prime * result
				+ ((ideaName == null) ? 0 : ideaName.hashCode());
		result = prime * result
				+ ((ideaStatus == null) ? 0 : ideaStatus.hashCode());
		result = prime * result + ((link1 == null) ? 0 : link1.hashCode());
		result = prime * result + ((link2 == null) ? 0 : link2.hashCode());
		result = prime * result + ((link3 == null) ? 0 : link3.hashCode());
		result = prime * result + ((link4 == null) ? 0 : link4.hashCode());
		result = prime * result + ((link5 == null) ? 0 : link5.hashCode());
		result = prime * result
				+ ((submitDate == null) ? 0 : submitDate.hashCode());
		result = prime * result
				+ ((updateDate == null) ? 0 : updateDate.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean isNew() {
		return ideaId == 0;
	}

	public boolean isRejected() {
		return IdeaStatus.REJECTED.equals(ideaStatus);
	}

	public boolean isSameIdea(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Idea other = (Idea) obj;
		if (challenge == null) {
			if (other.challenge != null)
				return false;
		} else if (!challenge.equals(other.challenge))
			return false;
		if (ideaBlog == null) {
			if (other.ideaBlog != null)
				return false;
		} else if (ideaBlog.getBlogId() != (other.ideaBlog.getBlogId()))
			return false;
		if (ideaDescription == null) {
			if (other.ideaDescription != null)
				return false;
		} else if (!ideaDescription.equals(other.ideaDescription))
			return false;
		if (ideaId != other.ideaId)
			return false;
		if (ideaName == null) {
			if (other.ideaName != null)
				return false;
		} else if (!ideaName.equals(other.ideaName))
			return false;
		if (ideaStatus == null) {
			if (other.ideaStatus != null)
				return false;
		} else if (!ideaStatus.equals(other.ideaStatus))
			return false;
		if (link1 == null) {
			if (other.link1 != null)
				return false;
		} else if (!link1.equals(other.link1))
			return false;
		if (link2 == null) {
			if (other.link2 != null)
				return false;
		} else if (!link2.equals(other.link2))
			return false;
		if (link3 == null) {
			if (other.link3 != null)
				return false;
		} else if (!link3.equals(other.link3))
			return false;
		if (link4 == null) {
			if (other.link4 != null)
				return false;
		} else if (!link4.equals(other.link4))
			return false;
		if (link5 == null) {
			if (other.link5 != null)
				return false;
		} else if (!link5.equals(other.link5))
			return false;
		if (submitDate == null) {
			if (other.submitDate != null)
				return false;
		} else if (!submitDate.equals(other.submitDate))
			return false;
		if (updateDate == null) {
			if (other.updateDate != null)
				return false;
		} else if (!updateDate.equals(other.updateDate))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (user.getUserId() != other.user.getUserId())
			return false;
		return true;
	}

	public boolean isShortlisted() {
		return IdeaStatus.SHORT_LISTED.equals(ideaStatus);
	}

	public void setCategory(Challenge challenge) {
		this.challenge = challenge;
	}

	public void setIdeaBlog(IdeaBlog ideaBlog) {
		this.ideaBlog = ideaBlog;
	}

	public void setIdeaDescription(String ideaDescription) {
		this.ideaDescription = ideaDescription;
	}

	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}

	public void setIdeaName(String ideaName) {
		this.ideaName = ideaName;
	}

	public void setIdeaStatus(IdeaStatus ideaStatus) {
		this.ideaStatus = ideaStatus;
	}

	public void setLink1(String link1) {
		this.link1 = link1;
	}

	public void setLink2(String link2) {
		this.link2 = link2;
	}

	public void setLink3(String link3) {
		this.link3 = link3;
	}

	public void setLink4(String link4) {
		this.link4 = link4;
	}

	public void setLink5(String link5) {
		this.link5 = link5;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Idea [ideaId=" + ideaId + ", ideaDescription="
				+ ideaDescription + ", ideaName=" + ideaName + ", link1="
				+ link1 + ", link2=" + link2 + ", link3=" + link3 + ", link4="
				+ link4 + ", link5=" + link5 + ", submitDate=" + submitDate
				+ ", updateDate=" + updateDate + ", challenge=" + challenge
				+ ", ideaStatus=" + ideaStatus + ", user=" + user + "]";
	}

	public boolean isShotlisted() {
		return IdeaStatus.SHORT_LISTED.equals(ideaStatus);
	}

	public boolean isWinner() {
		return IdeaStatus.WINNER_1st.equals(ideaStatus)
				|| IdeaStatus.WINNER_2nd.equals(ideaStatus)
				|| IdeaStatus.WINNER_3rd.equals(ideaStatus);
	}

}