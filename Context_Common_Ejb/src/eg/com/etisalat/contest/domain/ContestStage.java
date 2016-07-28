package eg.com.etisalat.contest.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the CONTEST_STAGE database table.
 * 
 */
@Entity
@Table(name = "CONTEST_STAGE")
public class ContestStage extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONTEST_STAGE_STAGEID_GENERATOR", sequenceName = "SEQ_CONTEST_STAGE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTEST_STAGE_STAGEID_GENERATOR")
	@Column(name = "STAGE_ID")
	private long stageId;

	@Column(name = "STAGE_ARABIC_DESC")
	private String stageArabicDesc;

	@Column(name = "STAGE_ARABIC_NAME")
	private String stageArabicName;

	@Column(name = "STAGE_ENGLISH_DESC")
	private String stageEnglishDesc;

	@Column(name = "STAGE_ENGLISH_NAME")
	private String stageEnglishName;

	@Column(name = "STAGE_ORDER")
	private int stageOrder;

	@Temporal(TemporalType.DATE)
	@Column(name = "STAGE_START_DATE")
	private Date stageStartDate;

	@Transient
	private Date nextStageStartDate;

	public ContestStage() {
	}

	/**
	 * @return the stageEndDate
	 */
	public Date getNextStageStartDate() {
		return nextStageStartDate;
	}

	public String getStageArabicDesc() {
		return stageArabicDesc;
	}

	public String getStageArabicName() {
		return stageArabicName;
	}

	public String getStageDesc(String local) {
		if (local.equalsIgnoreCase("ar")) {
			return this.stageArabicDesc;
		} else
			return this.stageEnglishDesc;
	}

	public String getStageEnglishDesc() {
		return stageEnglishDesc;
	}

	public String getStageEnglishName() {
		return stageEnglishName;
	}

	public long getStageId() {
		return this.stageId;
	}

	public String getStageName(String local) {
		if (local.equalsIgnoreCase("ar")) {
			return this.stageArabicName;
		}
		return this.stageEnglishName;
	}

	public int getStageOrder() {
		return this.stageOrder;
	}

	public Date getStageStartDate() {
		return this.stageStartDate;
	}

	/**
	 * @param stageEndDate
	 *            the stageEndDate to set
	 */
	public void setNextStageStartDate(Date stageEndDate) {
		this.nextStageStartDate = stageEndDate;
	}

	public void setStageArabicDesc(String stageArabicDesc) {
		this.stageArabicDesc = stageArabicDesc;
	}

	public void setStageArabicName(String stageArabicName) {
		this.stageArabicName = stageArabicName;
	}

	public void setStageEnglishDesc(String stageEnglishDesc) {
		this.stageEnglishDesc = stageEnglishDesc;
	}

	public void setStageEnglishName(String stageEnglishName) {
		this.stageEnglishName = stageEnglishName;
	}

	public void setStageId(long stageId) {
		this.stageId = stageId;
	}

	public void setStageOrder(int stageOrder) {
		this.stageOrder = stageOrder;
	}

	public void setStageStartDate(Date stageStartDate) {
		this.stageStartDate = stageStartDate;
	}

	@Override
	public String toString() {
		return "ContestStage [stageId=" + stageId + ", stageArabicDesc=" + stageArabicDesc + ", stageArabicName=" + stageArabicName + ", stageEnglishDesc="
				+ stageEnglishDesc + ", stageEnglishName=" + stageEnglishName + ", stageOrder=" + stageOrder + ", stageStartDate=" + stageStartDate
				+ ", nextStageStartDate=" + nextStageStartDate + "]";
	}

}