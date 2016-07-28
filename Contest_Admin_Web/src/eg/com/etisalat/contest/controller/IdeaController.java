package eg.com.etisalat.contest.controller;

import static eg.com.etisalat.jsf.utility.JSFUtils.sendToFlash;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import eg.com.etisalat.contest.dao.ChallengeDAO;
import eg.com.etisalat.contest.dao.IdeaBlogDAO;
import eg.com.etisalat.contest.dao.IdeaDAO;
import eg.com.etisalat.contest.dao.UserDAO;
import eg.com.etisalat.contest.domain.Challenge;
import eg.com.etisalat.contest.domain.DiscussionThread;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaStatus;
import eg.com.etisalat.contest.domain.User;
import eg.com.etisalat.contest.utility.BulkMailThread;
import eg.com.etisalat.contest.utility.CommonUtility;
import eg.com.etisalat.contest.utility.FileModel;
import eg.com.etisalat.contest.utility.MailThread;
import eg.com.etisalat.contest.utility.SettingCashManager;
import eg.com.etisalat.contest.utility.SettingsKeys;
import eg.com.etisalat.jsf.utility.JSFUtils;

@ManagedBean
@ViewScoped
public class IdeaController implements Serializable {

	@Inject
	private IdeaDAO ideaDAO;

	@Inject
	private ChallengeDAO challengeDAO;

	@Inject
	private UserDAO userDAO;
	
	@Inject
	private Logger logger;

	private Idea currentIdea;

	private boolean canSubmitIdea;

	private boolean canSubmitFile;

	private User currentUser;

	private List<Challenge> listOfChallengies;

	private List<FileModel> listOfFilesForCurrentIdea_STG1 = new ArrayList<FileModel>();
	private List<FileModel> listOfFilesForCurrentIdea_STG3 = new ArrayList<FileModel>();

	private int maximumFilesForStage;

	private String extensionsForStage;

	@ManagedProperty(value = "#{userSessionController}")
	UserSessionController userSessionController;

	private Idea ideaToDelete;

	public String filePath;

	private List<Idea> listOfAllIdeas;

	private String statusFilter;

	private String categoryFilter;

	private String userNameFilter;

	private String ideaNameFilter;

	private String currentUpdateComment;

	private List<IdeaStatus> allIdeaStatus;
	private UIInput ideaName;
	private UIInput ideaDescription;

	private boolean ideaAddedSuccessfully;

	public String createBlog() {
		sendToFlash("attachedIdea", currentIdea);
		return "createBlog?faces-redirect=true";

	}

	public void deleteFileIdea() {
		if (logger.isDebugEnabled()){
			logger.debug("delete idea ("+currentIdea+") started");
		}
		boolean deletedFromDB = false;
		if (currentIdea.getLink1() != null && currentIdea.getLink1().equals(filePath)) {
			currentIdea.setLink1(null);
			deletedFromDB = true;
			updateIdea(currentIdea);
		} else {
			if (currentIdea.getLink2() != null && currentIdea.getLink2().equals(filePath)) {
				currentIdea.setLink2(null);
				deletedFromDB = true;
				updateIdea(currentIdea);
			} else {
				if (currentIdea.getLink3() != null && currentIdea.getLink3().equals(filePath)) {
					currentIdea.setLink3(null);
					deletedFromDB = true;
					updateIdea(currentIdea);
				} else {
					if (currentIdea.getLink4() != null && currentIdea.getLink4().equals(filePath)) {
						currentIdea.setLink4(null);
						deletedFromDB = true;
						updateIdea(currentIdea);
					} else {
						if (currentIdea.getLink5() != null && currentIdea.getLink5().equals(filePath)) {
							currentIdea.setLink5(null);
							deletedFromDB = true;
							updateIdea(currentIdea);
						}
					}
				}
			}
		}
		if (deletedFromDB) {
			File file = new File(filePath);
			file.delete();
		}
		if (logger.isDebugEnabled()){
			logger.debug("delete idea ("+currentIdea+") ended successfully");
		}

	}

