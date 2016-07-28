package eg.com.etisalat.contest.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.Content;
import eg.com.etisalat.contest.domain.Contest;

@Stateless
public class EJBContentDAO extends EJBEntityDAO<Content> implements ContentDAO {

	@Override
	public Content getContentByMessageId(String message_ID) {
		return entityManager.find(Content.class, message_ID);

	}

	@Override
	public boolean updateEntity(Content content, Content oldContent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Content> getAllEntitesByUpdateDate(Date rejectionDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Content> getAllEnglishMessages() {
		try {
			Query query = entityManager
					.createQuery("select EN from CONTENT i ");
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Content> getAllArabicMessages() {
		try {
			Query query = entityManager
					.createQuery("select AR from CONTENT i ");
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
