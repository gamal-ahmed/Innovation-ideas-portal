/**
 * 
 */
package eg.com.etisalat.contest.dao;

import java.util.Date;
import java.util.List;

import eg.com.etisalat.base.dao.BaseEntityDAO;
import eg.com.etisalat.contest.domain.Content;
import eg.com.etisalat.contest.domain.Contest;
import eg.com.etisalat.contest.domain.ContestType;
import eg.com.etisalat.contest.domain.Idea;
import eg.com.etisalat.contest.domain.IdeaStatus;
import eg.com.etisalat.contest.domain.User;

/**
 * @author Ahmed Gamal
 * 
 */
public interface ContentDAO extends BaseEntityDAO<Content> {

	List<Content> getAllEntitesByUpdateDate(Date rejectionDate);

	Content getContentByMessageId(String message_ID);
	List<Content> getAllEnglishMessages();
	List<Content> getAllArabicMessages();

	boolean updateEntity(Content content, Content oldContent);
	
}
