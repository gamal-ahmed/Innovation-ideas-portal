/**
 * 
 */
package eg.com.etisalat.contest.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eg.com.etisalat.contest.dao.ContestStageDAO;
import eg.com.etisalat.contest.dao.UserDAO;
import eg.com.etisalat.contest.domain.ContestStage;
import eg.com.etisalat.contest.domain.IdeaStatus;
import eg.com.etisalat.contest.domain.User;
import eg.com.etisalat.contest.domain.UserStatus;
import eg.com.etisalat.contest.domain.UserType;
import eg.com.etisalat.contest.utility.CommonUtility;

/**
 * @author Ahmed.Gamal
 * 
 */
@ManagedBean
@SessionScoped
public class UserSessionController {

	public static final String ARABIC_LANGUAGE = "ar";

	public static final String ENGLISH_LANGUAGE = "en";

	public static User getUserInformation(String searchToken) {
		ArrayList userList = new ArrayList();
		User ldapUser = null;
		String name = "";
		String mobile = "";
		String email = "";
		String jobTitle = "";
		String officeExt = "";
		String location = "";
		String manager = "";
		String department = "";
		boolean isFound = false;
		StringBuffer searchCriteriaBuffer = null;
		SearchControls ctrl = new SearchControls();
		try {
			searchCriteriaBuffer = new StringBuffer();
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
			env.put("java.naming.provider.url", "ldap://10.196.1.14:389");
			env.put("java.naming.security.principal", "CN=IT SOA,OU=Functional Mailboxes OU,DC=EG01,DC=Etisalat,DC=net");
			env.put("java.naming.security.credentials", "As$54321");
			env.put("java.naming.security.authentication", "simple");
			env.put("java.naming.referral", "follow");
			InitialDirContext context = null;
			context = new InitialDirContext(env);
			ctrl.setSearchScope(2);
			searchCriteriaBuffer.append("(");
			searchCriteriaBuffer.append("mail=");
			searchCriteriaBuffer.append(searchToken);
			searchCriteriaBuffer.append("*");
			searchCriteriaBuffer.append(")");
			NamingEnumeration<SearchResult> enumerationResult = context.search("DC=EG01,DC=Etisalat,DC=net", searchCriteriaBuffer.toString(), ctrl);
			if (enumerationResult != null && enumerationResult.hasMore()) {
				while (enumerationResult.hasMore()) {
					Attribute lastName;
					Attribute mobileattribute;
					Attribute emailattribute;
					ldapUser = new User();
					SearchResult searchResult = enumerationResult.next();
					Attributes attributes = searchResult.getAttributes();
					Attribute nameattribute = attributes.get("givenName");
					if (nameattribute != null) {
						name = (String) nameattribute.get();
						ldapUser.setFirstName(name);
						isFound = true;
					}
					if ((lastName = attributes.get("sn")) != null) {
						ldapUser.setLastName((String) lastName.get());
						isFound = true;
					}
					if ((mobileattribute = attributes.get("mobile")) != null) {
						ldapUser.setMsisdn((String) mobileattribute.get());
						isFound = true;
					}
					if ((emailattribute = attributes.get("mail")) != null) {
						email = (String) emailattribute.get();
						if (!email.toLowerCase().endsWith("com")) {
							email = String.valueOf(email) + "@etisalat.com";
						}
						ldapUser.setEmail(email);
					}
					if (!isFound)
						continue;
					ldapUser.setPassword("InLDAP");
					ldapUser.setGender("M");
					ldapUser.setUserStatus(UserStatus.ACTIVE);
					ldapUser.setUserType(UserType.CONTENDER);
					return ldapUser;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private String siteLanguage;
	@Inject
	private UserDAO userDAO;
	@Inject
	private ContestStageDAO stageDAO;
	private User currentUser;
	private final Date beanStartTime;

	private ContestStage currentStage;

	public UserSessionController() {
		this.determineCurrentLanguage();
		this.beanStartTime = CommonUtility.getTimeNow();
	}

	public void checkLogin() throws IOException {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request.getRemoteUser() != null) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(String.valueOf(ec.getRequestContextPath()) + "/user/userHome.xhtml");
		}
	}

	private void determineCurrentLanguage() {
		Cookie[] cookies = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getCookies();
		if (cookies != null) {
			int i = 0;
			while (i < cookies.length) {
				if (cookies[i].getName().equals("siteLanguage")) {
					this.siteLanguage = cookies[i].getValue();
				}
				++i;
			}
		}
		if (this.siteLanguage == null) {
			this.siteLanguage = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage().toLowerCase().equals("ar") ? "ar" : "en";
		}
	}

	private void determineCurrentUser() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String email = request.getRemoteUser();

		if (!CommonUtility.isStringEmpty(email)) {
			this.currentUser = this.userDAO.getUserByEmail(email.toLowerCase());
			if (this.currentUser == null) {
				User userLDAP = new User();
				userLDAP = UserSessionController.getUserInformation(email);
				this.userDAO.addEntity(userLDAP);
				this.currentUser = userLDAP;
			}
		}
	}

	public ContestStage getCurrentStage() {
		if (this.currentStage == null) {
			this.currentStage = this.stageDAO.getCurrentStage();
		} else if (!this.isCurrentStageValid()) {
			this.currentStage = this.stageDAO.getCurrentStage();
		}
		return this.currentStage;
	}

	public User getCurrentUser() {
		if (this.currentUser == null) {
			this.determineCurrentUser();
		}
		return this.currentUser;
	}

	public String getSiteLanguage() {
		return this.siteLanguage;
	}

	public boolean isAdmin() {
		return this.getCurrentUser().isAdmin();
	}

	public boolean isContender() {
		return this.getCurrentUser().isContender();
	}

	public boolean isCreateBlogEnabled() {
		if (this.isContender() && !this.isCurrentStage4()) {
			return true;
		}
		return false;
	}

	public boolean isCreateCommentEnabled() {
		if (!this.isContender() && !this.isVoter()) {
			return false;
		}
		return true;
	}

	public boolean isCurrentStage1() {
		boolean result = false;
		ContestStage stage = this.getCurrentStage();
		if (stage != null) {
			result = stage.getStageOrder() == 1;
		}
		return true;
	}

	public boolean isCurrentStage2() {
		boolean result = false;
		ContestStage stage = this.getCurrentStage();
		if (stage != null) {
			result = stage.getStageOrder() == 2;
		}
		return result;
	}

	public boolean isCurrentStage3() {
		boolean result = false;
		ContestStage stage = this.getCurrentStage();
		if (stage != null) {
			result = stage.getStageOrder() == 3;
		}
		return result;
	}

	public boolean isCurrentStage4() {
		boolean result = false;
		ContestStage stage = this.getCurrentStage();
		if (stage != null) {
			result = stage.getStageOrder() == 4 || stage.getStageOrder() == 5;
		}
		return result;
	}

	public boolean isCurrentStage5() {
		boolean result = false;
		ContestStage stage = this.getCurrentStage();
		if (stage != null) {
			result = stage.getStageOrder() == 5;
		}
		return result;
	}

	private boolean isCurrentStageValid() {
		boolean result = false;
		Calendar beanStartTime = Calendar.getInstance();
		beanStartTime.setTime(this.beanStartTime);
		Calendar currentTime = Calendar.getInstance();
		if (beanStartTime.get(8) == currentTime.get(8)) {
			result = true;
		}
		return result;
	}

	public boolean isDeleteBlogEnabled() {
		return this.isCreateBlogEnabled();
	}

	public boolean isDeleteCommentEnabled() {
		return this.isJudge();
	}

	public boolean isEditBlogEnabled() {
		return this.isCreateBlogEnabled();
	}

	public boolean isJudge() {
		return this.getCurrentUser().isJudge();
	}

	public boolean isManageBlogPostsEnabled(IdeaStatus ideaStatus) {
		if (!this.isCurrentStage4() && !this.isCurrentStage2() && this.isContender() && this.isManageIdeaEnabled(ideaStatus)) {
			return true;
		}
		return false;
	}

	public boolean isManageIdeaEnabled(IdeaStatus ideaStatus) {
		if (this.isCurrentStage1()) {
			return true;
		}
		return ideaStatus.equals(IdeaStatus.SHORT_LISTED);
	}

	public boolean isManageVoteEnabled() {
		if (!this.isCurrentStage4() && (this.isContender() || this.isVoter())) {
			return true;
		}
		return false;
	}

	public boolean isRateBlogEnabled() {
		if (!this.isContender() && !this.isVoter()) {
			return false;
		}
		return true;
	}

	public boolean isResetBlogVotesEnabled() {
		return this.isJudge();
	}

	public boolean isVoter() {
		return this.getCurrentUser().isVoter();
	}

	public String logout() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try {
			httpServletRequest.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/landing.xhtml";
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void setSiteLanguage(String userLanguage) {
		this.siteLanguage = userLanguage;
	}

	public void switchLanguage() {
		this.siteLanguage = this.siteLanguage.equals("ar") ? "en" : "ar";
		this.updateUserView();
		Cookie languageCookie = new Cookie("siteLanguage", this.siteLanguage);
		languageCookie.setMaxAge(259200);
		languageCookie.setPath("/");
		((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).addCookie(languageCookie);
	}

	private void updateUserView() {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(this.siteLanguage));
	}

	public String withDrawFromCompetition() {
		this.getCurrentUser().setUserStatus(UserStatus.OPTED_OUT);
		this.userDAO.updateEntity(this.getCurrentUser());
		return this.logout();
	}
}
