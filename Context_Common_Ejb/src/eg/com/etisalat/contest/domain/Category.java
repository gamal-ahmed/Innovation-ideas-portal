package eg.com.etisalat.contest.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the CATEGORY database table.
 * 
 */
@Entity
public class Category extends eg.com.etisalat.base.entity.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CATEGORY_CATEGORYID_GENERATOR", sequenceName = "SEQ_CATEGORY_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_CATEGORYID_GENERATOR")
	@Column(name = "CATEGORY_ID")
	private long categoryId;

	@Column(name = "DESCRIPTION_EN")
	private String description_en;

	@Column(name = "DESCRIPTION_AR")
	private String description_ar;

	public Category() {
	}

	public Category(long categoryId, String description_en, String description_ar) {
		super();
		this.categoryId = categoryId;
		this.description_en = description_en;
		this.description_ar = description_ar;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (categoryId != other.categoryId)
			return false;
		if (description_ar == null) {
			if (other.description_ar != null)
				return false;
		} else if (!description_ar.equals(other.description_ar))
			return false;
		if (description_en == null) {
			if (other.description_en != null)
				return false;
		} else if (!description_en.equals(other.description_en))
			return false;
		return true;
	}

	public long getCategoryId() {
		return this.categoryId;
	}

	public String getDescription_ar() {
		return description_ar;
	}

	public String getDescription_en() {
		return this.description_en;
	}

	public String getLocalizedDescription(String local) {
		if (local.equalsIgnoreCase("ar")) {
			return this.description_ar;
		} else
			return this.description_en;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (categoryId ^ (categoryId >>> 32));
		result = prime * result + ((description_ar == null) ? 0 : description_ar.hashCode());
		result = prime * result + ((description_en == null) ? 0 : description_en.hashCode());
		return result;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public void setDescription_ar(String description_ar) {
		this.description_ar = description_ar;
	}

	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", description_en=" + description_en + ", description_ar=" + description_ar + "]";
	}

}