package eg.com.etisalat.contest.utility;

import java.util.HashMap;
import java.util.Map;

public class SettingCashManager {
	private Map<String, String> settings = new HashMap<String, String>();
	private static SettingCashManager instance = new SettingCashManager();

	public static SettingCashManager getIistance() {
		return instance;
	}

	public String getSettingValue(String key) {
		return settings.get(key);
	}

	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}

}
