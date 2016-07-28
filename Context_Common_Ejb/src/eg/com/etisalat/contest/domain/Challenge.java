package eg.com.etisalat.contest.domain;

import java.io.Serializable;
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



/**
 * The persistent class for the CATEGORY database table.
 * 
 */
@Entity
@Table(name = "CHALLENGE")
public class Challenge extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CHALLENGE_ID_GENERATOR", sequenceName = "SEQ_Challenge_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHALLENGE_ID_GENERATOR")
	@Column(name = "CHALLENGE_ID")
	private long Id;

	@Column(name = "DESCRIPTION_EN")
	private String description_en;

	@Column(name = "DESCRIPTION_AR")
	private String description_ar;
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	@Column(name = "NAME")
	private String name;
	
	@ManyToOne(targetEntity=Contest.class)
	@JoinColumn(name = "CONTEST_ID")
	private Contest contest;

	

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public int getInternal() {
		return internal;
	}

	public void setInternal(int internal) {
		this.internal = internal;
	}

	@Column(name = "INTERNAL")
	private int internal;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Challenge() {
	}

	public Challenge(long challengeId, String description_en, String description_ar) {
		super();
		this.Id = challengeId;
		this.description_en = description_en;
		this.description_ar = description_ar;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Challenge other = (Challenge) obj;
		if (Id != other.Id )
			return false;
		if (description_ar == null) {
			if (other.description_ar != null)
				return false;
		} else if (!description_ar.equals(other.description_ar))
			return false;
		if (description_en == null) {
			if (other.description_en != null)
				return false;
		} else if (!description_en.equals(other.description_en))
			return false;
		return true;
	}



	public String getDescription_ar() {
		return description_ar;
	}

	public String getDescription_en() {
		return this.description_en;
	}

	public String getLocalizedDescription(String local) {
		if (local.equalsIgnoreCase("ar")) {
			return this.description_ar;
		} else
			return this.description_en;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (Id ^ (Id >>> 32));
		result = prime * result + ((description_ar == null) ? 0 : description_ar.hashCode());
		result = prime * result + ((description_en == null) ? 0 : description_en.hashCode());
		return result;
	}

	public void setChallengeId(long ChallengeId) {
		this.Id = ChallengeId;
	}

	public void setDescription_ar(String description_ar) {
		this.description_ar = description_ar;
	}

	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}

	@Override
	public String toString() {
		return "Category [ChallengeID=" + Id + ", description_en=" + description_en + ", description_ar=" + description_ar + "]";
	}

	

}