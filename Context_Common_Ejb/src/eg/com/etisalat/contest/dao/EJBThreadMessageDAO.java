/**
 * 
 */
package eg.com.etisalat.contest.dao;

import javax.ejb.Stateless;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.ThreadMessage;

/**
 * @author karim.azkoul
 * 
 */
@Stateless
public class EJBThreadMessageDAO extends EJBEntityDAO<ThreadMessage> implements ThreadMessagesDAO {

}
