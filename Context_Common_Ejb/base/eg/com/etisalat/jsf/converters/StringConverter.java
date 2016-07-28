/**
 * 
 */
package eg.com.etisalat.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 * @author Karim.Azkoul
 * 
 */
@FacesConverter(forClass = String.class)
public class StringConverter implements javax.faces.convert.Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value != null ? value.trim() : null;

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return (String) value;
	}
}
