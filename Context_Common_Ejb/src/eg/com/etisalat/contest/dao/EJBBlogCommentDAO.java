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
import eg.com.etisalat.contest.domain.BlogComment;
import eg.com.etisalat.contest.domain.IdeaBlog;

/**
 * @author karim.azkoul
 * 
 */
@Stateless
public class EJBBlogCommentDAO extends EJBEntityDAO<BlogComment> implements BlogCommentDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eg.etisalat.com.base.dao.EJBEntityDAO#addEntity(eg.etisalat.com.base.
	 * entity.BaseEntity)
	 */
	@Inject
	private IdeaBlogDAO blogDAO;

	@Override
	public BlogComment addEntity(BlogComment entity) {
		// TODO Auto-generated method stub
		BlogComment result = super.addEntity(entity);
		blogDAO.increaseComments(entity.getIdeaBlog());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eg.etisalat.com.base.dao.EJBEntityDAO#deleteEntity(eg.etisalat.com.base
	 * .entity.BaseEntity)
	 */
	@Override
	public boolean deleteEntity(BlogComment entity) {
		boolean result = false;
		result = super.deleteEntity(entity);
		blogDAO.decreaseComments(entity.getIdeaBlog());
		return result;
	}

	public IdeaBlogDAO getBlogDAO() {
		return blogDAO;
	}

	@Override
	public List<BlogComment> getCommentsByBlog(IdeaBlog blog) {
		try {
			Query query = entityManager.createQuery("select c from BlogComment c where c.ideaBlog = ? ORDER BY c.commentDate DESC");
			query.setParameter(1, blog);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<BlogComment> getinitialCommentsByBlog(IdeaBlog blog, int max) {
		try {
			Query query = entityManager.createQuery("select  c1 from BlogComment c1 where c1.ideaBlog = ?  ORDER BY c1.commentDate DESC   ");
			query.setParameter(1, blog).setMaxResults(max);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void setBlogDAO(IdeaBlogDAO blogDAO) {
		this.blogDAO = blogDAO;
	}

}
