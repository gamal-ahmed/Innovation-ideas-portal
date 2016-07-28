package eg.com.etisalat.contest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "CONTEST_TYPE")
public class ContestType extends eg.com.etisalat.base.entity.BaseEntity
		implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "ID")
	private long contestTypeId;
	public long getContestTypeId() {
		return contestTypeId;
	}
	public void setContestTypeId(long contestTypeId) {
		this.contestTypeId = contestTypeId;
	}


	@Column(name = "TYPE_NAME")
	private String description;

	public static final ContestType EXTERNAL = new ContestType(1, "EXTERNAL");

	public static final ContestType INTERNAL = new ContestType(2, "INTERNAL");
	public static final ContestType ALL = new ContestType(3, "ALL");

	public static List<ContestType> getAllContestTypes() {
		List<ContestType> allContestTypes = new ArrayList<ContestType>();
		allContestTypes.add(EXTERNAL);
		allContestTypes.add(INTERNAL);
	
	

		return allContestTypes;
	}
	public ContestType() {
	}

	/**
	 * @param ContestTypeId
	 * @param description
	 */
	public ContestType(long contestTypeId, String description) {
		super();
		this.contestTypeId = contestTypeId;
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
		ContestType other = (ContestType) obj;
		if (contestTypeId != other.contestTypeId)
			return false;
		return true;
	}

	public String getDescription() {
		return this.description;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (contestTypeId ^ (contestTypeId >>> 32));
		return result;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		return "ContestType [description=" + description + "]";
	}

}
