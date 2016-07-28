/**
 * 
 */
package eg.com.etisalat.contest.dao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.IdeaBlog;
import eg.com.etisalat.contest.domain.User;
import eg.com.etisalat.contest.domain.Vote;

/**
 * @author O-Mohamed.Salman
 * 
 */
@Stateless
public class EJBVoteDAO extends EJBEntityDAO<Vote> implements VoteDAO {

//	@Inject
//	private IdeaBlogDAO blogDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eg.com.etisalat.base.dao.EJBEntityDAO#addEntity(eg.com.etisalat.base.
	 * entity.BaseEntity)
	 */
	@Override
	public Vote addEntity(Vote vote) {

		Vote result = super.addEntity(vote);
		Query query = entityManager.createQuery("delete from Vote v where v = ? and ((select count(j) from Vote j where j.user = ? and j.ideaBlog=?) >1)");
		query.setParameter(1, vote);
		query.setParameter(2, vote.getUser());
		query.setParameter(3, vote.getIdeaBlog());
		if (query.executeUpdate() > 0) {
			result = null;
		}
		return result;

	}

	@Override
	public void deleteAllBlogVotes(IdeaBlog currentIdeaBlog) {
		Query query = entityManager.createQuery("delete from Vote v where v.ideaBlog=?");
		query.setParameter(1, currentIdeaBlog);
		query.executeUpdate();
		Query queryUpdate = entityManager.createQuery("update  IdeaBlog ib set ib.positiveRating = 0 where ib.blogId=? ");		
		queryUpdate.setParameter(1, currentIdeaBlog.getBlogId());
		queryUpdate.executeUpdate();
//		blogDAO.updateEntity(currentIdeaBlog);
	}

	@Override
	public Vote getVoteByUserAndBlog(User user, IdeaBlog blog) {

		try {
			Query query = entityManager.createQuery("select v from Vote v where v.user = ? and v.ideaBlog=?");
			query.setParameter(1, user);
			query.setParameter(2, blog);
			return (Vote) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
