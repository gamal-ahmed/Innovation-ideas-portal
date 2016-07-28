package eg.com.etisalat.contest.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the USER_TYPE database table.
 * 
 */
@Entity
@Table(name = "USER_TYPE")
public class UserType extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_TYPE_ID")
	private long userTypeId;

	private String description;

	public static final UserType CONTENDER = new UserType(1, "Contender");

	public static final UserType VOTER = new UserType(2, "Voter");
	public static final UserType JUDGE = new UserType(3, "Judge");
	public static final UserType ADMIN = new UserType(4, "Admin");

	public UserType() {
	}

	/**
	 * @param userTypeId
	 * @param description
	 */
	public UserType(long userTypeId, String description) {
		super();
		this.userTypeId = userTypeId;
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
		UserType other = (UserType) obj;
		if (userTypeId != other.userTypeId)
			return false;
		return true;
	}

	public String getDescription() {
		return this.description;
	}

	public long getUserTypeId() {
		return this.userTypeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (userTypeId ^ (userTypeId >>> 32));
		return result;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUserTypeId(long userTypeId) {
		this.userTypeId = userTypeId;
	}

	@Override
	public String toString() {
		return "UserType [description=" + description + "]";
	}

}