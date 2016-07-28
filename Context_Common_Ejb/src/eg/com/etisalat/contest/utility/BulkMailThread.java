package eg.com.etisalat.contest.utility;

import java.util.List;

import org.apache.log4j.Logger;

import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaStatus;

public class BulkMailThread extends Thread {

	private final static Logger logger = Logger.getLogger(BulkMailThread.class);

	private final List<Idea> rejectedIdeas;
	private final String ideaStatusAr;

	public BulkMailThread(List<Idea> rejectedIdeas, String ideaStatusAr) {
		super();
		this.rejectedIdeas = rejectedIdeas;
		this.ideaStatusAr = ideaStatusAr;
	}

	@Override
	public void run() {
		if (logger.isDebugEnabled()) {
			logger.debug("MAIL_THREAD::Send Bulk Emails Started");
		}
		for (Idea idea : rejectedIdeas) {
			CommonUtility.sendEmail(SettingCashManager.getIistance().getSettingValue(SettingsKeys.CHANGE_IDEA_STATUS_TEMPLATE), idea.getUser().getEmail(), idea
					.getUser().getFormattedGroupEmails(), idea.getUser().getFirstName(), IdeaStatus.REJECTED.getDescription(), ideaStatusAr, idea
					.getIdeaName(), "","");
			if (logger.isDebugEnabled()) {
				logger.debug("MAIL_THREAD::Send email (" + idea.getUser().getEmail() + ") has been done successfully");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MAIL_THREAD::Send Bulk Emails Ended Finished Successfully");
		}

	}

}