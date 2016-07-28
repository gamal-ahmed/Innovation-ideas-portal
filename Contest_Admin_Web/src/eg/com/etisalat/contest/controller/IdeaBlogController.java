package eg.com.etisalat.contest.controller;

import static eg.com.etisalat.contest.utility.CommonUtility.isEmptyList;
import static eg.com.etisalat.jsf.utility.JSFUtils.displayErrorMessage;
import static eg.com.etisalat.jsf.utility.JSFUtils.displayInfoMessage;
import static eg.com.etisalat.jsf.utility.JSFUtils.getFromFlash;
import static eg.com.etisalat.jsf.utility.JSFUtils.getFromSession;
import static eg.com.etisalat.jsf.utility.JSFUtils.sendToFlash;
import static eg.com.etisalat.jsf.utility.JSFUtils.sendToSession;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.richfaces.component.SortOrder;
import org.richfaces.model.Filter;

import eg.com.etisalat.contest.dao.BlogCommentDAO;
import eg.com.etisalat.contest.dao.ChallengeDAO;
import eg.com.etisalat.contest.dao.IdeaBlogDAO;
import eg.com.etisalat.contest.dao.IdeaDAO;
import eg.com.etisalat.contest.dao.VoteDAO;
import eg.com.etisalat.contest.domain.BlogComment;
import eg.com.etisalat.contest.domain.Challenge;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaBlog;
import eg.com.etisalat.contest.domain.IdeaStatus;
import eg.com.etisalat.contest.domain.Vote;
import eg.com.etisalat.jsf.utility.JSFUtils;

@ManagedBean
@ViewScoped
public class IdeaBlogController {

	@Inject
	private IdeaBlogDAO ideaBlogDao;
	@Inject
	private IdeaDAO ideaDao;
	@Inject
	private ChallengeDAO challengeDAO;
	@Inject
	private VoteDAO voteDAO;
	private List<IdeaBlog> ideaBlogs;

	private IdeaBlog currentIdeaBlog;
	private String statusFilter;
	private Challenge categoryFilter;
	private List<IdeaStatus> allIdeaStatus;

	private List<Challenge> allChalenges;

	private List<IdeaBlog> personalBlogs;
	private String nameFilter;
	private Idea currentIdea;
	private Vote currentVote;
	@Inject
	private BlogCommentDAO blogCommentDAO;
	private BlogComment currentBlogCommemt;
	private List<BlogComment> commentsList;

	@ManagedProperty(value = "#{userSessionController}")
	private UserSessionController userSessionController;

	private String activeTab;

	private boolean allCommentsEnable = false;

	public static final int MAX_INITIAL_RESULTS = 5;
	private Object sortOrderNeg = SortOrder.unsorted;
	private Object sortOrderComm = SortOrder.unsorted;

	private Object sortOrderPos = SortOrder.unsorted;

	public void activateTab(String tabName) {
		activeTab = tabName;
	}

