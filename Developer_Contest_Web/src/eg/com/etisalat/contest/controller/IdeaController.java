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
import eg.com.etisalat.contest.dao.ContestDAO;
import eg.com.etisalat.contest.dao.IdeaBlogDAO;
import eg.com.etisalat.contest.dao.IdeaDAO;
import eg.com.etisalat.contest.dao.RetreivingCriteria;
import eg.com.etisalat.contest.dao.UserDAO;
import eg.com.etisalat.contest.domain.Challenge;
import eg.com.etisalat.contest.domain.Contest;
import eg.com.etisalat.contest.domain.ContestType;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaBlog;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int IDEAS_MAXIMUM_NUMBER_PER_USER = 10;

	public static int getIdeasMaximumNumberPerUser() {
		return IDEAS_MAXIMUM_NUMBER_PER_USER;
	}

	@Inject
	private IdeaDAO ideaDAO;

	@Inject
	private ContestDAO contesDAO;
	@Inject
	private ChallengeDAO challengeDAO;

	@Inject
	private UserDAO userDAO;

	@Inject
	IdeaBlogDAO ideaBlogDao;

	@Inject
	private Logger logger;

	private Idea currentIdea;

	private boolean canSubmitIdea;
	private int Privacy;

	private boolean canSubmitFile;

	private boolean canEditDesc;

	private User currentUser;

	private List<Challenge> listOfChallengies;

	private List<Challenge> listOfOpenChallengies;

	private List<FileModel> listOfFilesForCurrentIdea_STG1 = new ArrayList<FileModel>();

	private List<FileModel> listOfFilesForCurrentIdea_STG3 = new ArrayList<FileModel>();

	private int maximumFilesForStage;

	private String extensionsForStage;

	@ManagedProperty(value = "#{userSessionController}")
	private UserSessionController userSessionController;

	private List<Contest> listOfContests;
	private Idea ideaToDelete;

	private Contest slectedContest;

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

	private Idea oldIdea;

	private int fileSize;

	private int isOpenChallenge;

	private IdeaBlog currentIdeaBlog;

	public IdeaController() {

	}

	public String createBlog() {
		sendToFlash("attachedIdea", currentIdea);
		return "createBlog?faces-redirect=true";

	}

	public void deleteFileIdea() {
		if (logger.isDebugEnabled()) {
			logger.debug("delete idea (" + currentIdea + ") started");
		}
		boolean deletedFromDB = false;
		if (currentIdea.getLink1() != null && currentIdea.getLink1().equals(filePath)) {
			currentIdea.setLink1(null);
			deletedFromDB = true;
			updateIdea(currentIdea, oldIdea);
		} else {
			if (currentIdea.getLink2() != null && currentIdea.getLink2().equals(filePath)) {
				currentIdea.setLink2(null);
				deletedFromDB = true;
				updateIdea(currentIdea, oldIdea);
			} else {
				if (currentIdea.getLink3() != null && currentIdea.getLink3().equals(filePath)) {
					currentIdea.setLink3(null);
					deletedFromDB = true;
					updateIdea(currentIdea, oldIdea);
				} else {
					if (currentIdea.getLink4() != null && currentIdea.getLink4().equals(filePath)) {
						currentIdea.setLink4(null);
						deletedFromDB = true;
						updateIdea(currentIdea, oldIdea);
					} else {
						if (currentIdea.getLink5() != null && currentIdea.getLink5().equals(filePath)) {
							currentIdea.setLink5(null);
							deletedFromDB = true;
							updateIdea(currentIdea, oldIdea);
						}
					}
				}
			}
		}
		if (deletedFromDB) {
			File file = new File(filePath);
			file.delete();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("delete idea (" + currentIdea + ") ended successfully");
		}

	}

	public void deleteIdea() throws ClassNotFoundException, IOException {
		// if (ideaToDelete.getIdeaBlog() != null)
		// ideaBlogDAO.deleteEntity(ideaToDelete.getIdeaBlog());
		Idea clonedIdea = ideaToDelete.getIdenticalCopy();
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

	/**
	 * @return the
	 */
	public int getFileSize() {
		return fileSize;
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

	public int getIsOpenChallenge() {
		return isOpenChallenge;
	}

	public List<Idea> getListOfAllIdeas() {
		return listOfAllIdeas;
	}

	public List<Challenge> getListOfChallengies() {
		return listOfChallengies;
	}

	public List<Contest> getListOfContests() {
		return listOfContests;
	}

	public List<FileModel> getListOfFilesForCurrentIdea_STG1() {
		return listOfFilesForCurrentIdea_STG1;
	}

	public List<FileModel> getListOfFilesForCurrentIdea_STG3() {
		return listOfFilesForCurrentIdea_STG3;
	}

	public List<Challenge> getListOfOpenChallengies() {
		return listOfOpenChallengies;
	}

	public String getLocalizedMessageForDescription(IdeaStatus ideaStatus) {
		return ideaStatus.getDescription();
	}

	public int getMaximumFilesForStage() {
		return maximumFilesForStage;
	}

	public int getPrivacy() {
		return Privacy;
	}

	public Contest getSlectedContest() {
		return slectedContest;
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

	public boolean isCanEditDesc() {
		return canEditDesc;
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

		if (item.getSize() > fileSize) {
			JSFUtils.displayErrorMessage("idea.contender.filesize.error");
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

			File ideaFolder = new File(storePath + File.separator + userSessionController.getCurrentUser().getUserId() + File.separator
					+ currentIdea.getIdeaId());
			if (!ideaFolder.exists()) {
				ideaFolder.mkdir();
			}

			File file = new File(storePath + File.separator + userSessionController.getCurrentUser().getUserId() + File.separator + currentIdea.getIdeaId()
					+ File.separator + fileName);

			if (!file.exists()) {
				file.createNewFile();
				if (logger.isDebugEnabled()) {
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
				canSubmitFile = listOfFilesForCurrentIdea_STG3.size() + listOfFilesForCurrentIdea_STG1.size() < 1;
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
				canSubmitFile = listOfFilesForCurrentIdea_STG1.size() + listOfFilesForCurrentIdea_STG3.size() < 1;
			}
			if (!updateIdea(currentIdea, oldIdea)) {
				File fileToDelete = new File(file.getAbsolutePath());
				fileToDelete.delete();
			}
		}
	}

	public void loadChalengesOfContest() {
		if (slectedContest != null)
			listOfChallengies = slectedContest.getChallenges();

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
			setMaximumFilesForStage(1);
			setExtensionsForStage("doc, docx, ppt, pptx, pdf,mp4,mov,avi,mp3");
			fileSize = 1024 * 1024 * 10;
		}
		if (userSessionController.isCurrentStage3()) {
			setMaximumFilesForStage(1);
			setExtensionsForStage("doc, docx, ppt, pptx, pdf,mp4,mov,avi,mp3");
			fileSize = 1024 * 1024 * 10;
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

	/*
	 * Test 1. get All user Ideas and set currentUser Ideas 2. set canSubmitIdea
	 * 3. initiate currentIdea if eligible
	 */
	public void preRenderIdeaScreen(ComponentSystemEvent systemEvent) {
		if (currentIdea == null) {
			setCanEditDesc(false);
		} else {
			if (userSessionController.getCurrentStage().getStageId() == 1
					|| (userSessionController.getCurrentStage().getStageId() == 3 && currentIdea.isShortlisted()))
				setCanEditDesc(false);
			else
				setCanEditDesc(true);
		}

		manageAttributes();
		setCurrentUser(userSessionController.getCurrentUser());
		if (currentUser != null) {
			List<Idea> listOfIdeas = ideaDAO.getAllIdeasForUser(currentUser, RetreivingCriteria.RECENT, 0);
			currentUser.setIdeas(listOfIdeas);

			if (CommonUtility.isEmptyList(listOfContests)) {
				// if
				// (!currentUser.getEmail().toLowerCase().contains("@etisalat"))
				// listOfContests =
				// contesDAO.getAllContestsByType(ContestType.EXTERNAL, 0,
				// true);
				// else
				listOfContests = contesDAO.getAllEntites(Contest.class);

			}
			if (CommonUtility.isEmptyList(listOfOpenChallengies)) {
				// if
				// (!currentUser.getEmail().toLowerCase().contains("@etisalat"))
				// listOfOpenChallengies = challengeDAO.getAllOpenChallenges(0,
				// ContestType.EXTERNAL);
				// else
				listOfOpenChallengies = challengeDAO.getAllOpenChallenges(0, ContestType.ALL);

			}

			// add new idea
			if (currentIdea == null) {
				if ((listOfIdeas == null || listOfIdeas.size() < IDEAS_MAXIMUM_NUMBER_PER_USER) && userSessionController.isCurrentStage1()) {
					currentIdea = new Idea();
					canSubmitFile = true;
					canSubmitIdea = true;
				} else {
					canSubmitIdea = false;
				}
			} else {
				canSubmitIdea = true;
				listOfFilesForCurrentIdea_STG1.clear();
				listOfFilesForCurrentIdea_STG3.clear();

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
					listOfFilesForCurrentIdea_STG3.add(new FileModel(currentIdea.getLink4().substring(currentIdea.getLink4().lastIndexOf(File.separator) + 1),
							currentIdea.getLink4()));
				}
				if (currentIdea.getLink5() != null) {
					listOfFilesForCurrentIdea_STG3.add(new FileModel(currentIdea.getLink5().substring(currentIdea.getLink5().lastIndexOf(File.separator) + 1),
							currentIdea.getLink5()));
				}

				if (userSessionController.isCurrentStage1()) {
					canSubmitFile = listOfFilesForCurrentIdea_STG1.size() < 1;
				} else {
					if (userSessionController.isCurrentStage3()) {
						canSubmitFile = listOfFilesForCurrentIdea_STG1.size() + listOfFilesForCurrentIdea_STG3.size() < 1
								&& currentIdea.getIdeaStatus().equals(IdeaStatus.SHORT_LISTED);
					}
				}
			}

			try {
				if (currentIdea != null)
					oldIdea = currentIdea.getIdenticalCopy();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
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
			updateIdea(currentIdea, oldIdea);
		} else {

			currentIdea.setIdeaStatus(IdeaStatus.SUBMITTED);
			currentIdea.setSubmitDate(new Date());
			currentIdea.setUser(currentUser); // Get User from session
			ideaDAO.addEntity(currentIdea);
			if (Privacy == 1) {
				currentIdeaBlog = new IdeaBlog();
				currentIdeaBlog.setBlogTitle(currentIdea.getIdeaName());
				currentIdeaBlog.setBlogText(currentIdea.getIdeaDescription());
				currentIdeaBlog.setIdea(currentIdea);

				currentIdea.setIdeaBlog(currentIdeaBlog);
				ideaBlogDao.addEntity(currentIdeaBlog);
				currentIdea = ideaDAO.updateEntity(currentIdea);
				currentIdeaBlog = currentIdea.getIdeaBlog();
			}
		}
	}

	public String sendIdeaAndReset() {
		if (currentIdea.isNew()) {
			JSFUtils.displayInfoMessage("idea.contender.idea.success");
			sendIdea();
			ideaAddedSuccessfully = true;
		} else {
			sendIdea();
		}
		resetIdea();
		return "/user/userHome?faces-redirect=true";
	}

	public void setAllIdeaStatus(List<IdeaStatus> allIdeaStatus) {
		this.allIdeaStatus = allIdeaStatus;
	}

	public void setCanEditDesc(boolean canEditDesc) {
		this.canEditDesc = canEditDesc;
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

	/**
	 * @param fileSize
	 *            the fileSize to set
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
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

	public void setIsOpenChallenge(int isOpenChallenge) {
		this.isOpenChallenge = isOpenChallenge;
	}

	public void setListOfAllIdeas(List<Idea> listOfAllIdeas) {
		this.listOfAllIdeas = listOfAllIdeas;
	}

	public void setListOfChallengies(List<Challenge> listOfChallengies) {
		this.listOfChallengies = listOfChallengies;
	}

	public void setListOfContests(List<Contest> listOfContests) {
		this.listOfContests = listOfContests;
	}

	public void setListOfFilesForCurrentIdea_STG1(List<FileModel> listOfFilesForCurrentIdea_STG1) {
		this.listOfFilesForCurrentIdea_STG1 = listOfFilesForCurrentIdea_STG1;
	}

	public void setListOfFilesForCurrentIdea_STG3(List<FileModel> listOfFilesForCurrentIdea_STG3) {
		this.listOfFilesForCurrentIdea_STG3 = listOfFilesForCurrentIdea_STG3;
	}

	public void setListOfOpenChallengies(List<Challenge> listOfOpenChallengies) {
		this.listOfOpenChallengies = listOfOpenChallengies;
	}

	public void setMaximumFilesForStage(int maximumFilesForStage) {
		this.maximumFilesForStage = maximumFilesForStage;
	}

	public void setPrivacy(int privacy) {
		Privacy = privacy;
	}

	public void setSlectedContest(Contest slectedContest) {
		this.slectedContest = slectedContest;

		if (slectedContest != null) {

			listOfChallengies = slectedContest.getChallenges();
		}
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

	private boolean updateIdea(Idea ideaToUpdate, Idea oldIdea) {
		ideaToUpdate.setUpdateDate(new Date());
		listOfFilesForCurrentIdea_STG1.clear();
		listOfFilesForCurrentIdea_STG3.clear();
		return ideaDAO.updateEntity(ideaToUpdate, oldIdea);
	}

	public void updateIdeaAfterStatusChange() {
		currentIdea.setUpdateDate(new Date());
		ideaDAO.updateEntity(currentIdea);
		String ideaTemplate = SettingCashManager.getIistance().getSettingValue(SettingsKeys.CHANGE_IDEA_STATUS_TEMPLATE);
		String email = currentIdea.getUser().getEmail();
		List<String> ccMails = currentIdea.getUser().getFormattedGroupEmails();
		String firstName = currentIdea.getUser().getFirstName();
		String ideaStatus = JSFUtils.getEntityLoalizedDescription(currentIdea.getIdeaStatus());
		String ideaStatusAr = JSFUtils.getEntityArabicDescription(currentIdea.getIdeaStatus());
		String ideaName = currentIdea.getIdeaName();

		new MailThread(ideaTemplate, email, ccMails, firstName, ideaStatus, ideaStatusAr, ideaName, currentUpdateComment).start();

		if (currentIdea.getUser().getDiscussionThread() == null && currentIdea.getIdeaStatus().equals(IdeaStatus.SHORT_LISTED)) {
			// currentIdea.getUser().setDiscussionThread(new
			// DiscussionThread(currentIdea.getUser()));
			userDAO.updateEntity(currentIdea.getUser());
		}
		currentIdea = null;
		currentUpdateComment = "";
		listOfFilesForCurrentIdea_STG1.clear();
		listOfFilesForCurrentIdea_STG3.clear();
	}

}
