package eg.com.etisalat.contest.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SETTING database table.
 * 
 */
@Entity
public class Setting extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SETTING_SETTINGID_GENERATOR", sequenceName = "SEQ_SETTING_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SETTING_SETTINGID_GENERATOR")
	@Column(name = "SETTING_ID")
	private long settingId;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE")
	private Date lastUpdate;

	@Column(name = "SETTING_NAME")
	private String settingName;

	@Column(name = "SETTING_VALUE")
	private String settingValue;

	public Setting() {
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public long getSettingId() {
		return this.settingId;
	}

	public String getSettingName() {
		return this.settingName;
	}

	public String getSettingValue() {
		return this.settingValue;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setSettingId(long settingId) {
		this.settingId = settingId;
	}

	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}

}