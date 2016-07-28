package eg.com.etisalat.contest.dao;

import eg.com.etisalat.base.dao.BaseEntityDAO;
import eg.com.etisalat.contest.domain.ContestStage;

public interface ContestStageDAO extends BaseEntityDAO<ContestStage> {

	ContestStage getCurrentStage();

	ContestStage getStageByOrder(int order);

}
