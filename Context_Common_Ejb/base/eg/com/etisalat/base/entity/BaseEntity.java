/**
 * 
 */
package eg.com.etisalat.base.entity;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * @author karim.azkoul
 * 
 */
public abstract class BaseEntity implements Serializable {

	@Id
	private long id;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2503445275089686234L;

	public BaseEntity() {

	}

	public String getEntityDescription() {
		return "";
	}

	public long getId() {
		return id;
	}

	public boolean isNew() {
		return getId() == 0;
	}

}
