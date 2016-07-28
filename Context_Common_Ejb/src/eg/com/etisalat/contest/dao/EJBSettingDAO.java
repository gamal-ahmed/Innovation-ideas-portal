package eg.com.etisalat.contest.dao;

import javax.ejb.Stateless;

import eg.com.etisalat.base.dao.EJBEntityDAO;
import eg.com.etisalat.contest.domain.Setting;

@Stateless
public class EJBSettingDAO extends EJBEntityDAO<Setting> implements SettingDAO {

}
