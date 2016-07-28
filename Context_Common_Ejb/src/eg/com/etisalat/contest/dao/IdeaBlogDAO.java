/**
 * 
 */
package eg.com.etisalat.contest.dao;

import java.util.List;

import eg.com.etisalat.base.dao.BaseEntityDAO;
import eg.com.etisalat.contest.domain.IdeaBlog;
import eg.com.etisalat.contest.domain.User;
import eg.com.etisalat.contest.domain.Vote;

/**
 * @author karim.azkoul
 * 
 */
public interface IdeaBlogDAO extends BaseEntityDAO<IdeaBlog> {

	void decreaseComments(IdeaBlog blog);

	List<IdeaBlog> getBlogsByUser(User user);

	void increaseComments(IdeaBlog blog);

	boolean increaseNegativeRate(IdeaBlog blog);

	boolean increasePositiveRate(IdeaBlog blog, Vote vote, User user);

	boolean switchToNegativeRate(IdeaBlog blog);

	boolean switchToPositiveRate(IdeaBlog blog);

	IdeaBlog getFullIdeaBlog(IdeaBlog currentIdeaBlog);

}
