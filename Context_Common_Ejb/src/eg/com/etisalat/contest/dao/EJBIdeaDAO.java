package eg.com.etisalat.contest.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaStatus;
import eg.com.etisalat.contest.domain.User;

@Stateless
public class EJBIdeaDAO extends EJBEntityDAO<Idea> implements IdeaDAO {

	@Inject
	private IdeaBlogDAO ideaBlogDAO;

	@Override
	public Idea addEntity(Idea entity) {
		Idea result = super.addEntity(entity);
		Query query = entityManager.createQuery("delete from Idea i where i.ideaId = ? and ((select count(j) from Idea j where j.user = ?) >10)");
		query.setParameter(1, result.getIdeaId());
		query.setParameter(2, result.getUser());
		if (query.executeUpdate() > 0) {
			result = null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eg.com.etisalat.base.dao.EJBEntityDAO#deleteEntity(eg.com.etisalat.base
	 * .entity.BaseEntity)
	 */
	@Override
	public boolean deleteEntity(Idea idea) {
		if ((idea.getIdeaBlog() == null) || (idea.getIdeaBlog() != null && ideaBlogDAO.deleteEntity(idea.getIdeaBlog()))) {
			return super.deleteEntity(idea);
		}
		return false;
	}

	@Override
	public List<Idea> getAllEntitesByUpdateDate(Date rejectionDate) {
		try {
			Query query = entityManager.createQuery("select i from Idea i where i.updateDate = ? and i.ideaStatus = ?");
			query.setParameter(1, rejectionDate);
			query.setParameter(2, IdeaStatus.REJECTED);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Idea> getAllIdeasForUser(User user,RetreivingCriteria criteria,int max) {
		try {
			Query query =null;
			if(criteria==RetreivingCriteria.RECENT)
			{
				query= entityManager.createQuery("select i from Idea i where i.user = ? ORDER BY idea_id DESC");
				query.setParameter(1, user);
				
			}
			if (max != 0) {

				query.setMaxResults(max);
			}
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Idea getIdeaByIdeaId(Long IdeaId) {
		try {
			Query query = entityManager.createQuery("select i from Idea i where i.ideaId = ?");
			query.setParameter(1, IdeaId);
			return (Idea) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean updateEntity(Idea newIdea, Idea oldIdea) {
		try {
			Idea oldIdeaByPK = super.getEntityByPK(oldIdea);
			if (!oldIdea.isSameIdea(oldIdeaByPK)) {
				return false;
			} else {
				Idea foundIdea = entityManager.find(Idea.class, oldIdea.getIdeaId(), LockModeType.PESSIMISTIC_WRITE);
				super.updateEntity(newIdea);
				entityManager.lock(foundIdea, LockModeType.NONE);
				return true;
			}
		} catch (PessimisticLockException exception) {
			exception.printStackTrace();
			return false;
		} catch (LockTimeoutException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Date updateIdeasStatusByGivenStatus(IdeaStatus newStatus, IdeaStatus statusesToReject) {
		Query query = entityManager.createQuery("update Idea i set i.ideaStatus = ?, i.updateDate=?  where i.ideaStatus = ?");
		Date updateDate = new Date();
		query.setParameter(1, newStatus);
		query.setParameter(2, updateDate);
		query.setParameter(3, statusesToReject);
		query.executeUpdate();
		return updateDate;
	}

}
