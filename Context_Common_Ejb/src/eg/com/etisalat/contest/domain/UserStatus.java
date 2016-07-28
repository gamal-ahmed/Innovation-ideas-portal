package eg.com.etisalat.contest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the USER_STATUS database table.
 * 
 */
@Entity
@Table(name = "USER_STATUS")
public class UserStatus extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static List<UserStatus> getAllUserStatus() {
		List<UserStatus> allUserStatus = new ArrayList<UserStatus>();
		allUserStatus.add(ACTIVE);
		allUserStatus.add(SUSPENDED);
		allUserStatus.add(OPTED_OUT);
		allUserStatus.add(BLACKLISTED);
		return allUserStatus;
	}

	@Id
	@Column(name = "USER_STATUS_ID")
	private long userStatusId;

	private String description;

	public static final UserStatus ACTIVE = new UserStatus(1, "Active");
	public static final UserStatus SUSPENDED = new UserStatus(2, "Suspended");
	public static final UserStatus OPTED_OUT = new UserStatus(3, "Opted out");
	public static final UserStatus BLACKLISTED = new UserStatus(4, "Blacklisted");

	public UserStatus() {
	}

	/**
	 * @param userStatusId
	 * @param description
	 */
	public UserStatus(long userStatusId, String description) {
		super();
		this.userStatusId = userStatusId;
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
		UserStatus other = (UserStatus) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (userStatusId != other.userStatusId)
			return false;
		return true;
	}

	public String getDescription() {
		return this.description;
	}

	public long getUserStatusId() {
		return this.userStatusId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (userStatusId ^ (userStatusId >>> 32));
		return result;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUserStatusId(long userStatusId) {
		this.userStatusId = userStatusId;
	}

	@Override
	public String toString() {
		return "UserStatus [description=" + description + "]";
	}

}