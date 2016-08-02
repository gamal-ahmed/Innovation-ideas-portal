/**
 * 
 */
package eg.com.etisalat.contest.controller;

import static eg.com.etisalat.contest.utility.CommonUtility.isEmptyList;
import static eg.com.etisalat.jsf.utility.JSFUtils.displayErrorMessage;
import static eg.com.etisalat.jsf.utility.JSFUtils.displayInfoMessage;
import static eg.com.etisalat.jsf.utility.JSFUtils.getFromFlash;
import static eg.com.etisalat.jsf.utility.JSFUtils.getFromSession;
import static eg.com.etisalat.jsf.utility.JSFUtils.sendToFlash;
import static eg.com.etisalat.jsf.utility.JSFUtils.sendToSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.richfaces.model.Filter;

import eg.com.etisalat.contest.dao.IdeaDAO;
import eg.com.etisalat.contest.dao.RetreivingCriteria;
import eg.com.etisalat.contest.dao.UserDAO;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.ThreadStatus;
import eg.com.etisalat.contest.domain.User;
import eg.com.etisalat.contest.domain.UserStatus;
import eg.com.etisalat.contest.domain.UserType;
import eg.com.etisalat.contest.utility.CommonUtility;
import eg.com.etisalat.contest.utility.EncryptionUtility;
import eg.com.etisalat.contest.utility.MailThread;
import eg.com.etisalat.contest.utility.SettingCashManager;
import eg.com.etisalat.contest.utility.SettingsKeys;
import eg.com.etisalat.contest.utility.URLUtility;

/**
 * @author karim.azkoul
 * 
 */

@ManagedBean
@ViewScoped
public class UserController implements Serializable {

	@Inject
	private Logger logger;

	@Inject
	private UserDAO userDAO;

	@ManagedProperty(value = "#{userSessionController}")
	UserSessionController userSessionController;
	private List<User> allUsers;
	private List<Idea> recentUserIdeas;

	private User currentUser = new User();

	private String confirmPassword;

	private String nameFilter;

	@Inject
	private IdeaDAO ideaDAO;

	private List<UserStatus> allUserStatus;
	private String regestrationType = "developer";

	String capatchValue;

	private String statusFilter;
	public String msisdnFilter;

	public String emailFilter;

	ThreadStatus threadStatusfilter;

	// @Inject this is valid only if bean is in request scope
	// private FacesContext context;

	private UserType userTypeFilterValue;

	private boolean validUser = false;

	private String oldPassword;

	public UserController() {
	}

	public String addNewUser() {
		// threadDAO.getAllEntites(DiscussionThread.class);
		if (userSessionController.isCurrentStage2() || userSessionController.isCurrentStage3()) {
			currentUser.setUserType(UserType.VOTER);
		}
		String outCome = "landingPage";
		if (userDAO.getUserByEmail(currentUser.getEmail()) != null) {
			displayErrorMessage("user.manageUser.message.userExists", currentUser.getEmail());
			return "";
		} else if (!confirmPassword.equals(currentUser.getPassword())) {
			displayErrorMessage("user.manageUser.message.confirmPassword", currentUser.getEmail());
			return "";
		} else if (currentUser.getPassword().equalsIgnoreCase(currentUser.getEmail())) {
			displayErrorMessage("user.manageUser.message.passwordsameasusername", currentUser.getEmail());
			return "";
		} else {
			String orginalPasswd = currentUser.getPassword();
			String hashedPasswd = CommonUtility.hashPassword(currentUser.getPassword());
			currentUser.setPassword(hashedPasswd);
			userDAO.addEntity(currentUser);
			sendRegestrationMail();
			currentUser.setPassword(orginalPasswd);
			login();
		}
		return outCome;

	}

	public String changePassword() {
		String result = "";
		if (currentUser == null) {
			displayErrorMessage("user.usermanager.message.userdoesnotexist");
		} else {
			String originalPassword = currentUser.getPassword();
			if (updateUser()) {
				currentUser.setPassword(originalPassword);
				result = login();
			}
			confirmPassword = null;
		}

		return result;

	}

	public void changeRegestrationType(ValueChangeEvent changeEvent) {
		if (regestrationType.equalsIgnoreCase("developer")) {
			currentUser.setUserType(UserType.VOTER);
		} else {
			currentUser.setUserType(UserType.CONTENDER);
		}

	}

	public void changeUserStatus() {
		userDAO.updateEntity(currentUser);
	}

	public void clear() {
		currentUser = new User();
	}

	public void deleteUser() {
		userDAO.deleteEntity(currentUser);
	}

	public String editThread() {
		sendToSession("currentThread", currentUser.getDiscussionThread());
		return "/idea/discustionBoard?faces-redirect=true";
	}

	public String editUser() {
		sendToFlash("user", currentUser);
		return "createUser?faces-redirect=true";
	}

	public List<User> getAllUsers() {
		return allUsers;
	}

	public List<UserStatus> getAllUserStatus() {
		return allUserStatus;
	}

