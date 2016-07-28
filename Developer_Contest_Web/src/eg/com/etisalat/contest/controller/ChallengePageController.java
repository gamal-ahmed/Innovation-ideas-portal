package eg.com.etisalat.contest.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import eg.com.etisalat.contest.dao.ChallengeDAO;
import eg.com.etisalat.contest.domain.Challenge;
import eg.com.etisalat.contest.domain.Idea;

@ManagedBean
@RequestScoped
public class ChallengePageController {
	@ManagedProperty("#{param.challengeId}")
	private Long currentChalengetId;
	private Challenge currentChallenge;
	@Inject
	private Logger logger;
	private List<Idea> currentChallengeIdeas;
	@Inject
	private ChallengeDAO challengeDAO;

	public Long getCurrentChalengetId() {
		return currentChalengetId;
	}

	public Challenge getCurrentChallenge() {
		return currentChallenge;
	}

	public List<Idea> getCurrentChallengeIdeas(int max) {
		currentChallengeIdeas = challengeDAO.getAllCallhengeIdeas(max, currentChallenge);
		return currentChallengeIdeas;
	}

	public void initiateChallenge(ComponentSystemEvent systemEvent) {

		if (currentChalengetId == null) {
			String message = "Bad request. Please use a link from within the system.";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
			return;
		}

		currentChallenge = challengeDAO.getEntityById(currentChalengetId);

		if (currentChallenge == null) {
			String message = "Bad request. Unknown contest.";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
		}

	}

	public void setCurrentChalengetId(Long currentChalengetId) {
		this.currentChalengetId = currentChalengetId;
	}

	public void setCurrentChallenge(Challenge currentChallenge) {
		this.currentChallenge = currentChallenge;
	}

	public void setCurrentChallengeIdeas(List<Idea> currentChallengeIdeas) {
		this.currentChallengeIdeas = currentChallengeIdeas;
	}

}
