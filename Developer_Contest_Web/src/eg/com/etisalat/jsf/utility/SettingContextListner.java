package eg.com.etisalat.jsf.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import eg.com.etisalat.contest.dao.SettingDAO;
import eg.com.etisalat.contest.domain.Setting;
import eg.com.etisalat.contest.utility.SettingCashManager;

/**
 * Application Lifecycle Listener implementation class SettingContextListner
 * 
 */
@WebListener
public class SettingContextListner implements ServletContextListener {
	@EJB
	SettingDAO settingDAO;

	/**
	 * Default constructor.
	 */
	public SettingContextListner() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		List<Setting> settings = settingDAO.getAllEntites(Setting.class);
		Iterator<Setting> iterator = settings.iterator();
		Map<String, String> settingMap = new HashMap<String, String>();
		while (iterator.hasNext()) {
			Setting setting = iterator.next();
			settingMap.put(setting.getSettingName(), setting.getSettingValue());

		}
		SettingCashManager.getIistance().setSettings(settingMap);

	}

}
