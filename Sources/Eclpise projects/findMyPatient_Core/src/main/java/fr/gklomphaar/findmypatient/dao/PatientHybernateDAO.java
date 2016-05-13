/**
 * 
 */
package fr.gklomphaar.findmypatient.dao;

import org.hibernate.SessionFactory;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoInitializationException;
import fr.gklomphaar.findmypatient.datamodel.Patient;

/**
 * DAO for Patient data models using Hybernate implementation for CRUD operations
 * @author Gerard
 *
 */
public class PatientHybernateDAO extends GenericHybernateDAO<Patient> {
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new PatientHybernateDAO 
	 * @param sessionFactory the sessionFactory used for Hybernate operations
	 * @throws DaoInitializationException
	 */
	public PatientHybernateDAO(SessionFactory sessionFactory) throws DaoInitializationException {
		super(Patient.class, sessionFactory);
	}
}
