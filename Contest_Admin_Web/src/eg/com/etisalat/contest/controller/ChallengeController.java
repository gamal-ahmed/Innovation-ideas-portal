package eg.com.etisalat.contest.controller;

import static eg.com.etisalat.jsf.utility.JSFUtils.getFromFlash;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import eg.com.etisalat.contest.dao.ChallengeDAO;
import eg.com.etisalat.contest.dao.ContestDAO;
import eg.com.etisalat.contest.domain.Challenge;
import eg.com.etisalat.contest.domain.Contest;
import eg.com.etisalat.contest.domain.ContestType;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaBlog;
import eg.com.etisalat.contest.domain.User;
import eg.com.etisalat.jsf.utility.JSFUtils;

@ManagedBean
@ViewScoped
public class ChallengeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userSessionController}")
	UserSessionController userSessionController;
	
	@Inject
	private ChallengeDAO challengeDAO;
	@Inject
	private ContestDAO contestDAO;
	private Contest parentContest;
	private boolean hasParentContest = false;

	public boolean getHasParentContest() {
		return hasParentContest;
	}

	public void setHasParentContest(boolean hasParentContest) {
		this.hasParentContest = hasParentContest;
	}

	public Contest getParentContest() {
		return parentContest;
	}

	private List<Contest> AllContests;

	public List<Contest> getAllContests() {
		return AllContests;
	}

	@Inject
	private Logger logger;

	private Challenge currentChallenge;

	public Contest getparentContest() {
		return parentContest;
	}

	public void setparentContest(Contest parentContest) {
		this.parentContest = parentContest;
	}

	private User currentUser;

	public Challenge getCurrentChallenge() {
		return currentChallenge;
	}

	public void setCurrentChallenge(Challenge currentChallenge) {
		this.currentChallenge = currentChallenge;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void initiateChalleng(ComponentSystemEvent systemEvent) {
		if (parentContest == null) {
			parentContest = (Contest) getFromFlash("contest");
			if (parentContest != null) {
				hasParentContest = true;
			}

		}
		if (currentChallenge == null) {
			currentChallenge = (Challenge) getFromFlash("challenge");
			if (currentChallenge == null) {

				currentChallenge = new Challenge();
			}
			AllContests = contestDAO.getAllEntites(Contest.class);
		}

	}

	public String creatChallenge() {
		if (currentChallenge != null) {
			if (hasParentContest) {
				if (parentContest.getContestTypeID().getDescription()
						.equals(ContestType.INTERNAL.getDescription())) {
					currentChallenge.setInternal(1);

				} else
					currentChallenge.setInternal(0);

			}
			currentChallenge.setContest(parentContest);
			Challenge insertedChallenge = challengeDAO
					.addEntity(currentChallenge);
			if (insertedChallenge != null)
				JSFUtils.displayInfoMessage(
						"user.manageUser.message.userUpdated",
						currentChallenge.getName());
			return "/admin/index.xhtml?faces-redirect=true";
			//
		}

		return "/errorPage.xhtml?faces-redirect=true";
	}

	public UserSessionController getUserSessionController() {
		return userSessionController;
	}

	public void setUserSessionController(
			UserSessionController userSessionController) {
		this.userSessionController = userSessionController;
	}

}
