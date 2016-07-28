package eg.com.etisalat.contest.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.ContestStage;
import eg.com.etisalat.contest.utility.CommonUtility;

@Stateless
public class EJBContestStageDAO extends EJBEntityDAO<ContestStage> implements ContestStageDAO {

	@Override
	public ContestStage getCurrentStage() {
		ContestStage result = null;
		try {

			Query query = entityManager.createQuery("select s from ContestStage s where s.stageStartDate <= ? order by s.stageOrder desc");
			query.setParameter(1, CommonUtility.getTimeNow());
			List<ContestStage> resultList = query.getResultList();
			if (!CommonUtility.isEmptyList(resultList)) {
				result = (ContestStage) query.getResultList().get(0);
				ContestStage nextStage = getStageByOrder(result.getStageOrder() + 1);
				if (nextStage != null) {
					result.setNextStageStartDate(nextStage.getStageStartDate());
				}
			}
		} catch (NoResultException e) {

		}
		return result;
	}

	@Override
	public ContestStage getStageByOrder(int order) {
		try {
			Query query = entityManager.createQuery("select s from ContestStage s where s.stageOrder = ? order by s.stageId ").setMaxResults(1);
			query.setParameter(1, order);
			return (ContestStage) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
