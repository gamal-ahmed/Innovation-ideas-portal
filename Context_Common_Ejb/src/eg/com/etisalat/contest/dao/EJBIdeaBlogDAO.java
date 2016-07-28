/**
 * 
 */
package eg.com.etisalat.contest.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.IdeaBlog;
import eg.com.etisalat.contest.domain.User;
import eg.com.etisalat.contest.domain.Vote;

/**
 * @author karim.azkoul
 * 
 */
@Stateless
public class EJBIdeaBlogDAO extends EJBEntityDAO<IdeaBlog> implements IdeaBlogDAO {

	
	@Inject
	private VoteDAO voteDAO;
	
	
	@Override
	public void decreaseComments(IdeaBlog blog) {
		// TODO Auto-generated method stub

		try {
			Query query = entityManager.createQuery("update  IdeaBlog ib set ib.totalComments = ib.totalComments - 1 where ib=? ");
			query.setParameter(1, blog);
			query.executeUpdate();
		} catch (NoResultException e) {

		}
	}

	@Override
	public List<IdeaBlog> getBlogsByUser(User user) {

		// TODO Auto-generated method stub
		try {
			Query query = entityManager.createQuery("select b from IdeaBlog b where b.idea.user = ?");
			query.setParameter(1, user);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void increaseComments(IdeaBlog blog) {
		// TODO Auto-generated method stub
		try {
			Query query = entityManager.createQuery("update  IdeaBlog ib set ib.totalComments = ib.totalComments + 1 where ib=? ");
			query.setParameter(1, blog);
			query.executeUpdate();
		} catch (NoResultException e) {

		}

	}

	@Override
	public boolean increaseNegativeRate(IdeaBlog blog) {
		boolean res = false;
		try {
			Query query = entityManager.createQuery("update  IdeaBlog ib set ib.negativeRating = ib.negativeRating + 1 where ib=? ");
			query.setParameter(1, blog);
			res = (query.executeUpdate() > 0);
		} catch (NoResultException e) {
			return false;
		}
		return res;
	}

	@Override
	public boolean increasePositiveRate(IdeaBlog blog, Vote vote,  User user ) {

		boolean result = false;
		Vote oldVote = null;
		int netVote=0;
		try {

			oldVote=voteDAO.getVoteByUserAndBlog(vote.getUser(), blog);
			
			if (oldVote!=null){
				netVote=vote.getVoteValue()-oldVote.getVoteValue();
				if(vote.getVoteValue() == 0){
					Query deleteVote = entityManager.createQuery("delete  Vote v where v.ideaBlog=? and v.user=?");
					deleteVote.setParameter(1, vote.getIdeaBlog());
					deleteVote.setParameter(2, vote.getUser());
					deleteVote.executeUpdate();
				}else{
					Query updateVote = entityManager.createQuery("update  Vote v set v.voteValue = "+ vote.getVoteValue()  +" where v.ideaBlog=? and v.user=?");
					updateVote.setParameter(1, vote.getIdeaBlog());
					updateVote.setParameter(2, vote.getUser());
					updateVote.executeUpdate();
				}
				//voteDAO.updateEntity(vote);
			}else{
				netVote=vote.getVoteValue();
				voteDAO.addEntity(vote);
			}
			
			Query updateRating = entityManager.createQuery("update  IdeaBlog ib set ib.positiveRating = ib.positiveRating + "+ netVote  +" where ib=? ");
			updateRating.setParameter(1, blog);
			result = (updateRating.executeUpdate() > 0);	
						
		} catch (NoResultException e) {
			return result;
		}
		return result;
	}

	@Override
	public boolean switchToNegativeRate(IdeaBlog blog) {
		// TODO Auto-generated method stub
		boolean res = false;
		try {
			Query query = entityManager
					.createQuery("update  IdeaBlog ib set ib.negativeRating = ib.negativeRating + 1 , ib.positiveRating = ib.positiveRating - 1 where ib=? ");
			query.setParameter(1, blog);
			res = (query.executeUpdate() > 0);
		} catch (NoResultException e) {
			return false;
		}
		return res;
	}

	@Override
	public boolean switchToPositiveRate(IdeaBlog blog) {
		boolean res = false;
		try {
			Query query = entityManager
					.createQuery("update  IdeaBlog ib set ib.negativeRating = ib.negativeRating - 1 , ib.positiveRating = ib.positiveRating + 1 where ib=? ");
			query.setParameter(1, blog);
			res = (query.executeUpdate() > 0);
		} catch (NoResultException e) {
			return false;
		}
		return res;
	}

	@Override
	public IdeaBlog getFullIdeaBlog(IdeaBlog currentIdeaBlog) {
		currentIdeaBlog=super.getEntityByPK(currentIdeaBlog);
		logger.info(currentIdeaBlog.getVotes().size());
		logger.info(currentIdeaBlog.getBlogComments().size());
		return currentIdeaBlog;
		
	}
}
