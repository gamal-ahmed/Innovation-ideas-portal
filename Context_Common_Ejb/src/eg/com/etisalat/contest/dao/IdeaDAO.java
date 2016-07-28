/**
 * 
 */
package eg.com.etisalat.contest.dao;

import java.util.Date;
import java.util.List;

import eg.com.etisalat.base.dao.BaseEntityDAO;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaStatus;
import eg.com.etisalat.contest.domain.User;

/**
 * @author karim.azkoul
 * 
 */

public interface IdeaDAO extends BaseEntityDAO<Idea> {

	
	List<Idea> getAllEntitesByUpdateDate(Date rejectionDate);

	List<Idea> getAllIdeasForUser(User user,RetreivingCriteria criteria,int max);

	Idea getIdeaByIdeaId(String ideaId);

	boolean updateEntity(Idea idea, Idea oldIdea);

	Date updateIdeasStatusByGivenStatus(IdeaStatus newStatus, IdeaStatus statusToReject);
}
