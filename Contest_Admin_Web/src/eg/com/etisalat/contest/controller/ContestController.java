package eg.com.etisalat.contest.controller;

import static eg.com.etisalat.jsf.utility.JSFUtils.getFromFlash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import eg.com.etisalat.contest.dao.ContestDAO;
import eg.com.etisalat.contest.domain.Contest;
import eg.com.etisalat.contest.domain.ContestType;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaBlog;
import eg.com.etisalat.contest.domain.User;
import eg.com.etisalat.contest.utility.FileModel;
import eg.com.etisalat.contest.utility.SettingCashManager;
import eg.com.etisalat.jsf.utility.JSFUtils;

@ManagedBean
@ViewScoped
public class ContestController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ContestType> allContestTypes;

	public List<ContestType> getAllContestTypes() {
		return allContestTypes;
	}

	@Inject
	private ContestDAO contestDAO;

	@Inject
	private Logger logger;

	private Contest currentContest;

	private ContestType currentContestType;

	public ContestType getCurrentContestType() {
		return currentContestType;
	}

	public void setCurrentContestType(ContestType currentContestType) {
		this.currentContestType = currentContestType;
	}

	private boolean canSubmitContest;

	private boolean canEditDesc;

	private User currentUser;

	@ManagedProperty(value = "#{userSessionController}")
	UserSessionController userSessionController;

	public void initiateContest(ComponentSystemEvent systemEvent) {
		Package p = FacesContext.class.getPackage();

		if (logger.isDebugEnabled()) {
			logger.debug(p.getImplementationTitle() + "	version  ("
					+ p.getImplementationVersion() + "");
		}
		if (currentContest == null) {

			currentContest = (Contest) getFromFlash("contest");
			if (currentContest == null) {

				currentContest = new Contest();
			}
		}

		allContestTypes = ContestType.getAllContestTypes();

	}

	public List<Contest> getAllInternalContests() {
		List<Contest> internalContests = new ArrayList<Contest>();
		Package p = FacesContext.class.getPackage();

		if (logger.isDebugEnabled()) {
			logger.debug(p.getImplementationTitle() + "	version  ("
					+ p.getImplementationVersion() + "");
		}
		internalContests = contestDAO.getAllContestsByType(ContestType.INTERNAL, 0,false);
		return internalContests;

	}

	public List<Contest> getAllExternalContests() {
		List<Contest> externalContests = new ArrayList<Contest>();

		externalContests = contestDAO.getAllContestsByType(
				ContestType.EXTERNAL, 0,false);
		return externalContests;

	}

	public UserSessionController getUserSessionController() {
		return userSessionController;
	}

	public void setUserSessionController(
			UserSessionController userSessionController) {
		this.userSessionController = userSessionController;
	}

	private Contest ContesttoDelete;

	public String filePath;

	private List<Contest> listOfAllContests;

	private UIInput contestName;

	public Contest getCurrentContest() {
		return currentContest;
	}

	public void setCurrentContest(Contest currentContest) {
		this.currentContest = currentContest;
	}

	public boolean isCanSubmitContest() {
		return canSubmitContest;
	}

	public void setCanSubmitContest(boolean canSubmitContest) {
		this.canSubmitContest = canSubmitContest;
	}

	public boolean isCanEditDesc() {
		return canEditDesc;
	}

	public void setCanEditDesc(boolean canEditDesc) {
		this.canEditDesc = canEditDesc;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public Contest getContesttoDelete() {
		return ContesttoDelete;
	}

	public void setContesttoDelete(Contest contesttoDelete) {
		ContesttoDelete = contesttoDelete;
	}

	public List<Contest> getListOfAllContests() {
		return listOfAllContests;
	}

	public void setListOfAllContests(List<Contest> listOfAllContests) {
		this.listOfAllContests = listOfAllContests;
	}

	public UIInput getContestName() {
		return contestName;
	}

	public void setContestName(UIInput contestName) {
		this.contestName = contestName;
	}

	public UIInput getContestDescription() {
		return contestDescription;
	}

	public void setContestDescription(UIInput contestDescription) {
		this.contestDescription = contestDescription;
	}

	public boolean isContestAddedSuccessfully() {
		return contestAddedSuccessfully;
	}

	public void setContestAddedSuccessfully(boolean contestAddedSuccessfully) {
		this.contestAddedSuccessfully = contestAddedSuccessfully;
	}

	public Idea getOldContest() {
		return oldContest;
	}

	public void setOldContest(Idea oldContest) {
		this.oldContest = oldContest;
	}

	private UIInput contestDescription;

	private boolean contestAddedSuccessfully;

	private Idea oldContest;
	private int fileSize;

	public ContestController() {

	}

	public String creatContest() {
		if (currentContest != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Creating Contest .....");
			}
			currentContest.setContestTypeID(currentContestType);
			contestDAO.addEntity(currentContest);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Contest Created !!");
		}
		return "/admin/contest/contests.xhtml?faces-redirect=true";
	}

	public void listener(FileUploadEvent event) throws Exception {
		UploadedFile item = event.getUploadedFile();

		String fileName = item.getName();
		if (fileName != null) {
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		}

		boolean error = false;
		if (item.getSize() > 1024 * 1024 * 30) {
			JSFUtils.displayErrorMessage("idea.contender.filesize.error");
			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage(JSFUtils.getMessage("idea.contender.filesize.error")));
			error = true;
		}

		if (!error) {
			String storePath = SettingCashManager.getIistance()
					.getSettingValue("IDEA_PATH");

			File userFolder = new File(storePath + File.separator + "Admin");
			// if file doesn't exists, then create it
			if (!userFolder.exists()) {
				userFolder.mkdir();
			}

			File file = new File(storePath + File.separator + "Admin"
					+ File.separator + fileName);

			if (!file.exists()) {
				file.createNewFile();
				if (logger.isDebugEnabled()) {
					logger.debug(file.getAbsolutePath());
				}
			}

			FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
			currentContest.setImage(file.getAbsolutePath());
			fos.write(item.getData());
			fos.close();

		}
	}

	public String editContest() {
		
		// JSFUtils.sendToFlash("contest", contestDAO.getContestById(id));
		return "/admin/contest/addcontest.xhtml?faces-redirect=true";

	}

	public String updateContest() {
		if (currentContest != null) {
			currentContest.setContestTypeID(currentContestType);
			currentContest.setUpdateDate(new Date());

			contestDAO.updateEntity(currentContest);

		}
		if (logger.isDebugEnabled()) {
			logger.debug("Contest Updated !!");
		}
		return "/admin/contest/contests.xhtml?faces-redirect=true";
	}

	public String addChallenge() {

		JSFUtils.sendToFlash("editableContest", currentContest);
		return "/admin/challenge/addchallenge.xhtml?faces-redirect=true";

	}

}
