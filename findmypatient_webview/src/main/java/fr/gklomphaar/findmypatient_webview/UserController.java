/**
 * 
 */
package fr.gklomphaar.findmypatient_webview;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.PatientManagement;
import fr.gklomphaar.findmypatient.datamodel.User;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority;
import fr.gklomphaar.findmypatient.datamodel.UserManagement;
import fr.gklomphaar.findmypatient.dao.GenericHybernateDAO;

/**
 * 
 * @author Gerard
 *
 */
public class UserController {
	
	// Management, requires user authority
	private UserAuthority userAuthority;
	private PatientManagement patientManagement;
	private UserManagement userManagement;
	
	/**
	 * Create a new controller
	 * @param userDao The DAO responsible for user CRUD operations
	 * @param patientDao The DAO responsible for Patient CRUD operations
	 */
	public UserController(IDataDAO<User> userDAO, IDataDAO<Patient> patientDAO){
		this.userAuthority = new UserAuthority(userDAO);
		this.userManagement = new UserManagement(userDAO, this.userAuthority);
		this.patientManagement = new PatientManagement(patientDAO, userAuthority);
	}

	/**
	 * @return the userAuthority
	 */
	public UserAuthority getUserAuthority() {
		return userAuthority;
	}

	/**
	 * @return the patientManagement
	 */
	public PatientManagement getPatientManagement() {
		return patientManagement;
	}

	/**
	 * @return the userManagement
	 */
	public UserManagement getUserManagement() {
		return userManagement;
	}
	
	
}
