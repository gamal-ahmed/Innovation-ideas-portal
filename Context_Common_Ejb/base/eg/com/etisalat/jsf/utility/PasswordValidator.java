package eg.com.etisalat.jsf.utility;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("PasswordValidator")
public class PasswordValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		String parm = arg2.toString();
		if (parm != null && !parm.equalsIgnoreCase("")) {
			if (parm.length() < 6) {
				FacesMessage msg = new FacesMessage("Password must be 6 charchter ", "Password must be 6 charchter");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}

		}
	}

}
