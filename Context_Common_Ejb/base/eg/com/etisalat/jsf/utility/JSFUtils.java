/**
 * 
 */
package eg.com.etisalat.jsf.utility;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import eg.com.etisalat.base.entity.BaseEntity;
import eg.com.etisalat.contest.utility.CommonUtility;

/**
 * @author karim.azkoul
 * 
 */
public class JSFUtils {

	public static final String ARABIC_LANGUGAE = "ar";
	public static final String ENGLISH_LANGUGAE = "en";

	private static ResourceBundle bundleAr = getResourceBundle(ARABIC_LANGUGAE);
	private static ResourceBundle bundleEn = getResourceBundle(ENGLISH_LANGUGAE);

	public static void displayErrorMessage(String key, String... params) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessage(key, params), null));
	}

	public static void displayInfoMessage(String key, String... params) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage(key, params), null));
	}

	public static String getArabicMessage(String key, String... params) {
		return getMessage(bundleAr, key, params);
	}

	public static ResourceBundle getBundle() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getViewRoot().getLocale().getLanguage().equals(ARABIC_LANGUGAE) ? bundleAr : bundleEn;
	}

	public static String getDateDifference(Date previous) {
		int[] difference = CommonUtility.differenceDate(previous);
		if (difference[0] != 0) {
			return " " + difference[0] + " " + getMessage("blog.manageBlog.message.daysAgo", "");
		} else if (difference[1] != 0) {
			return " " + difference[1] + "  " + getMessage("blog.manageBlog.message.hours", "") + " " + difference[2] + "  "
					+ getMessage("blog.manageBlog.message.mins", "") + "   " + getMessage("blog.manageBlog.message.ago", " ");
		}

		else {
			return " " + difference[2] + "  " + getMessage("blog.manageBlog.message.mins", "") + "   " + getMessage("blog.manageBlog.message.ago", " ");
		}
	}

	public static String getEntityArabicDescription(BaseEntity param) {
		String description = param.getEntityDescription();
		String fullyQulifiedKeyName = param.getClass().getName();
		return getArabicMessage(fullyQulifiedKeyName.toLowerCase() + "." + description.toLowerCase());
	}

	public static String getEntityLoalizedDescription(BaseEntity param) {
		String description = param.getEntityDescription();
		String fullyQulifiedKeyName = param.getClass().getName();
		return getMessage(fullyQulifiedKeyName.toLowerCase() + "." + description.toLowerCase());
	}

	public static Object getFromFlash(String beanName) {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash().get(beanName);
	}

	public static Object getFromSession(String beanName) {
		Object attribute = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(beanName);
		return attribute;
	}

	public static String getMessage(ResourceBundle bundle, String key, String... params) {
		String value = bundle.getString(key);
		if (value != null && params != null) {
			for (int i = 0; i < params.length; i++) {
				value = value.replaceFirst("\\{" + i + "\\}", params[i]);
			}
		}
		return value;
	}

	public static String getMessage(String key, String... params) {
		return getMessage(getBundle(), key, params);
	}

	public static void sendToFlash(String beanName, Object beanValue) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put(beanName, beanValue);
	}

	public static void sendToSession(String beanName, Object beanValue) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(beanName, beanValue);
	}

	private static ResourceBundle getResourceBundle(String lang) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale oldLocale = facesContext.getViewRoot().getLocale();
		facesContext.getViewRoot().setLocale(new Locale(lang));
		ResourceBundle result = facesContext.getApplication().getResourceBundle(facesContext, "messages");
		facesContext.getViewRoot().setLocale(oldLocale);
		return result;
	}
}
