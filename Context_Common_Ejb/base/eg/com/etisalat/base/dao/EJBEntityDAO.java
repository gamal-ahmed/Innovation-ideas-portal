/**
 * 
 */
package eg.com.etisalat.base.dao;

import java.util.Arrays;
import java.util.List;

import javax.ejb.SessionContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import eg.com.etisalat.base.entity.BaseEntity;

/**
 * @author karim.azkoul
 * @param <T>
 * 
 */
public class EJBEntityDAO<T extends BaseEntity> implements BaseEntityDAO<T> {

	@PersistenceContext
	protected EntityManager entityManager;

	@Inject
	protected Logger logger;

	@Inject
	protected SessionContext sessionContext;

	public EJBEntityDAO() {
	}

	@Override
	public T addEntity(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public boolean deleteEntity(T entity) {
		if ((entity = getEntityByPK(entity)) != null) {
			entityManager.remove(entity);
			return true;
		}
		return false;
	}

	@Override
	public List<T> getAllEntites(Class<T> arg0) {
		return entityManager.createQuery("select t from " + arg0.getSimpleName() + " t").getResultList();
	}

	@Override
	public T getEntityByPK(T arg0) {
		Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(arg0);
		return (T) entityManager.find(arg0.getClass(), id);
	}

	@AroundInvoke
	public Object interceptCall(InvocationContext ctx) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("EJB method [" + ctx.getMethod().getName() + "] started");

			try {
				logger.debug("Caller is : [" + sessionContext.getCallerPrincipal().getName() + "]");
			} catch (IllegalStateException e) {
				logger.debug("Caller is : [" + "anonymous" + "]");
			}

		}
		Object[] params = ctx.getParameters();
		if (params != null && params.length > 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("method [" + ctx.getMethod().getName() + "] parameters " + Arrays.deepToString(params) + "");
			}
		}
		Object result = null;
		try {
			result = ctx.proceed();
		} catch (Exception exception) {
			logger.error("EJB method [" + ctx.getMethod().getName() + "] failed !", exception);
			throw exception;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("EJB method [" + ctx.getMethod().getName() + "] ended successfully");
		}
		return result;
	}

	@Override
	public T updateEntity(T entity) {
		return entityManager.merge(entity);
	}

}
