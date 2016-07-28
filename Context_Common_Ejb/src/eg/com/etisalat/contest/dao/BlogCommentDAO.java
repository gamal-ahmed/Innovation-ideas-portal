/**
 * 
 */
package eg.com.etisalat.contest.dao;

import java.util.List;

import eg.com.etisalat.base.dao.BaseEntityDAO;
import eg.com.etisalat.contest.domain.BlogComment;
import eg.com.etisalat.contest.domain.IdeaBlog;

/**
 * @author karim.azkoul
 * 
 */
public interface BlogCommentDAO extends BaseEntityDAO<BlogComment> {

	List<BlogComment> getCommentsByBlog(IdeaBlog blog);

	List<BlogComment> getinitialCommentsByBlog(IdeaBlog blog, int max);

}
