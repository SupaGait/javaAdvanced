/**
 * 
 */
package fr.gklomphaar.findmypatient_webview;

import java.util.List;

import org.hibernate.SessionFactory;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.helpers.IMatcher;
import fr.gklomphaar.findmypatient.dao.GenericHybernateDAO;
import fr.gklomphaar.findmypatient.dao.IPatientDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;

/**
 * @author Gerard
 *
 */
public class PatientHybernateDAO extends GenericHybernateDAO<Patient> implements IPatientDAO {
	public PatientHybernateDAO(SessionFactory sessionFactory) {
		super(Patient.class, sessionFactory);
	}

	@Override
	public List<Patient> search(Patient data, IMatcher<Patient> matcher) throws DaoLoadObjectException {
		// TODO Auto-generated method stub
		return null;
	}
}
