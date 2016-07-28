/**
 * 
 */
package eg.com.etisalat.contest.dao;

import java.util.Date;
import java.util.List;

import eg.com.etisalat.base.dao.BaseEntityDAO;
import eg.com.etisalat.contest.domain.Contest;
import eg.com.etisalat.contest.domain.ContestType;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaStatus;
import eg.com.etisalat.contest.domain.User;

/**
 * @author Ahmed Gamal
 * 
 */
public interface ContestDAO extends BaseEntityDAO<Contest> {

	List<Contest> getAllEntitesByUpdateDate(Date rejectionDate);

	List<Contest>getAllContestsByType(ContestType contestType, int max,boolean hasChallengesOnly);
	Contest getContestById(long contestId);
	

	boolean updateEntity(Contest contest, Contest oldContest);
	
}
