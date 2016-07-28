/**
 * 
 */
package eg.com.etisalat.contest.dao;

import javax.ejb.Stateless;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.Category;

/**
 * @author karim.azkoul
 * 
 */
@Stateless
public class EJBCategoryDAO extends EJBEntityDAO<Category> implements CategoryDAO {

}
