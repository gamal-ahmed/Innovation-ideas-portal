/**
 * 
 */
package eg.com.etisalat.contest.dao;

import javax.ejb.Stateless;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.DiscussionThread;

/**
 * @author karim.azkoul
 * 
 */
@Stateless
public class EJBDiscussionThreadDAO extends EJBEntityDAO<DiscussionThread> implements DiscussionThreadDAO {

	@Override
	public DiscussionThread loadDiscussionThreadFully(DiscussionThread discussionThread) {
		DiscussionThread thread = getEntityByPK(discussionThread);
		thread.getThreadMessages();
		logger.info("message size" + thread.getThreadMessages().size());
		return thread;
	}

}
