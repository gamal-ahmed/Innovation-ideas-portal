package eg.com.etisalat.contest.utility;

public class MailMessage {
	private String from;
	private String to;
	private String subject;
	private String body;
	private String ccMails;

	public String getBody() {
		return body;
	}

	/**
	 * @return the ccMails
	 */
	public String getCcMails() {
		return ccMails;
	}

	public String getFrom() {
		return from;
	}

	public String getSubject() {
		return subject;
	}

	public String getTo() {
		return to;
	}

	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @param ccMails
	 *            the ccMails to set
	 */
	public void setCcMails(String ccMails) {
		this.ccMails = ccMails;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
