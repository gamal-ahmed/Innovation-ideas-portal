/**
 * 
 */
package eg.com.etisalat.contest.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.Challenge;
import eg.com.etisalat.contest.domain.Contest;
import eg.com.etisalat.contest.domain.ContestType;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaStatus;

/**
 * @author Ahmed Gamal
 * 
 */
@Stateless
public class EJBChallengeDAO extends EJBEntityDAO<Challenge> implements
		ChallengeDAO {

	@Override
	public Challenge getEntityById(Long currentChalengetId) {
		return entityManager.find(Challenge.class, currentChalengetId);
	}

	@Override
	public List<Idea> getAllCallhengeIdeas(int max, Challenge challengeId) {
		try {
			Query query = entityManager
					.createQuery("select i from Idea i where i.challenge = ? ");
			query.setParameter(1, challengeId);
			if (max > 0)
				query.setMaxResults(max);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Challenge> getAllOpenChallenges(int max, ContestType type) {
		try {
			Query query = null;
			int kind = 0;

			if (!type.getDescription().equals("ALL")) {
				if (type.getDescription().equals("INTERNAL")) 
					kind = 1;
				else
					kind = 0;
				query = entityManager
						.createQuery(
								"select i from Challenge i where i.internal = :kind and i.contest=null order by i.Id desc ",
								Challenge.class);
				query.setParameter("kind", kind);
			} else {
				query = entityManager
						.createQuery(
								"select i from Challenge i where  i.contest=null order by i.Id desc ",
								Challenge.class);

			}

			if (max != 0) {

				query.setMaxResults(max);
			}
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
