package eg.com.etisalat.jsf.utility;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;

@FacesValidator("CapthaValidator")
public class CapthaValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		javax.servlet.http.HttpSession session = request.getSession();
		String parm = arg2.toString();
		String c = (String) session.getAttribute(Captcha.CAPTCHA_KEY);
		if (!parm.equalsIgnoreCase(c)) {
			FacesMessage msg = new FacesMessage("captha value is not correct", "captha value is not correct.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}
	}

}
