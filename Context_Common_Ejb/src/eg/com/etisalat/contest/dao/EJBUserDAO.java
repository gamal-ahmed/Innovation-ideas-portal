package eg.com.etisalat.contest.dao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.User;

@Stateless
public class EJBUserDAO extends EJBEntityDAO<User> implements UserDAO {

	@Inject
	private DiscussionThreadDAO threadDAO;

	@Override
	public User addEntity(User entity) {
		User result = super.addEntity(entity);
		// threadDAO.updateEntity(result.getDiscussionThread());
		return result;
	}

	@Override
	public User getUserByEmail(String userName) {
		try {
			Query query = entityManager.createQuery("select u from User u where u.email = ?");
			query.setParameter(1, userName);
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User updateEntity(User entity) {
		if (entity.getPassword() == null || entity.getPassword().equalsIgnoreCase("")) {
			User user = getUserByEmail(entity.getEmail());
			entity.setPassword(user.getPassword());
		}

		return super.updateEntity(entity);
	}

	@Override
	public void updateUserExceptUserStatus(User currentUser) {
		if (currentUser.getPassword() == null || currentUser.getPassword().equalsIgnoreCase("")) {
			User user = getUserByEmail(currentUser.getEmail());
			currentUser.setPassword(user.getPassword());
		}

		String queryString = "update User u set u.firstName = ?, u.lastName=?, u.password=?, u.msisdn=?, u.userStatus.userStatusId=?";// ,
																																		// u.age=?,
																																		// u.gender=?,
																																		// ";
		if (currentUser.getAge() != null) {
			queryString += ", u.age=?";
		}
		if (currentUser.getGroupEmails() != null) {
			queryString += ",u.groupEmails=?";
		}
		if (currentUser.getGender() != null) {
			queryString += ", u.gender=?";
		}

		queryString += " where u.userId=?";

		Query query = entityManager.createQuery(queryString);
		int index = 1;
		query.setParameter(index++, currentUser.getFirstName());
		query.setParameter(index++, currentUser.getLastName());
		query.setParameter(index++, currentUser.getPassword());
		query.setParameter(index++, currentUser.getMsisdn());
		query.setParameter(index++, currentUser.getUserStatus().getUserStatusId());
		if (currentUser.getAge() != null) {
			query.setParameter(index++, currentUser.getAge());
		}
		if (currentUser.getGroupEmails() != null) {
			query.setParameter(index++, currentUser.getGroupEmails());
		}
		if (currentUser.getGender() != null) {
			query.setParameter(index++, currentUser.getGender());
		}

		query.setParameter(index++, currentUser.getUserId());
		query.executeUpdate();

	}

}
