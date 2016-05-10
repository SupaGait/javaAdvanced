/**
 * 
 */
package fr.gklomphaar.findmypatient_webview;

import org.hibernate.SessionFactory;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.dao.GenericHybernateDAO;

/**
 * @author Gerard
 *
 */
public class PatientHybernateDAO extends GenericHybernateDAO<Patient> {
	private static final long serialVersionUID = 1L;

	public PatientHybernateDAO(SessionFactory sessionFactory) {
		super(Patient.class, sessionFactory);
	}
}