	public String addNewBlog() {

		if (currentIdea == null) {
			displayErrorMessage("blog.manageBlog.message.noIdeaAttached");
			return null;
		}

		if (isEmptyList(personalBlogs)) {
			personalBlogs = ideaBlogDao.getBlogsByUser(userSessionController.getCurrentUser());
		}
		if (!isEmptyList(personalBlogs)) {
			Iterator<IdeaBlog> iterator = personalBlogs.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().getBlogTitle().equalsIgnoreCase(currentIdeaBlog.getBlogTitle())) {
					displayErrorMessage("blog.manageBlog.message.repeatedName");
					return null;
				}
			}
		}

		currentIdeaBlog.setIdea(currentIdea);
		currentIdea.setIdeaBlog(currentIdeaBlog);
		// ideaBlogDao.addEntity(currentIdeaBlog);
		currentIdea = ideaDao.updateEntity(currentIdea);
		currentIdeaBlog = currentIdea.getIdeaBlog();
		displayInfoMessage("blog.manageBlog.message.blogDeleted", currentIdeaBlog.getBlogTitle());
		currentIdeaBlog = new IdeaBlog();
		currentIdea = null;
		return "developers?faces-redirect=true";
	}

	public void addNewComment() {
		if (currentBlogCommemt == null) {
			currentBlogCommemt = new BlogComment(userSessionController.getCurrentUser(), currentIdeaBlog);
		}
		blogCommentDAO.addEntity(currentBlogCommemt);
		loadMostRecentComments();
		currentIdeaBlog.setTotalComments(currentIdeaBlog.getTotalComments() + 1);
		currentBlogCommemt = null;

	}

	public void changeCurrentBlog(ValueChangeEvent event) {
		allCommentsEnable = false;
		currentBlogCommemt.setIdeaBlog(currentIdeaBlog);
	}

	public void deleteBlogs() {
		// currentIdeaBlog.setIdea(null);
		ideaBlogDao.deleteEntity(currentIdeaBlog);
		ideaBlogs = ideaBlogDao.getAllEntites(IdeaBlog.class);
		personalBlogs = ideaBlogDao.getBlogsByUser(userSessionController.getCurrentUser());
		if (!isEmptyList(personalBlogs)) {
			currentIdeaBlog = personalBlogs.get(0);
		}

		else {
			currentIdeaBlog = null;
		}

	}

	public void deleteComment() {
		blogCommentDAO.deleteEntity(currentBlogCommemt);
		currentIdeaBlog.setTotalComments(currentIdeaBlog.getTotalComments() - 1);
		currentBlogCommemt = null;
	}

	public String editBlog() {
		sendToFlash("blog", currentIdeaBlog);
		return "createBlog?faces-redirect=true";
	}

	/**
	 * @return the activeTab
	 */
	public String getActiveTab() {
		return activeTab;
	}

	public List<Challenge> getAllChalenges() {
		return allChalenges;
	}

	public List<IdeaStatus> getAllIdeaStatus() {
		return allIdeaStatus;
	}

	public int getBlogTotalVotes() {

		int votes = currentIdeaBlog.getVotes().size();
		if (currentIdeaBlog != null && votes > 0) {
			return (currentIdeaBlog.getPositiveRating() / votes);
		} else {
			return 0;
		}
	}

	/**
	 * @return the categoryFilter
	 */
	public Challenge getCategoryFilter() {
		return categoryFilter;
	}

	public List<BlogComment> getCommentsList() {
		return commentsList;
	}

	public BlogComment getCurrentBlogCommemt() {
		return currentBlogCommemt;
	}

	public Idea getCurrentIdea() {
		return currentIdea;
	}

	public IdeaBlog getCurrentIdeaBlog() {
		return currentIdeaBlog;
	}

	/**
	 * @return the currentVote
	 */
	public Vote getCurrentVote() {
		return currentVote;
	}

	public Filter<?> getFilterCategory() {
		return new Filter<IdeaBlog>() {
			@Override
			public boolean accept(IdeaBlog b) {
				String category = getCategoryFilter().getLocalizedDescription(userSessionController.getSiteLanguage());
				if (category == null || category.length() == 0
						|| category.equals(b.getIdea().getChallenge().getLocalizedDescription(userSessionController.getSiteLanguage()))) {
					return true;
				}
				return false;
			}
		};
	}

	public Filter<?> getFilterStatus() {
		return new Filter<IdeaBlog>() {
			@Override
			public boolean accept(IdeaBlog b) {
				String status = getStatusFilter();
				if (status == null || status.length() == 0 || status.equals(b.getIdea().getIdeaStatus().getDescription())) {
					return true;
				}
				return false;
			}
		};
	}

	public List<IdeaBlog> getIdeaBlogs() {
		return ideaBlogs;
	}

	public String getLastUpdatedDate(Date updateDate) {

		return JSFUtils.getDateDifference(updateDate);

	}

	public String getLastUpdatedPeriod() {
		return JSFUtils.getDateDifference(currentIdeaBlog.getLastUpdateDate());
	}

	public String getNameFilter() {
		return nameFilter;
	}

	/**
	 * @return the personalBlogs
	 */
	public List<IdeaBlog> getPersonalBlogs() {
		return personalBlogs;
	}

	public Object getSortOrderComm() {
		return sortOrderComm;
	}

	public Object getSortOrderNeg() {
		return sortOrderNeg;
	}

	public Object getSortOrderPos() {
		return sortOrderPos;
	}

	public String getStatusFilter() {
		return statusFilter;
	}

	public UserSessionController getUserSessionController() {
		return userSessionController;
	}

	public void initiateBlog(ComponentSystemEvent systemEvent) {
		if (currentIdeaBlog == null) {
			currentIdeaBlog = (IdeaBlog) getFromFlash("blog");
			allCommentsEnable = false;
			if (currentIdeaBlog != null) {
				currentIdeaBlog = ideaBlogDao.getFullIdeaBlog(currentIdeaBlog);
			} else {
				currentIdeaBlog = new IdeaBlog();
			}
		}

		if (currentIdea == null) {
			currentIdea = (Idea) getFromFlash("attachedIdea");

		}
	}

	public void initiateComment(ComponentSystemEvent systemEvent) {
		if (currentBlogCommemt == null) {
			currentBlogCommemt = new BlogComment(userSessionController.getCurrentUser(), currentIdeaBlog);
		}

		if (currentBlogCommemt != null) {
			currentBlogCommemt.setIdeaBlog(currentIdeaBlog);
		}
		if (currentIdeaBlog != null) {
			loadMostRecentComments();
		}
	}

	/**
	 * @return the allCommentsEnable
	 */
	public boolean isAllCommentsEnable() {
		return allCommentsEnable;
	}

	public void loadAllBlogs() {

		if (isEmptyList(ideaBlogs)) {
			if (isEmptyList(allIdeaStatus)) {
				loadAllStatus();
			}
			if (isEmptyList(allChalenges)) {
				loadAllCategories();
			}
			ideaBlogs = ideaBlogDao.getAllEntites(IdeaBlog.class);
		}
	}
	
	public int loadFullIdeaBlog(IdeaBlog blog){		
		return ideaBlogDao.getFullIdeaBlog(blog).getVotes().size();	
	}
	
	public void loadAllComments() {
		commentsList = blogCommentDAO.getCommentsByBlog(currentIdeaBlog);
	}

	public void loadAllStatus() {
		allIdeaStatus = IdeaStatus.getAllIdeaStatus();
	}

	public void loadBlog(ComponentSystemEvent systemEvent) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		if (currentIdeaBlog == null) {
			currentIdeaBlog = (IdeaBlog) getFromFlash("blog");
			if (currentIdeaBlog == null) {
				// currentIdeaBlog = (IdeaBlog)
				// sessionMap.get("currentBlogsession");
				currentIdeaBlog = (IdeaBlog) getFromSession("currentBlogsession");
				// displayErrorMessage("blog.manageBlog.error.noBlogSelected");
			}
		}

		if (currentIdeaBlog != null) {
			currentIdeaBlog = ideaBlogDao.getFullIdeaBlog(currentIdeaBlog);
		}
		
		if (currentIdeaBlog != null) {
			// sessionMap.put("currentBlogsession", currentIdeaBlog);
			loadMostRecentComments();
			loadVote();
		}

	}

	public void loadPersonalBlogs() {
		if (isEmptyList(personalBlogs)) {
			personalBlogs = ideaBlogDao.getBlogsByUser(userSessionController.getCurrentUser());
			if (!isEmptyList(personalBlogs)) {
				currentIdeaBlog = personalBlogs.get(0);
			}
		}

	}

	public void negativeRate() {

		if (currentVote == null) {
			initiateVote();
			currentVote.setVoteValue(-1);
			if (ideaBlogDao.increaseNegativeRate(currentIdeaBlog)) {
				voteDAO.addEntity(currentVote);
				currentIdeaBlog.setNegativeRating(currentIdeaBlog.getNegativeRating() + 1);
			}
		}

		else if (currentVote.getVoteValue() == -1) {

		}

		else if (currentVote.getVoteValue() == 1) {
			currentVote.setVoteValue(-1);
			if (ideaBlogDao.switchToNegativeRate(currentIdeaBlog)) {
				voteDAO.updateEntity(currentVote);
				currentIdeaBlog.setPositiveRating(currentIdeaBlog.getPositiveRating() - 1);
				currentIdeaBlog.setNegativeRating(currentIdeaBlog.getNegativeRating() + 1);
			}
		}

	}

	public void positiveRate() {

		if (currentVote == null) {
			initiateVote();
			currentVote.setVoteValue(1);
//			if (ideaBlogDao.increasePositiveRate(currentIdeaBlog)) {
//				voteDAO.addEntity(currentVote);
//				currentIdeaBlog.setPositiveRating(currentIdeaBlog.getPositiveRating() + 1);
//			}
		}

		else if (currentVote.getVoteValue() == 1) {

		}

		else if (currentVote.getVoteValue() == -1) {
			currentVote.setVoteValue(1);
			if (ideaBlogDao.switchToPositiveRate(currentIdeaBlog)) {
				voteDAO.updateEntity(currentVote);
				currentIdeaBlog.setNegativeRating(currentIdeaBlog.getNegativeRating() - 1);
				currentIdeaBlog.setPositiveRating(currentIdeaBlog.getPositiveRating() + 1);
			}

		}

	}

	public void resetBlogVotes() {
		currentIdeaBlog.setPositiveRating(0);
		currentIdeaBlog.setNegativeRating(0);
		voteDAO.deleteAllBlogVotes(currentIdeaBlog);
	}

	/**
	 * @param activeTab
	 *            the activeTab to set
	 */
	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public void setAllCategories(List<Challenge> allCategories) {
		this.allChalenges = allCategories;
	}

	/**
	 * @param allCommentsEnable
	 *            the allCommentsEnable to set
	 */
	public void setAllCommentsEnable(boolean allCommentsEnable) {
		this.allCommentsEnable = allCommentsEnable;
	}

	public void setAllIdeaStatus(List<IdeaStatus> allIdeaStatus) {
		this.allIdeaStatus = allIdeaStatus;
	}

	/**
	 * @param categoryFilter
	 *            the categoryFilter to set
	 */
	public void setCategoryFilter(Challenge categoryFilter) {
		this.categoryFilter = categoryFilter;
	}

	public void setCurrentBlogCommemt(BlogComment currentBlogCommemt) {
		this.currentBlogCommemt = currentBlogCommemt;
	}

	public void setCurrentIdea(Idea currentIdea) {
		this.currentIdea = currentIdea;
	}

	public void setCurrentIdeaBlog(IdeaBlog currentIdeaBlog) {
		this.currentIdeaBlog = currentIdeaBlog;
	}

	/**
	 * @param currentVote
	 *            the currentVote to set
	 */
	public void setCurrentVote(Vote currentVote) {
		this.currentVote = currentVote;
	}

	public void setIdeaBlogDao(IdeaBlogDAO ideaBlogDao) {
		this.ideaBlogDao = ideaBlogDao;
	}

	public void setIdeaBlogs(List<IdeaBlog> ideaBlogs) {
		this.ideaBlogs = ideaBlogs;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	/**
	 * @param personalBlogs
	 *            the personalBlogs to set
	 */
	public void setPersonalBlogs(List<IdeaBlog> personalBlogs) {
		this.personalBlogs = personalBlogs;
	}

	public void setSortOrderComm(Object sortOrderComm) {
		this.sortOrderComm = sortOrderComm;
	}

	public void setSortOrderNeg(Object sortOrderNeg) {
		this.sortOrderNeg = sortOrderNeg;
	}

	public void setSortOrderPos(Object sortOrderPos) {
		this.sortOrderPos = sortOrderPos;
	}

	public void setStatusFilter(String statusFilter) {
		this.statusFilter = statusFilter;
	}

	public void setUserSessionController(UserSessionController userSessionController) {
		this.userSessionController = userSessionController;
	}

	/**
	 * @param voteDAO
	 *            the voteDAO to set
	 */
	public void setVoteDAO(VoteDAO voteDAO) {
		this.voteDAO = voteDAO;
	}

	public String showBlog() {
		sendToFlash("blog", currentIdeaBlog);
		sendToSession("currentBlogsession", currentIdeaBlog);
		return "showBlog?faces-redirect=true";
	}

	public void sortComm() {
		setSortOrderPos(SortOrder.unsorted);
		setSortOrderNeg(SortOrder.unsorted);
		if (sortOrderComm.equals(SortOrder.ascending)) {
			setSortOrderComm(SortOrder.descending);
		} else {
			setSortOrderComm(SortOrder.ascending);
		}
	}

	public void sortNeg() {
		setSortOrderPos(SortOrder.unsorted);
		setSortOrderComm(SortOrder.unsorted);
		if (sortOrderNeg.equals(SortOrder.ascending)) {
			setSortOrderNeg(SortOrder.descending);
		} else {
			setSortOrderNeg(SortOrder.ascending);
		}
	}

	public void sortPos() {
		setSortOrderNeg(SortOrder.unsorted);
		setSortOrderComm(SortOrder.unsorted);
		if (sortOrderPos.equals(SortOrder.ascending)) {
			setSortOrderPos(SortOrder.descending);
		} else {
			setSortOrderPos(SortOrder.ascending);
		}
	}

	public void updateBlog() {
		currentIdeaBlog.setLastUpdateDate(new Date());
		ideaBlogDao.updateEntity(currentIdeaBlog);
		displayInfoMessage("blog.manageBlog.message.blogUpdated", currentIdeaBlog.getBlogTitle());
	}

	private void initiateVote() {

		currentVote = new Vote(userSessionController.getCurrentUser(), currentIdeaBlog);
	}

	private void loadAllCategories() {
		allChalenges = challengeDAO.getAllEntites(Challenge.class);

	}

	private void loadMostRecentComments() {

		if (!allCommentsEnable) {
			commentsList = blogCommentDAO.getinitialCommentsByBlog(currentIdeaBlog, MAX_INITIAL_RESULTS);
		}

		else {
			commentsList = blogCommentDAO.getCommentsByBlog(currentIdeaBlog);
		}
	}

	private void loadVote() {
		// TODO Auto-generated method stub
		if (userSessionController.getCurrentUser() != null) {
			currentVote = voteDAO.getVoteByUserAndBlog(userSessionController.getCurrentUser(), currentIdeaBlog);
		}
	}
}
