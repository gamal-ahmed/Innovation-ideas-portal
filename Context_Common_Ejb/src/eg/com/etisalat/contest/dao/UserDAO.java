/**
 * 
 */
package eg.com.etisalat.contest.dao;

import eg.com.etisalat.base.dao.BaseEntityDAO;
import eg.com.etisalat.contest.domain.User;

/**
 * @author karim.azkoul
 * 
 */
public interface UserDAO extends BaseEntityDAO<User> {

	User getUserByEmail(String userName);

	void updateUserExceptUserStatus(User currentUser);

}
