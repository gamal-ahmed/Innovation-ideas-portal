package eg.com.etisalat.contest.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import eg.com.etisalat.contest.dao.ContestDAO;
import eg.com.etisalat.contest.domain.Challenge;
import eg.com.etisalat.contest.domain.Contest;
import eg.com.etisalat.contest.domain.ContestType;
import eg.com.etisalat.contest.domain.User;

@ManagedBean
@ViewScoped
public class ContestController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ContestType> allContestTypes;

	private List<Contest> filteredValues;

	private List<Contest> allContests;

	@Inject
	private ContestDAO contestDAO;

	@Inject
	private Logger logger;

	private List<Challenge> currentContestChallenges;
	private ContestType currentContestType;

	private boolean canSubmitContest;

	private boolean canEditDesc;

	private User currentUser;

	@ManagedProperty(value = "#{userSessionController}")
	UserSessionController userSessionController;

	private Contest ContesttoDelete;

	public String filePath;
	private List<Contest> listOfAllContests;
	private UIInput contestName;

	private UIInput contestDescription;

	private boolean contestAddedSuccessfully;

	public ContestController() {

	}

	public List<Contest> getAllContests() {

		allContests = contestDAO.getAllEntites(Contest.class);
		return allContests;

	}

	public List<ContestType> getAllContestTypes() {
		return allContestTypes;
	}

	public List<Contest> getAllExternalContests(int max) {
		List<Contest> externalContests = new ArrayList<Contest>();

		externalContests = contestDAO.getAllContestsByType(ContestType.EXTERNAL, max, false);
		return externalContests;

	}

	public List<Contest> getAllInternalContests(int max) {
		List<Contest> internalContests = new ArrayList<Contest>();

		internalContests = contestDAO.getAllContestsByType(ContestType.INTERNAL, max, false);
		return internalContests;

	}

	public UIInput getContestDescription() {
		return contestDescription;
	}

	public UIInput getContestName() {
		return contestName;
	}

	public Contest getContesttoDelete() {
		return ContesttoDelete;
	}

	public ContestType getCurrentContestType() {
		return currentContestType;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public List<Contest> getFilteredValues() {
		return filteredValues;
	}

	public boolean isCanEditDesc() {
		return canEditDesc;
	}

	public boolean isCanSubmitContest() {
		return canSubmitContest;
	}

	public boolean isContestAddedSuccessfully() {
		return contestAddedSuccessfully;
	}

	public void setAllContests(List<Contest> allContests) {
		this.allContests = allContests;
	}

	public void setCanEditDesc(boolean canEditDesc) {
		this.canEditDesc = canEditDesc;
	}

	public void setCanSubmitContest(boolean canSubmitContest) {
		this.canSubmitContest = canSubmitContest;
	}

	public void setContestAddedSuccessfully(boolean contestAddedSuccessfully) {
		this.contestAddedSuccessfully = contestAddedSuccessfully;
	}

	public void setContestDescription(UIInput contestDescription) {
		this.contestDescription = contestDescription;
	}

	public void setContestName(UIInput contestName) {
		this.contestName = contestName;
	}

	public void setContesttoDelete(Contest contesttoDelete) {
		ContesttoDelete = contesttoDelete;
	}

	public void setCurrentContestType(ContestType currentContestType) {
		this.currentContestType = currentContestType;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void setFilteredValues(List<Contest> filteredValues) {
		this.filteredValues = filteredValues;
	}

	public void setListOfAllContests(List<Contest> listOfAllContests) {
		this.listOfAllContests = listOfAllContests;
	}

	public void setUserSessionController(UserSessionController userSessionController) {
		this.userSessionController = userSessionController;
	}

}
