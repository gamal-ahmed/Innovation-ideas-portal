package eg.com.etisalat.contest.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Entity
@Table(name = "CONTEST")
public class Contest extends eg.com.etisalat.base.entity.BaseEntity implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONTEST_ID_GENERATOR", sequenceName = "SEQ_CONTEST_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTEST_ID_GENERATOR")
	@Column(name = "ID")
	private long contestId;

	public long getContestId() {
		return contestId;
	}

	public void setContestId(long contestId) {
		this.contestId = contestId;
	}

	@Column(name = "DESCRIPTION_AR")
	private String description_AR;

	@Column(name = "DESCRIPTION_EN")
	private String description_EN;

	@Column(name = "NAME")
	private String name;

	@Column(name = "IMAGE")
	private String image;

	@Column(name = "PLACEHOLDER_AR")
	private String placeHolder_AR;
	@Column(name = "PLACEHOLDER_EN")
	private String placeHolder_EN;

	@Column(name = "TARGETAUDIENCE_AR")
	private String targetAudience_AR;
	@OneToMany( cascade = { CascadeType.REMOVE, CascadeType.REFRESH },fetch=FetchType.EAGER,mappedBy ="contest")
	private List<Challenge> challenges;
	public List<Challenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}

	public String getPlaceHolder_AR() {
		return placeHolder_AR;
	}

	public void setPlaceHolder_AR(String placeHolder_AR) {
		this.placeHolder_AR = placeHolder_AR;
	}

	public String getPlaceHolder_EN() {
		return placeHolder_EN;
	}

	public void setPlaceHolder_EN(String placeHolder_EN) {
		this.placeHolder_EN = placeHolder_EN;
	}

	public String getTargetAudience_AR() {
		return targetAudience_AR;
	}

	public void setTargetAudience_AR(String targetAudience_AR) {
		this.targetAudience_AR = targetAudience_AR;
	}

	public String getTargetAudience_EN() {
		return targetAudience_EN;
	}

	public void setTargetAudience_EN(String targetAudience_EN) {
		this.targetAudience_EN = targetAudience_EN;
	}

	public String getAwards_AR() {
		return awards_AR;
	}

	public void setAwards_AR(String awards_AR) {
		this.awards_AR = awards_AR;
	}

	public String getAwards_EN() {
		return awards_EN;
	}

	public void setAwards_EN(String awards_EN) {
		this.awards_EN = awards_EN;
	}

	public String getEvaluationCriteria_AR() {
		return evaluationCriteria_AR;
	}

	public void setEvaluationCriteria_AR(String evaluationCriteria_AR) {
		this.evaluationCriteria_AR = evaluationCriteria_AR;
	}

	public String getEvaluationCriteria_EN() {
		return evaluationCriteria_EN;
	}

	public void setEvaluationCriteria_EN(String evaluationCriteria_EN) {
		this.evaluationCriteria_EN = evaluationCriteria_EN;
	}

	@Column(name = "TARGETAUDIENCE_EN")
	private String targetAudience_EN;

	@Column(name = "AWARDS_AR")
	private String awards_AR;
	@Column(name = "AWARDS_EN")
	private String awards_EN;

	@Column(name = "EVALUATIONCRITERIA_AR")
	private String evaluationCriteria_AR;
	@Column(name = "EVALUATIONCRITERIA_EN")
	private String evaluationCriteria_EN;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUBMITE_DATE")
	private Date submitDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	@ManyToOne
	@JoinColumn(name = "CONTEST_TYPE_ID")
	private ContestType contestTypeID;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public ContestType getContestTypeID() {
		return contestTypeID;
	}

	public void setContestTypeID(ContestType contestTypeID) {
		this.contestTypeID = contestTypeID;
	}

	public boolean isSameContest(Object obj) {

		if (getClass() != obj.getClass())
			return false;
		if (obj == null)
			return false;
		if (this == obj)
			return true;

		Contest other = (Contest) obj;

		if (description_EN == null) {
			if (other.description_EN != null)
				return false;
		} else if (description_AR == null) {
			if (other.description_AR != null)
				return false;
		}
		if (contestId != other.contestId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (contestTypeID == null) {
			if (other.contestTypeID != null)
				return false;
		} else if (contestTypeID == other.contestTypeID)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (contestId ^ (contestId >>> 32));
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
		Contest other = (Contest) obj;
		if (contestId != other.contestId)
			return false;
		return true;
	}
	@Override
	public boolean isNew() {
		return contestId == 0;
	}
	public String getLocalizedDescription(String local) {
		if (local.equalsIgnoreCase("ar")) {
			return this.description_AR;
		} else
			return this.description_EN;
	}
	public String getLocalizedTargetAudience(String local) {
		if (local.equalsIgnoreCase("ar")) {
			return this.targetAudience_AR;
		} else
			return this.targetAudience_EN;
	}
	public String getLocalizedAwards(String local) {
		if (local.equalsIgnoreCase("ar")) {
			return this.awards_AR;
		} else
			return this.awards_EN;
	}
	public String getLocalizEdevaluationCriteria(String local) {
		if (local.equalsIgnoreCase("ar")) {
			return this.evaluationCriteria_AR;
		} else
			return this.evaluationCriteria_EN;
	}
	public String getLocalizedPlaceHolder(String local) {
		if (local.equalsIgnoreCase("ar")) {
			return this.placeHolder_AR;
		} else
			return this.placeHolder_EN;
	}
}
