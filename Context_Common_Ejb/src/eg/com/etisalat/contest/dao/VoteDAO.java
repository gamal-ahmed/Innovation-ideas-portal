/**
 * 
 */
package eg.com.etisalat.contest.dao;

import eg.com.etisalat.base.dao.BaseEntityDAO;
import eg.com.etisalat.contest.domain.IdeaBlog;
import eg.com.etisalat.contest.domain.User;
import eg.com.etisalat.contest.domain.Vote;

/**
 * @author O-Mohamed.Salman
 * 
 */
public interface VoteDAO extends BaseEntityDAO<Vote> {

	void deleteAllBlogVotes(IdeaBlog currentIdeaBlog);

	Vote getVoteByUserAndBlog(User u, IdeaBlog blog);

}
