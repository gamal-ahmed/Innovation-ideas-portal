package eg.com.etisalat.contest.controller;

import static eg.com.etisalat.jsf.utility.JSFUtils.getFromFlash;
import static eg.com.etisalat.jsf.utility.JSFUtils.sendToFlash;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import eg.com.etisalat.contest.dao.ContestDAO;
import eg.com.etisalat.contest.domain.Challenge;
import eg.com.etisalat.contest.domain.Contest;

@ManagedBean
@RequestScoped
public class ContestPageController {
	@ManagedProperty("#{param.id}")
	private Long currentContestId;
	private Contest currentContest;
	@Inject
	private Logger logger;
	private List<Challenge> currentContestChallenges;
	@Inject
	private ContestDAO contestDAO;

	public Contest getCurrentContest() {
		return currentContest;
	}

	public List<Challenge> getCurrentContestChallenges(int max) {

		if (currentContest != null) {
			currentContestChallenges = currentContest.getChallenges();
			// send the current contest again to flash for the upcomming ajax
			// request
			sendToFlash("currentContest", currentContest);
		} else {
			currentContest = (Contest) getFromFlash("currentContest");
			currentContestChallenges = currentContest.getChallenges();

		}
		int lentgh = 0;
		lentgh = currentContestChallenges.size();

		if (max > 0)
			if (max < lentgh)
				return currentContestChallenges.subList(0, max);
		return currentContestChallenges;

	}

	public Long getCurrentContestId() {
		return currentContestId;
	}

	@PostConstruct
	public void init() {
		int x;
		if (currentContestId == null)
			x = 5;
	}

	public void initiateContest(ComponentSystemEvent systemEvent) {

		if (currentContestId == null) {
			String message = "Bad request. Please use a link from within the system.";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
			return;
		}

		currentContest = contestDAO.getContestById(currentContestId);

		if (currentContest != null)
			sendToFlash("currentContest", currentContest);
		if (currentContest == null) {
			String message = "Bad request. Unknown contest.";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
		}

	}

	public void setCurrentContest(Contest currentContest) {
		this.currentContest = currentContest;
	}

	public void setCurrentContestChallenges(List<Challenge> currentContestChallenges) {
		this.currentContestChallenges = currentContestChallenges;
	}

	public void setCurrentContestId(Long currentContestId) {
		this.currentContestId = currentContestId;
	}
}
