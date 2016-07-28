/**
 * 
 */
package eg.com.etisalat.contest.dao;

import eg.com.etisalat.base.dao.BaseEntityDAO;
import eg.com.etisalat.contest.domain.DiscussionThread;

/**
 * @author karim.azkoul
 *
 */
public interface DiscussionThreadDAO extends BaseEntityDAO<DiscussionThread> {
	public DiscussionThread loadDiscussionThreadFully(DiscussionThread  discussionThread);

}
