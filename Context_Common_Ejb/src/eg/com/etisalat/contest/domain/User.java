package eg.com.etisalat.contest.domain;

import java.io.Serializable;
import java.util.Arrays;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eg.com.etisalat.contest.utility.CommonUtility;

/**
 * The persistent class for the "USER" database table.
 * 
 */
@Entity
@Table(name = "\"USER\"")
public class User extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {

	@Id
	@SequenceGenerator(name = "USER_USERID_GENERATOR", sequenceName = "SEQ_USER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_USERID_GENERATOR")
	@Column(name = "USER_ID")
	private long userId;

	@Column(nullable = false)
	private String email;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "GROUP_COUNT")
	private Integer groupCount;

	@Column(name = "GROUP_EMAILS")
	private String groupEmails;

	@Column(name = "LAST_NAME")
	private String lastName;

	private String password;

	@Column(name = "AGE", nullable = true)
	private Integer age;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "GROUP_FLAG")
	private String groupFlag = "I";

	@Temporal(TemporalType.DATE)
	@Column(name = "REGISTRATION_DATE")
	private Date registrationDate;

	@Column(name = "MSISDN")
	private String msisdn;

	// bi-directional many-to-one association to Idea
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private List<Idea> ideas;

	// uni-directional many-to-one association to DiscussionThread
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "THREAD_ID")
	private DiscussionThread discussionThread;

	// uni-directional many-to-one association to UserStatus
	@ManyToOne
	@JoinColumn(name = "USER_STATUS_ID")
	private UserStatus userStatus;

	// uni-directional many-to-one association to UserType
	@ManyToOne
	@JoinColumn(name = "USER_TYPE_ID")
	private UserType userType;

	public User() {
		if (isNew()) {
			userStatus = UserStatus.ACTIVE;
			registrationDate = new Date();
			userType = UserType.CONTENDER;
		}
	}

	public Integer getAge() {
		return age;
	}

	public DiscussionThread getDiscussionThread() {
		return this.discussionThread;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public List<String> getFormattedGroupEmails() {
		List<String> result = null;
		if (!CommonUtility.isStringEmpty(groupEmails)) {
			result = Arrays.asList(groupEmails.split(","));
		}
		return result;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getGender() {
		return gender;
	}

	public Integer getGroupCount() {
		return this.groupCount;
	}

	public String getGroupEmails() {
		return this.groupEmails;
	}

	public String getGroupFlag() {
		return groupFlag;
	}

	public List<Idea> getIdeas() {
		return this.ideas;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public String getPassword() {
		return this.password;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public long getUserId() {
		return this.userId;
	}

	public UserStatus getUserStatus() {
		return this.userStatus;
	}

	public UserType getUserType() {
		return this.userType;
	}

	public boolean isAdmin() {
		return UserType.ADMIN.equals(userType);
	}

	public boolean isContender() {
		return UserType.CONTENDER.equals(userType);
	}

	public boolean isJudge() {
		return UserType.JUDGE.equals(userType);
	}

	@Override
	public boolean isNew() {
		return userId == 0;
	}

	public boolean isVoter() {
		return UserType.VOTER.equals(userType);
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setDiscussionThread(DiscussionThread discussionThread) {
		this.discussionThread = discussionThread;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}

	public void setGroupEmails(String groupEmails) {
		this.groupEmails = groupEmails;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public void setIdeas(List<Idea> ideas) {
		this.ideas = ideas;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	public boolean isShorlisted(){
		boolean result=false;
		for (Idea idea : ideas) {
			if (result=idea.isShotlisted()){
			break;
			}
		}
		return result;
	}
	
	public boolean isWinner(){
		boolean result=false;
		for (Idea idea : ideas) {
			if(result=idea.isWinner()){
				break;
			}
		}
		return result;		
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", firstName=" + firstName + ", groupCount=" + groupCount + ", groupEmails=" + groupEmails
				+ ", lastName=" + lastName + ", password=" + password + ", age=" + age + ", gender=" + gender + ", groupFlag=" + groupFlag
				+ ", registrationDate=" + registrationDate + ", msisdn=" + msisdn + ", userStatus=" + userStatus + ", userType=" + userType + "]";
	}
}