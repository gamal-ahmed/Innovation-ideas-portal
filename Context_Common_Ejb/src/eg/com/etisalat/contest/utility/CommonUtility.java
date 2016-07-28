/**
 * 
 */
package eg.com.etisalat.contest.utility;

import java.io.File;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.sun.mail.smtp.SMTPMessage;

/**
 * @author karim.azkoul
 * 
 */
public class CommonUtility {

	private static final Logger logger = Logger.getLogger(CommonUtility.class);

	public static final long DAY_IN_MILLISECONDES = 24 * 60 * 60 * 1000;

	public static final String MSISDN_PATTERN = "(01|1)[^3][0-9]+";

	public static boolean allItemsAreEmpty(List<? extends Object> list) {
		boolean result = isEmptyList(list);
		if (!result) {
			result = true;
			Iterator<? extends Object> iterator = list.iterator();
			Object item = null;
			while (iterator.hasNext()) {
				item = iterator.next();
				if (item != null) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	public static boolean allStringsAreEmpty(List<String> list) {
		boolean result = isEmptyList(list);
		if (!result) {
			result = true;
			Iterator<String> iterator = list.iterator();
			String item = null;
			while (iterator.hasNext()) {
				item = iterator.next();
				if (!isStringEmpty(item)) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	public static int[] differenceDate(java.util.Date previousDate) {

		Calendar previousCalendarDate = Calendar.getInstance();
		Calendar nowCalendarDate = Calendar.getInstance();
		previousCalendarDate.setTime(previousDate);
		nowCalendarDate.setTime(getTimeNow());
		int hours = 0;
		int mins = 0;

		int days = (int) (nowCalendarDate.getTimeInMillis() - previousCalendarDate.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		if (days == 0) {
			hours = (int) (nowCalendarDate.getTimeInMillis() - previousCalendarDate.getTimeInMillis()) / (1000 * 60 * 60);
			mins = (int) ((nowCalendarDate.getTimeInMillis() - previousCalendarDate.getTimeInMillis()) / (1000 * 60)) - (hours * 60);
		}
		int[] result = { days, hours, mins };
		return result;

	}

	public static java.util.Date extractDate(java.util.Date timestamp) {
		java.util.Date result = timestamp;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(timestamp);
		try {
			result = new Date(dateFormat.parse(dateString).getTime());
		} catch (ParseException e) {
			logger.error("getDateNow failed!", e);
			result = null;
		}
		return result;
	}

	public static String formatNumberWithLeadingZero(String msisdn) {
		String result = msisdn;
		if (!msisdn.startsWith("0")) {
			result = "0" + msisdn;
		}
		return result;
	}

	public static String generateRandomPassword() {
		Random r = new Random();
		String token = Long.toString(Math.abs(r.nextLong()), 36);
		return token;
	}

	public static Date getDateNow() {
		Date result = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(result);
		try {
			result = new Date(dateFormat.parse(dateString).getTime());
		} catch (ParseException e) {
			logger.error("getDateNow failed!", e);
			result = null;
		}
		return result;
	}

	public static Date getTimeNow() {
		Date result = new Date(System.currentTimeMillis());
		return result;
	}

	public static String hashPassword(String passwod) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(passwod.getBytes());
			byte[] enc = md.digest();
			String md5Sum = new sun.misc.BASE64Encoder().encode(enc);
			return md5Sum;
		} catch (NoSuchAlgorithmException nsae) {
			logger.error(nsae,nsae);
			return null;
		}

	}

	public static boolean isEmptyArray(Object[] jobNames) {
		boolean result = jobNames == null || jobNames.length == 0;
		return result;
	}

	public static boolean isEmptyCollection(Collection<? extends Object> collection) {
		boolean result = collection == null || collection.size() == 0;
		return result;
	}

	public static boolean isEmptyList(List<? extends Object> list) {
		boolean result = list == null || list.size() == 0;
		return result;
	}

	public static boolean isEmptyMap(Map<? extends Object, ? extends Object> map) {
		boolean result = map == null || map.size() == 0;
		return result;
	}

	public static boolean isStringEmpty(String str) {
		if (str == null || str.trim().length() == 0)
			return true;
		else
			return false;
	}

	public static java.util.Date mergeDateWithTime(java.util.Date someDay, java.util.Date time) {
		java.util.Date mergedDateAndTime = someDay;
		if (someDay != null) {
			Calendar calendarTime = Calendar.getInstance();
			calendarTime.setTime(time);

			Calendar calendarDate = Calendar.getInstance();
			calendarDate.setTime(someDay);

			calendarDate.set(Calendar.HOUR_OF_DAY, calendarTime.get(Calendar.HOUR_OF_DAY));
			calendarDate.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
			calendarDate.set(Calendar.SECOND, calendarTime.get(Calendar.SECOND));

			mergedDateAndTime = calendarDate.getTime();
		}

		return mergedDateAndTime;

	}

	public static String removeLastComma(String string) {
		string = string.substring(0, string.lastIndexOf(","));
		return string;
	}

	public static String replace(String orgnal, String replaceStr, String regex) {
		return orgnal.replaceAll(regex, replaceStr);
	}

	public static void sendEmail(String templateName, String email, List<String> ccEmails, String firstName, String newStatusEn,
			String newStatusAr, String ideaName, String updateComment,String URL) {

		try {
			Properties props = new Properties();

			SettingCashManager settingCashManager = SettingCashManager.getIistance();
			final String username = settingCashManager.getSettingValue(SettingsKeys.MAIL_USER_NAME);
			final String password = settingCashManager.getSettingValue(SettingsKeys.MAIL_USER_PASSWD);
			String host = settingCashManager.getSettingValue(SettingsKeys.MAIL_SMTP_HOST);
			String fromMail = settingCashManager.getSettingValue(SettingsKeys.MAIL_FROM);
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", host);
			props.setProperty("mail.user", username);
			props.setProperty("mail.password", password);
			props.put("mail.smtp.auth", "true");
			List toList = new ArrayList();
			toList.add(email);
			HashMap messageParameters = new HashMap();
			messageParameters.put("firstName", firstName);
			String title = settingCashManager.getSettingValue(SettingsKeys.RESGESTRATION_MAIL_SUBJECT);
			if (templateName.equals(settingCashManager.getSettingValue(SettingsKeys.REG_TEMPLATE))) {
				messageParameters.put("email", email);
			}
			if (templateName.equals(settingCashManager.getSettingValue(SettingsKeys.RESET_TEMPLATE))) {
				messageParameters.put("URL",URL) ;
				title = settingCashManager.getSettingValue(SettingsKeys.RESET_MAIL_SUBJECT);
			}
			if (templateName.equals(settingCashManager.getSettingValue(SettingsKeys.CHANGE_IDEA_STATUS_TEMPLATE))) {
				messageParameters.put("newStatusEn", newStatusEn);
				messageParameters.put("newStatusAr", newStatusAr);
				messageParameters.put("ideaName", ideaName);
				messageParameters.put("updateComment", updateComment);
				title = settingCashManager.getSettingValue(SettingsKeys.CHANGE_STATUS_MAIL_SUBJECT);
				logger.debug("Sending a mail to User: " + email + " for idea name: " + ideaName);
			}

			sendMail(props, fromMail, toList, ccEmails == null ? new ArrayList() : ccEmails, new ArrayList(), title, messageParameters, templateName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void sendMail(Properties mailProperty, String fromMail, List toList, List ccList, List bccList, String title, HashMap messageParameters,
			String templateName) {
		try {
			Session mailSession = Session.getDefaultInstance(mailProperty, null);
			mailSession.setDebug(false);
			Transport transport = mailSession.getTransport("smtp");
			SMTPMessage message = new SMTPMessage(mailSession);
			message.setSubject(title, "UTF-8");
			message.addHeader("Date", new java.util.Date().toString());
			message.setSentDate(new java.util.Date());
			message.setFrom(new InternetAddress(fromMail));
			for (int i = 0; i < toList.size(); i++) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toList.get(i).toString()));
			}
			for (int i = 0; i < ccList.size(); i++) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccList.get(i).toString()));
			}
			for (int i = 0; i < bccList.size(); i++) {
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bccList.get(i).toString()));
			}

			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.init();

			VelocityContext context = new VelocityContext();

			Iterator messageIterator = messageParameters.keySet().iterator();
			while (messageIterator.hasNext()) {
				String key = (String) messageIterator.next();
				context.put(key, messageParameters.get(key));
			}

			Template t = ve.getTemplate("Template" + File.separator + templateName);
			StringWriter writer = new StringWriter();
			t.merge(context, writer);

			javax.mail.internet.MimeMultipart multipart = new javax.mail.internet.MimeMultipart("related");

			// first part (the html)
			javax.mail.BodyPart messageBodyPart = new javax.mail.internet.MimeBodyPart();
			messageBodyPart.setContent(writer.toString(), "text/html; charset=iso-8859-6");

			// add it
			multipart.addBodyPart(messageBodyPart);

			// second part (the image)

			final String templateImagesPath = SettingCashManager.getIistance().getSettingValue(SettingsKeys.TEMPLATE_IMAGES_PATH);

			messageBodyPart = new javax.mail.internet.MimeBodyPart();
			javax.activation.DataSource fds = new javax.activation.FileDataSource(templateImagesPath + File.separator + "image001.jpg");
			messageBodyPart.setDataHandler(new javax.activation.DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<header>");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new javax.mail.internet.MimeBodyPart();
			fds = new javax.activation.FileDataSource(templateImagesPath + File.separator + "image002.jpg");
			messageBodyPart.setDataHandler(new javax.activation.DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<innerImage>");
			multipart.addBodyPart(messageBodyPart);

			// put everything together
			message.setContent(multipart);

			// message.setContent(writer.toString(),
			// "text/plain; charset=utf8");
			SettingCashManager settingCashManager = SettingCashManager.getIistance();
			final String username = settingCashManager.getSettingValue(SettingsKeys.MAIL_USER_NAME);
			final String password = settingCashManager.getSettingValue(SettingsKeys.MAIL_USER_PASSWD);
			String host = settingCashManager.getSettingValue(SettingsKeys.MAIL_SMTP_HOST);

			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());

			transport.close();
		}

		catch (MessagingException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * split string to list using pattern [ ,\\r\\n]+ and remove duplicated
	 * items
	 * 
	 * @param data
	 * @return
	 */
	public static List<String> splitToUniqueList(String data) {
		List<String> list = Arrays.asList(data.trim().split("[ ,\\r\\n]+"));
		return uniqueList(list);
	}

	// @SuppressWarnings("unchecked")
	public static <E extends Object> List<E> subtractList(List<E> a, List<E> b) {
		if (isEmptyList(a)) {
			return a;
		}
		if (isEmptyList(b)) {
			return a;
		}

		HashSet<E> aSet = new HashSet<E>(a);
		HashSet<E> bSet = new HashSet<E>(b);
		if (aSet.removeAll(bSet)) {
			return new ArrayList<E>(aSet);
		}
		return a;
	}

	public static String toCommaSeparatedValues(List<Long> requestIdList) {
		return requestIdList.toString().substring(1, requestIdList.toString().length() - 1);
	}

	/**
	 * merge array of strings to CSV
	 * 
	 * @param args
	 * @return
	 */
	public static String toCSV(Object... args) {
		StringBuilder builder = new StringBuilder();
		final String COMMA = ", ";

		for (Object value : args) {
			builder.append(value.toString());
			builder.append(COMMA);
		}

		// remove last comma
		builder.replace(builder.length() - COMMA.length(), builder.length(), "");
		return builder.toString();
	}

	public static List<String> trimStringList(List<String> list) {
		List<String> trimmed = new ArrayList<String>(list.size());

		for (String item : list) {
			String val = item.trim();
			if (val.equals("")) {
				continue;
			}
			trimmed.add(val);
		}

		return trimmed;
	}

	/**
	 * convert list to unique list
	 * 
	 * @param list
	 * @return
	 */
	public static List uniqueList(List list) {
		Set set = new HashSet(list);
		return new ArrayList(set);
	}

	public static boolean validateMsisdn(String msisdn) {
		return msisdn.matches(MSISDN_PATTERN);
	}

}
