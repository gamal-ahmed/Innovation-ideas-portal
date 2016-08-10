package eg.com.etisalat.contest.views;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import eg.com.etisalat.contest.dao.IdeaDAO;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaStatus;
import eg.com.etisalat.contest.utility.MailThread;
import eg.com.etisalat.jsf.utility.JSFUtils;

@ManagedBean
@ViewScoped
public class EditIdeaPage {

	private Idea orginalIdea;
	private Idea clonedIdea;
	private Long ideaId;

	
	public void init() {
		ideaId = Long.valueOf(FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("id"));
		if (ideaId == null) {
			String message = "Bad request. Please use a link from within the system.";
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									message, null));
			return;
		} else {

			orginalIdea = ideaDao.getIdeaByIdeaId(ideaId);

			if (orginalIdea != null) {
				JSFUtils.sendToFlash("manageidea", orginalIdea);
				clonedIdea = orginalIdea;
				JSFUtils.sendToFlash("manageideacloned", clonedIdea);

			}
		}
	}

	@Inject
	IdeaDAO ideaDao;

	public Long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(Long ideaId) {
		this.ideaId = ideaId;
	}

	public Idea getOrginalIdea() {
		// if (JSFUtils.getFromFlash("manageidea") != null)
		// orginalIdea = (Idea) JSFUtils.getFromFlash("manageidea");

		return orginalIdea;
	}

	public void setOrginalIdea(Idea orginalIdea) {
		this.orginalIdea = orginalIdea;
		// JSFUtils.sendToFlash("manageidea", orginalIdea);

	}

	public Idea getClonedIdea() {
		return clonedIdea;
	}

	public void setClonedIdea(Idea clonedIdea) {
		this.clonedIdea = clonedIdea;
	}

	public void submit() {
		if (JSFUtils.getFromFlash("manageideacloned") != null) {
			clonedIdea = (Idea) JSFUtils.getFromFlash("manageideacloned");
			if (!clonedIdea.isSameIdea(orginalIdea)) {
				ideaDao.updateEntity(orginalIdea);
				MailThread mail = new MailThread("changeStatus.vm",
						"agamal.halem@gmail.com", null, "Ahmed",
						IdeaStatus.SHORT_LISTED.getDescription(), "",
						clonedIdea.getIdeaName(), "check updates");

			}
		}
	}
}
