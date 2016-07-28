/**
 * 
 */
package eg.com.etisalat.jsf.converters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import biz.source_code.base64Coder.Base64Coder;

/**
 * @author Karim.Azkoul
 * 
 */
@FacesConverter("genericConverter")
public class GenericConverter implements Converter {

	@Inject
	private static Logger logger;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext
	 * , javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1,
			String stringValue) {
		Object result = null;
		ByteArrayInputStream inputStream = null;
		ObjectInputStream objectInputStream = null;
		byte[] data = null;

		try {
			data = Base64Coder.decode(stringValue);
			inputStream = new ByteArrayInputStream(data);
			objectInputStream = new ObjectInputStream(inputStream);
			result = objectInputStream.readObject();
		} catch (Exception e) {

			logger.error("Failed to Convert string value[" + stringValue
					+ "] into domain object", e);

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext
	 * , javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		String result = null;
		ByteArrayOutputStream outputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(object);
			result = new String(Base64Coder.encode(outputStream.toByteArray()));
		} catch (Exception e) {

			logger.error("Failed to Convert Object [" + object + "] of class <"
					+ object.getClass() + ">  into string value", e);

		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}

}
