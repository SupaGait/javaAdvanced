package fr.gklomphaar.findmypatient.dao;

import org.hibernate.SessionFactory;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoInitializationException;
import fr.gklomphaar.findmypatient.datamodel.SystemUser;

/**
 * DAO for User data models using Hybernate implementation for CRUD operations
 * @author Gerard
 */
public class UserHybernateDAO extends GenericHybernateDAO<SystemUser> {
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new UserHybernateDAO 
	 * @param sessionFactory the sessionFactory used for Hybernate operations
	 * @throws DaoInitializationException
	 */
	public UserHybernateDAO(SessionFactory sessionFactory) throws DaoInitializationException {
		super(SystemUser.class, sessionFactory);
	}
}
