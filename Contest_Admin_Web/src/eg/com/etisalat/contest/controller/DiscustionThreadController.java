package eg.com.etisalat.contest.controller;

import static eg.com.etisalat.jsf.utility.JSFUtils.getFromSession;

import java.util.Collections;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import eg.com.etisalat.contest.dao.DiscussionThreadDAO;
import eg.com.etisalat.contest.dao.ThreadMessagesDAO;
import eg.com.etisalat.contest.domain.DiscussionThread;
import eg.com.etisalat.contest.domain.MessageType;
import eg.com.etisalat.contest.domain.ThreadMessage;
import eg.com.etisalat.contest.domain.ThreadStatus;
import eg.com.etisalat.contest.domain.User;
import eg.com.etisalat.jsf.utility.JSFUtils;

@ManagedBean
@ViewScoped
public class DiscustionThreadController {

	@Inject
	ThreadMessagesDAO messagesDAO;
	@Inject
	DiscussionThreadDAO discussionThreadDAO;

	@Inject
	ThreadMessagesDAO threadMessagesDAO;

	DiscussionThread currentDiscussionThread;
	@ManagedProperty(value = "#{userSessionController}")
	UserSessionController userSessionController;

	private ThreadMessage currentThreadMessage = new ThreadMessage();

	public void addMessage() {
		User currentUser = userSessionController.getCurrentUser();
		currentThreadMessage.setDiscussionThread(currentDiscussionThread);
		currentThreadMessage.setMessageDate(new Date());
		if (currentUser.isContender()) {
			currentThreadMessage.setMessageType(MessageType.REQUEST);
			currentDiscussionThread.setThreadStatus(ThreadStatus.WAIT_FOR_RESPONSE);
		} else {
			currentThreadMessage.setMessageType(MessageType.RESPONSE);
			currentDiscussionThread.setThreadStatus(ThreadStatus.CLOSED);
		}
		// messagesDAO.addEntity(currentThreadMessage);
		currentDiscussionThread.getThreadMessages().add(currentThreadMessage);
		currentDiscussionThread = discussionThreadDAO.updateEntity(currentDiscussionThread);
		currentDiscussionThread = discussionThreadDAO.loadDiscussionThreadFully(currentDiscussionThread);
		Collections.sort(currentDiscussionThread.getThreadMessages());
		currentThreadMessage = new ThreadMessage();

	}

	public DiscussionThread getCurrentDiscussionThread() {
		return currentDiscussionThread;
	}

	public ThreadMessage getCurrentThreadMessage() {
		return currentThreadMessage;
	}

	public ThreadMessagesDAO getMessagesDAO() {
		return messagesDAO;
	}

	public MessageType getRequestMessageType() {
		return MessageType.REQUEST;
	}

	public MessageType getResponseMessageType() {
		return MessageType.RESPONSE;
	}

	public UserSessionController getUserSessionController() {
		return userSessionController;
	}

	public void loadThreadMessages(ComponentSystemEvent componentSystemEvent) {

		if (currentDiscussionThread == null) {
			DiscussionThread discussionThread = (DiscussionThread) getFromSession("currentThread");
			if (discussionThread != null) {
				currentDiscussionThread = discussionThread;

			} else {
				currentDiscussionThread = userSessionController.getCurrentUser().getDiscussionThread();

				if (currentDiscussionThread == null) {
					JSFUtils.displayErrorMessage("idea.discustionthread.refreshRrror");
				}
			}
			currentDiscussionThread = discussionThreadDAO.loadDiscussionThreadFully(currentDiscussionThread);
			Collections.sort(currentDiscussionThread.getThreadMessages());
		}

	}

	public void setCurrentDiscussionThread(DiscussionThread currentDiscussionThread) {
		this.currentDiscussionThread = currentDiscussionThread;
	}

	public void setCurrentThreadMessage(ThreadMessage currentThreadMessage) {
		this.currentThreadMessage = currentThreadMessage;
	}

	public void setMessagesDAO(ThreadMessagesDAO messagesDAO) {
		this.messagesDAO = messagesDAO;
	}

	public void setUserSessionController(UserSessionController userSessionController) {
		this.userSessionController = userSessionController;
	}

}
