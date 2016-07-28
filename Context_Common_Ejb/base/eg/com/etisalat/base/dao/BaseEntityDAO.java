/**
 * 
 */
package eg.com.etisalat.base.dao;

import java.util.List;

import eg.com.etisalat.base.entity.BaseEntity;

/**
 * @author karim.azkoul
 * 
 */
public interface BaseEntityDAO<T extends BaseEntity> {

	public abstract T addEntity(T entity);

	public abstract boolean deleteEntity(T entity);

	public abstract List<T> getAllEntites(Class<T> arg0);

	public abstract T getEntityByPK(T arg0);

	public abstract T updateEntity(T entity);

}
