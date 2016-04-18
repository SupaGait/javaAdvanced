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
import fr.gklomphaar.services.dao.generichybernate.GenericHybernateDAO;

/**
 * 
 * @author Gerard
 *
 */
public class WebController {
	
/*	@Autowired
	@Qualifier(value="userDAO")
	IUserHybernateDAO userDAO;
	//IDataDAO<User> userDAO;
	
	@Autowired
	@Qualifier(value="patientDAO")
	IPatientHybernateDAO patientDAO;
	//IDataDAO<Patient> patientDAO;
*/	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	@Qualifier(value="userDAO")
	GenericHybernateDAO<User> userGenericDAO;
	IDataDAO<User> userDAO = (IDataDAO<User>) userGenericDAO;
	
	@Autowired
	@Qualifier(value="patientDAO")
	GenericHybernateDAO<Patient> patientGenericDAO;
	IDataDAO<Patient> patientDAO = (IDataDAO<Patient>) patientGenericDAO; 
	
	// Management, requires user authority
	private UserAuthority userAuthority;
	private PatientManagement patientManagement;
	private UserManagement userManagement;
	
	/**
	 * Create a new controller
	 * @param userDao The DAO responsible for user CRUD operations
	 * @param patientDao The DAO responsible for Patient CRUD operations
	 */
	public WebController(/*IDataDAO<User> userDAO, IDataDAO<Patient> patientDAO*/){
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
