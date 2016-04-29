/**
 * 
 */
package fr.gklomphaar.findmypatient_webview;

import org.hibernate.SessionFactory;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.dao.GenericHybernateDAO;
import fr.gklomphaar.findmypatient.dao.IGenericDataDAO;

/**
 * @author Gerard
 *
 */
public class PatientHybernateDAO extends GenericHybernateDAO<Patient> implements IGenericDataDAO<Patient> {
	public PatientHybernateDAO(SessionFactory sessionFactory) {
		super(Patient.class, sessionFactory);
	}

/*	@Override
	public List<Patient> search(Patient data, IMatcher<Patient> matcher) throws DaoLoadObjectException {
		// TODO Auto-generated method stub
		return new ArrayList<Patient>();
	}*/
}