	public String getCapatchValue() {
		return capatchValue;
	}

	private String getChangePasswordURL(String email) throws Exception {
		String encriptedmail = "";
		String encryptedDate = "";
		String currentDate = Long.toString(System.currentTimeMillis());

		EncryptionUtility encryptor;
		encryptor = new EncryptionUtility(EncryptionUtility.GENERAL_KEY);

		encryptedDate = encryptor.encrypt(currentDate);
		encriptedmail = encryptor.encrypt(email);

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String path = SettingCashManager.getIistance().getSettingValue(SettingsKeys.CONTEST_URL) + request.getContextPath() + "/changePassword.xhtml"
				+ "?token1=" + URLUtility.URLEncoding(encriptedmail) + "&token2=" + URLUtility.URLEncoding(encryptedDate);
		return path;
	}

	public ThreadStatus getClosedStatus() {
		return ThreadStatus.CLOSED;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public UserType getContentnderUserType() {
		return UserType.CONTENDER;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public String getEmailFilter() {
		return emailFilter;
	}

	public Filter<?> getFilterStatus() {
		return new Filter<User>() {
			@Override
			public boolean accept(User u) {
				String status = getStatusFilter();
				if (status == null || status.length() == 0 || status.equals(u.getUserStatus().getDescription())) {
					return true;
				}
				return false;
			}
		};
	}

	List<Idea> getHotestUserIdeas() {
		return ideaDAO.getAllIdeasForUser(currentUser, RetreivingCriteria.HOT, 3);
	}

	public String getMsisdnFilter() {
		return msisdnFilter;
	}

	public String getNameFilter() {
		return nameFilter;
	}

	public ThreadStatus getNoStatus() {
		return new ThreadStatus();
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public ThreadStatus getOpendStatus() {
		return ThreadStatus.WAIT_FOR_RESPONSE;
	}

	public List<Idea> getRecentUserIdeas() {
		recentUserIdeas = ideaDAO.getAllIdeasForUser(currentUser, RetreivingCriteria.RECENT, 3);

		return recentUserIdeas;
	}

	public String getRegestrationType() {
		return regestrationType;
	}

	public String getStatusFilter() {
		return statusFilter;
	}

	public Filter<?> getThreadFilterImpl() {
		return new Filter<User>() {
			@Override
			public boolean accept(User user) {
				if (getThreadStatusfilter() == null || getThreadStatusfilter().equals(getNoStatus())) {
					return true;
				}
				if (user.getDiscussionThread() == null) {
					return false;
				}
				return user.getDiscussionThread().getThreadStatus().equals(getThreadStatusfilter());
			}
		};

	}

	public ThreadStatus getThreadStatusfilter() {
		return threadStatusfilter;
	}

	public UserSessionController getUserSessionController() {
		return userSessionController;
	}

	public Filter<?> getUserTypeFilterImpl() {
		return new Filter<User>() {
			@Override
			public boolean accept(User user) {
				UserType none = new UserType();
				none.setUserTypeId(-1);
				if (getUserTypeFilterValue() == null || getUserTypeFilterValue().equals(none)) {
					return true;
				}
				return user.getUserType().equals(getUserTypeFilterValue());
			}
		};

	}

	public UserType getUserTypeFilterValue() {
		return userTypeFilterValue;
	}

	public List<UserType> getUserTypes() {
		List<UserType> list = new ArrayList<UserType>();
		UserType none = new UserType();
		none.setDescription("        ");
		none.setUserTypeId(-1);
		list.add(none);
		list.add(UserType.CONTENDER);
		list.add(UserType.VOTER);

		return list;
	}

	public UserType getVoterUserType() {
		return UserType.VOTER;
	}

	public void initiateUser(ComponentSystemEvent systemEvent) {

		User flashUser = (User) getFromFlash("user");
		if (flashUser != null) {
			currentUser = flashUser;
		} else {
			User sessionUser = (User) getFromSession("user");
			if (sessionUser != null) {
				currentUser = sessionUser;
			}
		}

	}

	/**
	 * @return the validUser
	 */
	public boolean isValidUser() {
		return validUser;
	}

	public void loadAllUsers(ComponentSystemEvent systemEvent) {
		if (isEmptyList(allUsers)) {
			allUsers = userDAO.getAllEntites(User.class);
		}

	}

	public void loadAllUserStatus() {
		if (CommonUtility.isEmptyList(allUserStatus)) {
			allUserStatus = UserStatus.getAllUserStatus();
		}
	}

	public void loadChangePassword() throws Exception {

		String mail = "";
		long sentTime = 0L;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		if (request.getParameter("token1") != null & request.getParameter("token2") != null) {

			EncryptionUtility encryptor;

			encryptor = new EncryptionUtility(EncryptionUtility.GENERAL_KEY);
			sentTime = Long.valueOf(encryptor.decrypt(URLUtility.URLDecoding(request.getParameter("token2"))));
			mail = encryptor.decrypt(URLUtility.URLDecoding(request.getParameter("token1")));

			long currentTime = System.currentTimeMillis();
			if (((currentTime - sentTime) < (30 * 60 * 1000))) {
				User user = userDAO.getUserByEmail(mail);
				if ((user != null)) {
					// user.setPassword(null);
					currentUser = user;
					validUser = true;
				} else {
					validUser = false;
				}
			} else {
				validUser = false;
			}

		}

	}

	public String login() {
		String userName = "";
		String password = "";
		String outcome = "loginFail";
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try {
			User loggedinUser = userSessionController.getCurrentUser();
			if (loggedinUser == null) {
				if (currentUser.getEmail().toLowerCase().contains("@etisalat.com")) {// contains(""))
					// {{
					userName = currentUser.getEmail().toLowerCase().replace("@etisalat.com", "");

				} else if (currentUser.getEmail().toLowerCase().contains("@outsource.etisalat.com")) {// contains(""))

					userName = currentUser.getEmail().toLowerCase().replace("@outsource.etisalat.com", "");

				}

				httpServletRequest.login(userName, currentUser.getPassword());
				currentUser = userSessionController.getCurrentUser();
			}

			outcome = "contest/contests";
			loggedinUser = userSessionController.getCurrentUser();

			if (loggedinUser.isAdmin() || loggedinUser.isJudge()) {
				outcome = "user/judgeHome";
			}
		} catch (ServletException e) {
			e.printStackTrace();
			return outcome;
		}

		return outcome;
	}

	public void resetPassword() throws Exception {

		String email = currentUser.getEmail();
		User user = userDAO.getUserByEmail(email);
		if (user == null) {
			displayErrorMessage("user.usermanager.message.userdoesnotexist", currentUser.getEmail());
			return;
		} else {
			String URL = getChangePasswordURL(user.getEmail());
			new MailThread(SettingCashManager.getIistance().getSettingValue(SettingsKeys.RESET_TEMPLATE), user.getEmail(), null, user.getFirstName(), "", "",
					"", "", URL).start();
			displayInfoMessage("user.usermanager.message.passwordchnage");
		}

	}

	private void sendRegestrationMail() {
		new MailThread(SettingCashManager.getIistance().getSettingValue(SettingsKeys.REG_TEMPLATE), currentUser.getEmail(),
				currentUser.getFormattedGroupEmails(), currentUser.getFirstName(), "", "", "", "").start();
	}

	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}

	public void setAllUserStatus(List<UserStatus> allUserStatus) {
		this.allUserStatus = allUserStatus;
	}

	public void setCapatchValue(String capatchValue) {
		this.capatchValue = capatchValue;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void setEmailFilter(String emailFilter) {
		this.emailFilter = emailFilter;
	}

	public void setMsisdnFilter(String msisdnFilter) {
		this.msisdnFilter = msisdnFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setRegestrationType(String regestrationType) {
		this.regestrationType = regestrationType;
	}

	public void setStatusFilter(String statusFilter) {
		this.statusFilter = statusFilter;
	}

	public void setThreadStatusfilter(ThreadStatus threadStatusfilter) {
		this.threadStatusfilter = threadStatusfilter;
	}

	public void setUserSessionController(UserSessionController userSessionController) {
		this.userSessionController = userSessionController;
	}

	public void setUserTypeFilterValue(UserType userTypeFilterValue) {
		this.userTypeFilterValue = userTypeFilterValue;
	}

	/**
	 * @param validUser
	 *            the validUser to set
	 */
	public void setValidUser(boolean validUser) {
		this.validUser = validUser;
	}

	public String updateProfile() {
		User user = userSessionController.getCurrentUser();
		sendToSession("user", user);
		return "updateUser";

	}

	public boolean updateUser() {
		if (currentUser.getPassword() != null && confirmPassword != null) {
			if (!confirmPassword.equals(currentUser.getPassword())) {
				displayErrorMessage("user.manageUser.message.confirmPassword");
				return false;
			} else if (currentUser.getPassword().equalsIgnoreCase(currentUser.getEmail())) {
				displayErrorMessage("user.manageUser.message.passwordsameasusername", currentUser.getEmail());
				return false;
			} else {
				if (oldPassword != null && !userDAO.getEntityByPK(currentUser).getPassword().equals(CommonUtility.hashPassword(oldPassword))) {
					displayErrorMessage("user.manageUser.message.invalidOldPassword");
					confirmPassword = null;
					return false;
				}

				if (currentUser.getPassword() != null && !currentUser.getPassword().equalsIgnoreCase("")) {
					String hashedPasswd = CommonUtility.hashPassword(currentUser.getPassword());
					currentUser.setPassword(hashedPasswd);
				}

			}
		}
		confirmPassword = null;
		if (currentUser.isContender() || currentUser.isVoter()) {
			userDAO.updateUserExceptUserStatus(currentUser);
		} else {
			userDAO.updateEntity(currentUser);
		}
		displayInfoMessage("user.manageUser.message.userUpdated", currentUser.getEmail());
		return true;
	}

}
