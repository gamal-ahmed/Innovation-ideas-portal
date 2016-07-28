package eg.com.etisalat.contest.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.LockModeType;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.Contest;
import eg.com.etisalat.contest.domain.ContestType;


@Stateless
public class EJBContestDAO extends EJBEntityDAO<Contest> implements ContestDAO {

	@Override
	public Contest addEntity(Contest entity) {
		Contest result = super.addEntity(entity);

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
	public boolean deleteEntity(Contest contest) {
		return super.deleteEntity(contest);
	}

	@Override
	public List<Contest> getAllEntitesByUpdateDate(Date rejectionDate) {
		try {
			Query query = entityManager
					.createQuery("select i from CONTEST i where i.updateDate = ? ");
			query.setParameter(1, rejectionDate);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean updateEntity(Contest newContest, Contest oldContest) {
		try {
			Contest oldContestByPK = super.getEntityByPK(oldContest);
			if (!oldContest.isSameContest(oldContestByPK)) {
				return false;
			} else {
				Contest foundContest = entityManager.find(Contest.class,
						oldContest.getId(), LockModeType.PESSIMISTIC_WRITE);
				super.updateEntity(newContest);
				entityManager.lock(foundContest, LockModeType.NONE);
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
	public Contest getContestById(long contestId) {
		return entityManager.find(Contest.class, contestId);
	}

	@Override
	public List<Contest> getAllContestsByType(ContestType contestType, int max,boolean hasChallengesOnly) {
		try {
			Query query;
			query = entityManager
					.createQuery(
							"select i from Contest i where i.contestTypeID = :contesType order by i.contestId desc ",
							Contest.class);
			query.setParameter("contesType", contestType);

			if (max !=0) {

				query.setMaxResults(max);
			}
			if(hasChallengesOnly)
			{
				List<Contest>contestsHasChallenges=new ArrayList<Contest>();
				List<Contest>contests=query.getResultList();
				for ( Contest contest: contests ) {
					if(contest.getChallenges().size()>0)
						contestsHasChallenges.add(contest);
					
				} 
				return contestsHasChallenges;
			}
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
