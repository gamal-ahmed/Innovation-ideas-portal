/**
 * 
 */
package eg.com.etisalat.contest.dao;

import java.util.List;

import eg.com.etisalat.base.dao.BaseEntityDAO;
import eg.com.etisalat.contest.domain.Challenge;
import eg.com.etisalat.contest.domain.ContestType;
import eg.com.etisalat.contest.domain.Idea;

/**
 * @author Ahmed.gamal 
 * 
 */
public interface ChallengeDAO extends BaseEntityDAO<Challenge> {

	Challenge getEntityById(Long currentChalengetId);

	List<Idea> getAllCallhengeIdeas(int max,Challenge challengeId);

	List<Challenge> getAllOpenChallenges(int max,ContestType type);

}
