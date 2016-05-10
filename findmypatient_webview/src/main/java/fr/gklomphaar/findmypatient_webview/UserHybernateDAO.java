package fr.gklomphaar.findmypatient_webview;

import org.hibernate.SessionFactory;

import fr.gklomphaar.findmypatient.datamodel.SystemUser;
import fr.gklomphaar.findmypatient.dao.GenericHybernateDAO;

/**
 * @author Gerard
 *
 */
public class UserHybernateDAO extends GenericHybernateDAO<SystemUser> {
	private static final long serialVersionUID = 1L;

	public UserHybernateDAO(SessionFactory sessionFactory) {
		super(SystemUser.class, sessionFactory);
	}
}
