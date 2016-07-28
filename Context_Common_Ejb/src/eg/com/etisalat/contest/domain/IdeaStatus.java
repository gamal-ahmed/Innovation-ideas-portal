package eg.com.etisalat.contest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the IDEA_STATUS database table.
 * 
 */
@Entity
@Table(name = "IDEA_STATUS")
public class IdeaStatus extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final IdeaStatus SUBMITTED = new IdeaStatus(1, "Submitted");
	public static final IdeaStatus REJECTED = new IdeaStatus(2, "Rejected");
	public static final IdeaStatus SHORT_LISTED = new IdeaStatus(3, "Shortlisted");
	public static final IdeaStatus WINNER_1st = new IdeaStatus(4, "Winner");
	public static final IdeaStatus WINNER_2nd = new IdeaStatus(5, "Business Enabler Award");
	public static final IdeaStatus WINNER_3rd = new IdeaStatus(6, "Customer Experience Award");

	public static List<IdeaStatus> getAllIdeaStatus() {
		List<IdeaStatus> allIdeaStatus = new ArrayList<IdeaStatus>();
		allIdeaStatus.add(SUBMITTED);
		allIdeaStatus.add(REJECTED);
		allIdeaStatus.add(SHORT_LISTED);
		allIdeaStatus.add(WINNER_1st);
	

		return allIdeaStatus;
	}

	@Id
	@Column(name = "IDEA_STATUS_ID")
	private int ideaStatusId;

	private String description;

	public IdeaStatus() {
	}

	public IdeaStatus(int ideaStatusId, String description) {
		super();
		this.ideaStatusId = ideaStatusId;
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
		IdeaStatus other = (IdeaStatus) obj;
		if (ideaStatusId != other.ideaStatusId)
			return false;
		return true;
	}

	public String getDescription() {
		return this.description;
	}

	@Override
	public String getEntityDescription() {
		return description;

	}

	public int getIdeaStatusId() {
		return this.ideaStatusId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ideaStatusId ^ (ideaStatusId >>> 32));
		return result;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIdeaStatusId(int ideaStatusId) {
		this.ideaStatusId = ideaStatusId;
	}

	@Override
	public String toString() {
		return "IdeaStatus [ideaStatusId=" + ideaStatusId + ", description=" + description + "]";
	}

}