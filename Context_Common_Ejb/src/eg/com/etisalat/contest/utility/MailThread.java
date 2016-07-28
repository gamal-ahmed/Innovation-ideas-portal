package eg.com.etisalat.contest.utility;

import java.util.List;

public class MailThread extends Thread {

	private final String template, email, firstName, ideaStatusEn, ideaStatusAr, ideaName, updateComment,URL;
	private final List<String> ccEmails;
	
	public MailThread(String template, String email, List<String> ccEmails, String firstName, String ideaStatusEn, String ideaStatusAr,
			String ideaName, String updateComment) {
		super();
		this.template = template;
		this.email = email;
		this.firstName = firstName;
		this.ideaStatusEn = ideaStatusEn;
		this.ideaStatusAr = ideaStatusAr;
		this.ideaName = ideaName;
		this.updateComment = updateComment;
		this.ccEmails = ccEmails;
		this.URL="";
	}
	
	
	public MailThread(String template, String email, List<String> ccEmails, String firstName, String ideaStatusEn, String ideaStatusAr,
			String ideaName, String updateComment, String URL) {
		super();
		this.template = template;
		this.email = email;
		this.firstName = firstName;
		this.ideaStatusEn = ideaStatusEn;
		this.ideaStatusAr = ideaStatusAr;
		this.ideaName = ideaName;
		this.updateComment = updateComment;
		this.ccEmails = ccEmails;
		this.URL=URL;
	}
	
	

	public String getIdeaStatusAr() {
		return ideaStatusAr;
	}

	@Override
	public void run() {
		CommonUtility.sendEmail(template, email, ccEmails, firstName,  ideaStatusEn, getIdeaStatusAr(), ideaName, updateComment,URL);
	}
}