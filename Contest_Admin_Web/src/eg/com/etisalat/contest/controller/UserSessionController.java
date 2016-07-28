/**
 * 
 */
package eg.com.etisalat.contest.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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
import eg.com.etisalat.contest.utility.CommonUtility;

/**
 * @author karim.azkoul
 * 
 */
@ManagedBean
@SessionScoped
public class UserSessionController {

	public static final String ARABIC_LANGUAGE = "ar";
	public static final String ENGLISH_LANGUAGE = "en";

	private String siteLanguage;

	@Inject
	private UserDAO userDAO;

	@Inject
	private ContestStageDAO stageDAO;

	private User currentUser;

	private final Date beanStartTime;

	private ContestStage currentStage;

	public UserSessionController() {
		determineCurrentLanguage();
		beanStartTime = CommonUtility.getTimeNow();
	}

	private void determineCurrentLanguage() {
		// TODO: read language from cookie
		Cookie[] cookies = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("siteLanguage")) {
					siteLanguage = cookies[i].getValue();
				}
			}
		}
		if (siteLanguage == null) {
			if (FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage().toLowerCase().equals(ARABIC_LANGUAGE)) {
				siteLanguage = ARABIC_LANGUAGE;
			} else {
				siteLanguage = ENGLISH_LANGUAGE;
			}
		}
	}

	private void determineCurrentUser() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String email = request.getRemoteUser();
		if (!CommonUtility.isStringEmpty(email)) {
			currentUser = userDAO.getUserByEmail(email);
		}
	}

	public ContestStage getCurrentStage() {
		if (currentStage == null) {
			currentStage = stageDAO.getCurrentStage();
		} else {
			if (!isCurrentStageValid()) {
				currentStage = stageDAO.getCurrentStage();
			}
		}
		return currentStage;
	}

	/**
	 * @return the currentUser
	 */
	public User getCurrentUser() {
		if (currentUser == null) {
			determineCurrentUser();
		}
		return currentUser;
	}

	/**
	 * @return the userLanguage
	 */
	public String getSiteLanguage() {
		return siteLanguage;
	}

	public boolean isAdmin() {
		return getCurrentUser().isAdmin();
	}

	public boolean isContender() {
		return getCurrentUser().isContender();
	}

	public boolean isCreateBlogEnabled() {
		return isContender() && (!isCurrentStage4());
	}

	public boolean isCreateCommentEnabled() {
		return isContender() || isVoter();
	}

	public boolean isCurrentStage1() {
		boolean result = false;
		ContestStage stage = getCurrentStage();
		if (stage != null) {
			result = stage.getStageOrder() == 1;
		}
		return result;
	}

	public boolean isCurrentStage2() {
		boolean result = false;
		ContestStage stage = getCurrentStage();
		if (stage != null) {
			result = stage.getStageOrder() == 2;
		}
		return result;
	}

	public boolean isCurrentStage3() {
		boolean result = false;
		ContestStage stage = getCurrentStage();
		if (stage != null) {
			result = stage.getStageOrder() == 3;
		}
		return result;
	}

	public boolean isCurrentStage4() {
		boolean result = false;
		ContestStage stage = getCurrentStage();
		if (stage != null) {
			result = stage.getStageOrder() == 4 || stage.getStageOrder() == 5;
		}
		return result;
	}

	private boolean isCurrentStageValid() {
		boolean result = false;
		Calendar beanStartTime = Calendar.getInstance();
		beanStartTime.setTime(this.beanStartTime);

		Calendar currentTime = Calendar.getInstance();

		if (beanStartTime.get(Calendar.DAY_OF_WEEK_IN_MONTH) == currentTime.get(Calendar.DAY_OF_WEEK_IN_MONTH)) {
			result = true;
		}

		return result;
	}

	public boolean isDeleteBlogEnabled() {
		return isCreateBlogEnabled();
	}

	public boolean isDeleteCommentEnabled() {
		return isJudge();
	}

	public boolean isEditBlogEnabled() {
		return isCreateBlogEnabled();
	}

	public boolean isJudge() {
		return getCurrentUser().isJudge();
	}

	public boolean isManageBlogPostsEnabled(IdeaStatus ideaStatus) {

		return (!isCurrentStage4() && !isCurrentStage2() && isContender() && isManageIdeaEnabled(ideaStatus));

	}

	public boolean isManageIdeaEnabled(IdeaStatus ideaStatus) {
		if (isCurrentStage1())
			return true;
		else
			return ideaStatus.equals(IdeaStatus.SHORT_LISTED);
	}

	public boolean isManageVoteEnabled() {

		return (!isCurrentStage4() && (isContender() || isVoter()));

	}

	public boolean isRateBlogEnabled() {

		return (isContender() || isVoter());
	}

	public boolean isResetBlogVotesEnabled() {
		return isJudge();
	}

	public boolean isVoter() {
		return getCurrentUser().isVoter();
	}

	public String logout() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try {
			httpServletRequest.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";
	}

	/**
	 * @param currentUser
	 *            the currentUser to set
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * @param userLanguage
	 *            the userLanguage to set
	 */
	public void setSiteLanguage(String userLanguage) {
		this.siteLanguage = userLanguage;
	}

	public void switchLanguage() {
		if (siteLanguage.equals(ARABIC_LANGUAGE)) {
			siteLanguage = ENGLISH_LANGUAGE;
		} else {
			siteLanguage = ARABIC_LANGUAGE;
		}
		updateUserView();
		// TODO:write site language in cookie
		Cookie languageCookie = new Cookie("siteLanguage", siteLanguage);
		languageCookie.setMaxAge(6 * 30 * 24 * 60); // 6 months
		languageCookie.setPath("/");
		((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).addCookie(languageCookie);
	}

	private void updateUserView() {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(siteLanguage));
	}

	public String withDrawFromCompetition() {
		getCurrentUser().setUserStatus(UserStatus.OPTED_OUT);
		userDAO.updateEntity(getCurrentUser());
		return logout();

	}
}
