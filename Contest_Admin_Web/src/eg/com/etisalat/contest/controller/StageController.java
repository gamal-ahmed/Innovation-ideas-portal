package eg.com.etisalat.contest.controller;

import static eg.com.etisalat.contest.utility.CommonUtility.isEmptyList;
import static eg.com.etisalat.jsf.utility.JSFUtils.displayErrorMessage;
import static eg.com.etisalat.jsf.utility.JSFUtils.displayInfoMessage;
import static eg.com.etisalat.jsf.utility.JSFUtils.getFromFlash;
import static eg.com.etisalat.jsf.utility.JSFUtils.sendToFlash;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import org.richfaces.component.SortOrder;

import eg.com.etisalat.contest.dao.ContestStageDAO;
import eg.com.etisalat.contest.domain.ContestStage;

@ManagedBean
@ViewScoped
public class StageController {

	@Inject
	private ContestStageDAO contestStageDAO;

	private ContestStage currentContestStage;
	private List<ContestStage> allStages;
	@ManagedProperty(value = "#{userSessionController}")
	private UserSessionController userSessionController;
	private SortOrder dateOrder = SortOrder.unsorted;

	public StageController() {
	}

	public String editStage() {
		sendToFlash("stage", currentContestStage);
		return "createStage?faces-redirect=true";
	}

	public List<ContestStage> getAllStage() {
		return allStages;
	}

	public ContestStage getcurrentContestStage() {
		return currentContestStage;
	}

	/**
	 * @return the dateOrder
	 */
	public SortOrder getDateOrder() {
		return dateOrder;
	}

	public UserSessionController getUserSessionController() {
		return userSessionController;
	}

	public void initiateStage(ComponentSystemEvent systemEvent) {
		if (currentContestStage == null) {
			currentContestStage = (ContestStage) getFromFlash("stage");
			if (currentContestStage == null) {
				currentContestStage = new ContestStage();
			}
		}
	}

	public void loadAllStages(ComponentSystemEvent systemEvent) {
		if (isEmptyList(allStages)) {
			allStages = contestStageDAO.getAllEntites(ContestStage.class);
		}
	}

	public void setAllStage(List<ContestStage> allStage) {
		this.allStages = allStage;
	}

	public void setcurrentContestStage(ContestStage currentContestStage) {
		this.currentContestStage = currentContestStage;
	}

	/**
	 * @param dateOrder
	 *            the dateOrder to set
	 */
	public void setDateOrder(SortOrder dateOrder) {
		this.dateOrder = dateOrder;
	}

	public void setUserSessionController(UserSessionController userSessionController) {
		this.userSessionController = userSessionController;
	}

	public void sortByStartDate() {

		if (dateOrder.equals(SortOrder.ascending)) {
			setDateOrder(SortOrder.descending);
		} else {
			setDateOrder(SortOrder.ascending);
		}
	}

	public void updateStage() {
		if ((isValidStartDate())) {
			contestStageDAO.updateEntity(currentContestStage);
			displayInfoMessage("stage.manageStage.message.stageUpdated", currentContestStage.getStageName(userSessionController.getSiteLanguage()));

		}

	}

	private boolean isValidStartDate() {
		// TODO Auto-generated method stub
		Date previousSatageDate = null;
		Date nextSatageDate = null;
		int previousOrderId = (currentContestStage.getStageOrder()) - 1;
		int nextOrderId = (currentContestStage.getStageOrder()) + 1;
		boolean result = false;
		if (currentContestStage.getStageStartDate().compareTo(new Date()) >= 0) {
			if (contestStageDAO.getStageByOrder(previousOrderId) != null) {
				previousSatageDate = contestStageDAO.getStageByOrder(previousOrderId).getStageStartDate();
			}
			if (previousSatageDate == null || (currentContestStage.getStageStartDate().compareTo(previousSatageDate) >= 0)) {

				if (contestStageDAO.getStageByOrder(nextOrderId) != null) {
					nextSatageDate = contestStageDAO.getStageByOrder(nextOrderId).getStageStartDate();
				}
				if (nextSatageDate == null || (currentContestStage.getStageStartDate().compareTo(nextSatageDate) <= 0)) {
					result = true;
				}

				else {
					displayErrorMessage("stage.manageStage.message.stageDateAfterNextStage",
							currentContestStage.getStageName(userSessionController.getSiteLanguage()));
				}

			}

			else {
				displayErrorMessage("stage.manageStage.message.stageDateBeforePreviousStage",
						currentContestStage.getStageName(userSessionController.getSiteLanguage()));
			}

		}

		else {
			displayErrorMessage("stage.manageStage.message.pastDate", currentContestStage.getStageName(userSessionController.getSiteLanguage()));
		}

		return result;

	}

}