	public void deleteIdea() throws ClassNotFoundException, IOException {
		Idea clonedIdea = currentIdea.getIdenticalCopy();
		if (ideaDAO.deleteEntity(ideaToDelete)) {
			if (clonedIdea.getLink1() != null) {
				File file = new File(clonedIdea.getLink1());
				file.delete();
			}
			if (clonedIdea.getLink2() != null) {
				File file = new File(clonedIdea.getLink2());
				file.delete();
			}
			if (clonedIdea.getLink3() != null) {
				File file = new File(clonedIdea.getLink3());
				file.delete();
			}
			if (clonedIdea.getLink4() != null) {
				File file = new File(clonedIdea.getLink4());
				file.delete();
			}
			if (clonedIdea.getLink5() != null) {
				File file = new File(clonedIdea.getLink5());
				file.delete();
			}
			listOfFilesForCurrentIdea_STG1.clear();
			listOfFilesForCurrentIdea_STG3.clear();
			currentIdea = null;
		}
	}

	public void deleteProvidedIdea(Idea idea) {
		ideaToDelete = idea;
	}

	public void downloadFile(String filePath) {
		try {
			File file = new File(filePath);
			InputStream fis = new FileInputStream(file);
			byte[] buf = new byte[(int) file.length()];
			int offset = 0;
			int numRead = 0;
			while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) {

				offset += numRead;

			}
			fis.close();
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			String mimeType = "octet-stream";
			if (ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")) {
				if (ext.equalsIgnoreCase("doc"))
					mimeType = "msword";
				else
					mimeType = "vnd.openxmlformats-officedocument.wordprocessingml.template";
			} else {
				if (ext.equalsIgnoreCase("ppt") || ext.equalsIgnoreCase("pptx")) {
					if (ext.equalsIgnoreCase("ppt"))
						mimeType = "vnd.ms-powerpoint";
					else
						mimeType = "vnd.openxmlformats-officedocument.presentationml.presentation";
				} else if (ext.equalsIgnoreCase("rar") || ext.equalsIgnoreCase("zip") || ext.equalsIgnoreCase("7z") || ext.equalsIgnoreCase("tar")) {
					mimeType = "x-zip-compressed";

				} else if (ext.equalsIgnoreCase("pdf")) {
					mimeType = "pdf";

				}
			}
			response.setContentType("application/" + mimeType);
			response.setHeader("Content-Disposition", "attachment;filename=" + file.getName() + "");
			response.getOutputStream().write(buf);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException ex) {
			logger.error(ex, ex);
		}
	}

	public void editIdea(Idea idea) {
		currentIdea = idea;
	}

	public List<IdeaStatus> getAllIdeaStatus() {
		return allIdeaStatus;
	}

	public String getCategoryFilter() {
		return categoryFilter;
	}

	public Idea getCurrentIdea() {
		return currentIdea;
	}

	public String getCurrentUpdateComment() {
		return currentUpdateComment;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public String getExtensionsForStage() {
		return extensionsForStage;
	}

	public String getFilePath() {
		return filePath;
	}

	public UIInput getIdeaDescription() {
		return ideaDescription;
	}

	public UIInput getIdeaName() {
		return ideaName;
	}

	public String getIdeaNameFilter() {
		return ideaNameFilter;
	}

	public Idea getIdeaToDelete() {
		return ideaToDelete;
	}

	public List<Idea> getListOfAllIdeas() {
		return listOfAllIdeas;
	}

	public List<Challenge> getlistOfChallengies() {
		return listOfChallengies;
	}

	public List<FileModel> getListOfFilesForCurrentIdea_STG1() {
		return listOfFilesForCurrentIdea_STG1;
	}

	public List<FileModel> getListOfFilesForCurrentIdea_STG3() {
		return listOfFilesForCurrentIdea_STG3;
	}

	public String getLocalizedMessageForDescription(IdeaStatus ideaStatus) {
		return JSFUtils.getEntityLoalizedDescription(ideaStatus);
	}

	public int getMaximumFilesForStage() {
		return maximumFilesForStage;
	}

	public String getStatusFilter() {
		return statusFilter;
	}

	public String getUserNameFilter() {
		return userNameFilter;
	}

	public UserSessionController getUserSessionController() {
		return userSessionController;
	}

	public boolean isCanSubmitFile() {
		return canSubmitFile;
	}

	public boolean isCanSubmitIdea() {
		return canSubmitIdea;
	}

	public boolean isIdeaAddedSuccessfully() {
		return ideaAddedSuccessfully;
	}

	public boolean isRejected() {
		return getCurrentIdea().isRejected();
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
		for (int i = 0; i < listOfFilesForCurrentIdea_STG1.size(); i++) {
			if (listOfFilesForCurrentIdea_STG1.get(i).getFileName().equals(fileName)) {
				JSFUtils.displayErrorMessage("idea.contender.rename");
				// FacesContext.getCurrentInstance().addMessage(null, new
				// FacesMessage(JSFUtils.getMessage("idea.contender.rename")));
				error = true;
			}
		}
		for (int i = 0; i < listOfFilesForCurrentIdea_STG3.size(); i++) {
			if (listOfFilesForCurrentIdea_STG3.get(i).getFileName().equals(fileName)) {
				JSFUtils.displayErrorMessage("idea.contender.rename");
				// FacesContext.getCurrentInstance().addMessage(null, new
				// FacesMessage(JSFUtils.getMessage("idea.contender.rename")));
				error = true;
			}
		}
		if (!error) {
			String storePath = SettingCashManager.getIistance().getSettingValue("IDEA_PATH");

			File userFolder = new File(storePath + File.separator + userSessionController.getCurrentUser().getUserId());
			// if file doesn't exists, then create it
			if (!userFolder.exists()) {
				userFolder.mkdir();
			}

			File file = new File(storePath + File.separator + userSessionController.getCurrentUser().getUserId() + File.separator + fileName);

			if (!file.exists()) {
				file.createNewFile();
				if (logger.isDebugEnabled()){
					logger.debug(file.getAbsolutePath());
				}
			}

			FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
			fos.write(item.getData());
			fos.close();
			if (userSessionController.isCurrentStage1()) {
				if (currentIdea.getLink1() == null) {
					currentIdea.setLink1(file.getAbsolutePath());
					listOfFilesForCurrentIdea_STG1.add(new FileModel(fileName, currentIdea.getLink1()));
				} else {
					if (currentIdea.getLink2() == null) {
						currentIdea.setLink2(file.getAbsolutePath());
						listOfFilesForCurrentIdea_STG1.add(new FileModel(fileName, currentIdea.getLink2()));
					} else {
						if (currentIdea.getLink3() == null) {
							currentIdea.setLink3(file.getAbsolutePath());
							listOfFilesForCurrentIdea_STG1.add(new FileModel(fileName, currentIdea.getLink3()));
						}
					}
				}
				canSubmitFile = listOfFilesForCurrentIdea_STG1.size() < 3;
			} else {
				if (userSessionController.isCurrentStage3()) {
					if (currentIdea.getLink4() == null) {
						currentIdea.setLink4(file.getAbsolutePath());
						listOfFilesForCurrentIdea_STG3.add(new FileModel(fileName, currentIdea.getLink4()));
					} else {
						if (currentIdea.getLink5() == null) {
							currentIdea.setLink5(file.getAbsolutePath());
							listOfFilesForCurrentIdea_STG3.add(new FileModel(fileName, currentIdea.getLink5()));
						}
					}
				}
				canSubmitFile = listOfFilesForCurrentIdea_STG3.size() < 2;
			}
			updateIdea(currentIdea);
		}
	}

	public void loadCurrentIdea(Idea idea) {
		listOfFilesForCurrentIdea_STG1.clear();
		listOfFilesForCurrentIdea_STG3.clear();
		currentIdea = idea;
		if (currentIdea.getLink1() != null) {
			listOfFilesForCurrentIdea_STG1.add(new FileModel(currentIdea.getLink1().substring(currentIdea.getLink1().lastIndexOf(File.separator) + 1),
					currentIdea.getLink1()));
		}
		if (currentIdea.getLink2() != null) {
			listOfFilesForCurrentIdea_STG1.add(new FileModel(currentIdea.getLink2().substring(currentIdea.getLink2().lastIndexOf(File.separator) + 1),
					currentIdea.getLink2()));
		}
		if (currentIdea.getLink3() != null) {
			listOfFilesForCurrentIdea_STG1.add(new FileModel(currentIdea.getLink3().substring(currentIdea.getLink3().lastIndexOf(File.separator) + 1),
					currentIdea.getLink3()));
		}
		if (currentIdea.getLink4() != null) {
			listOfFilesForCurrentIdea_STG1.add(new FileModel(currentIdea.getLink4().substring(currentIdea.getLink4().lastIndexOf(File.separator) + 1),
					currentIdea.getLink4()));
		}
		if (currentIdea.getLink5() != null) {
			listOfFilesForCurrentIdea_STG1.add(new FileModel(currentIdea.getLink5().substring(currentIdea.getLink5().lastIndexOf(File.separator) + 1),
					currentIdea.getLink5()));
		}

	}

	public void manageAttributes() {
		if (userSessionController.isCurrentStage1()) {
			setMaximumFilesForStage(3);
			setExtensionsForStage("doc, docx, ppt, pptx, pdf");
		}
		if (userSessionController.isCurrentStage3()) {
			setMaximumFilesForStage(2);
			setExtensionsForStage("zip, rar, 7z, tar");
		}
	}

	public void preRenderAllIdeasScreen(ComponentSystemEvent systemEvent) {
		setListOfAllIdeas(ideaDAO.getAllEntites(Idea.class));
		if (CommonUtility.isEmptyList(listOfChallengies)) {
			listOfChallengies = challengeDAO.getAllEntites(Challenge.class);
		}

		if (CommonUtility.isEmptyList(allIdeaStatus)) {
			allIdeaStatus = IdeaStatus.getAllIdeaStatus();
		}
	}

	public void rejectAllNonSubmittedIdeas() throws Exception {
		Date rejectionDate = ideaDAO.updateIdeasStatusByGivenStatus(IdeaStatus.REJECTED, IdeaStatus.SUBMITTED);
		List<Idea> rejectedIdeas = ideaDAO.getAllEntitesByUpdateDate(rejectionDate);
		String ideaStatusAr = JSFUtils.getEntityArabicDescription(IdeaStatus.REJECTED);

		new BulkMailThread(rejectedIdeas, ideaStatusAr).start();

	}

	public void resetIdea() {
		currentIdea = null;
		listOfFilesForCurrentIdea_STG1.clear();
		listOfFilesForCurrentIdea_STG3.clear();
	}

	public void sendIdea() {
		if (!currentIdea.isNew()) {
			updateIdea(currentIdea);
		} else {
			if (currentIdea.getIdeaName().equals("")) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter your idea name", null));
			} else {
				if (currentIdea.getIdeaDescription().equals("")) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter your idea description", null));
				} else {
					currentIdea.setIdeaStatus(IdeaStatus.SUBMITTED);
					currentIdea.setSubmitDate(new Date());
					currentIdea.setUser(currentUser); // Get User from session
					ideaDAO.addEntity(currentIdea);
				}

			}
		}
	}

	public void sendIdeaAndReset() {
		if (currentIdea.isNew()) {
			JSFUtils.displayInfoMessage("idea.contender.idea.success");
			sendIdea();
			ideaAddedSuccessfully = true;
		} else {
			sendIdea();
		}
		resetIdea();
	}

	public void setAllIdeaStatus(List<IdeaStatus> allIdeaStatus) {
		this.allIdeaStatus = allIdeaStatus;
	}

	public void setCanSubmitFile(boolean canSubmitFile) {
		this.canSubmitFile = canSubmitFile;
	}

	public void setCanSubmitIdea(boolean canSubmitIdea) {
		this.canSubmitIdea = canSubmitIdea;
	}

	public void setCategoryFilter(String categoryFilter) {
		this.categoryFilter = categoryFilter;
	}

	public void setCurrentIdea(Idea currentIdea) {
		this.currentIdea = currentIdea;
	}

	public void setCurrentUpdateComment(String currentUpdateComment) {
		this.currentUpdateComment = currentUpdateComment;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void setExtensionsForStage(String extensionsForStage) {
		this.extensionsForStage = extensionsForStage;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setIdeaAddedSuccessfully(boolean ideaAddedSuccessfully) {
		this.ideaAddedSuccessfully = ideaAddedSuccessfully;
	}

	public void setIdeaDescription(UIInput ideaDescription) {
		this.ideaDescription = ideaDescription;
	}

	public void setIdeaName(UIInput ideaName) {
		this.ideaName = ideaName;
	}

	public void setIdeaNameFilter(String ideaNameFilter) {
		this.ideaNameFilter = ideaNameFilter;
	}

	public void setIdeaToDelete(Idea ideaToDelete) {
		this.ideaToDelete = ideaToDelete;
	}

	public void setListOfAllIdeas(List<Idea> listOfAllIdeas) {
		this.listOfAllIdeas = listOfAllIdeas;
	}

	public void setlistOfChallengies(List<Challenge> listOfChallengies) {
		this.listOfChallengies = listOfChallengies;
	}

	public void setListOfFilesForCurrentIdea_STG1(List<FileModel> listOfFilesForCurrentIdea_STG1) {
		this.listOfFilesForCurrentIdea_STG1 = listOfFilesForCurrentIdea_STG1;
	}

	public void setListOfFilesForCurrentIdea_STG3(List<FileModel> listOfFilesForCurrentIdea_STG3) {
		this.listOfFilesForCurrentIdea_STG3 = listOfFilesForCurrentIdea_STG3;
	}

	public void setMaximumFilesForStage(int maximumFilesForStage) {
		this.maximumFilesForStage = maximumFilesForStage;
	}

	public void setStatusFilter(String statusFilter) {
		this.statusFilter = statusFilter;
	}

	public void setUserNameFilter(String userNameFilter) {
		this.userNameFilter = userNameFilter;
	}

	public void setUserSessionController(UserSessionController userSessionController) {
		this.userSessionController = userSessionController;
	}

	public void updateIdeaAfterStatusChange() {
		currentIdea.setUpdateDate(new Date());
		ideaDAO.updateEntity(currentIdea);
		String ideaTemplate = SettingCashManager.getIistance().getSettingValue(SettingsKeys.CHANGE_IDEA_STATUS_TEMPLATE);
		String email = currentIdea.getUser().getEmail();
		List<String> ccMails = currentIdea.getUser().getFormattedGroupEmails();
		String firstName = currentIdea.getUser().getFirstName();
		String ideaStatus =currentIdea.getIdeaStatus().getDescription();
		//String ideaStatusAr = JSFUtils.getEntityArabicDescription(currentIdea.getIdeaStatus());
		String ideaName = currentIdea.getIdeaName();

		new MailThread(ideaTemplate, email, ccMails, firstName, ideaStatus, ideaStatus, ideaName, currentUpdateComment).start();

		if (currentIdea.getUser().getDiscussionThread() == null && currentIdea.getIdeaStatus().equals(IdeaStatus.SHORT_LISTED)) {
//			currentIdea.getUser().setDiscussionThread(new DiscussionThread(currentIdea.getUser()));
			userDAO.updateEntity(currentIdea.getUser());
		}
		currentIdea = null;
		currentUpdateComment = "";
		listOfFilesForCurrentIdea_STG1.clear();
		listOfFilesForCurrentIdea_STG3.clear();
	}

	private void updateIdea(Idea ideaToUpdate) {
		ideaToUpdate.setUpdateDate(new Date());
		ideaDAO.updateEntity(ideaToUpdate);
		// currentIdea = new Idea();
		listOfFilesForCurrentIdea_STG1.clear();
		listOfFilesForCurrentIdea_STG3.clear();
	}

}
