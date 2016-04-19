package fr.gklomphaar.findmypatient_webview;

import org.hibernate.SessionFactory;

import fr.gklomphaar.findmypatient.datamodel.User;
import fr.gklomphaar.findmypatient.dao.GenericHybernateDAO;

/**
 * @author Gerard
 *
 */
public class UserHybernateDAO extends GenericHybernateDAO<User> {
	public UserHybernateDAO(SessionFactory sessionFactory) {
		super(User.class, sessionFactory);
	}
}
