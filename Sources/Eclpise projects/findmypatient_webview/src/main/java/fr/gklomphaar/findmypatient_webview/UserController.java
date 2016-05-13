/**
 * 
 */
package fr.gklomphaar.findmypatient_webview;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.PatientManagement;
import fr.gklomphaar.findmypatient.datamodel.SystemUser;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority;
import fr.gklomphaar.findmypatient.datamodel.UserManagement;

/**
 * Controller for the current user context (Session)
 * @author Gerard
 *
 */
public class UserController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Management, requires user authority (Session)
	private UserAuthority userAuthority;
	private PatientManagement patientManagement;
	private UserManagement userManagement;
	
	// Singleton objects
	@Autowired
	IDataDAO<SystemUser> userDAO;
	@Autowired
	IDataDAO<Patient> patientDAO;
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		 in.defaultReadObject();
		 
		 // Restore the singleton objects
		 this.userAuthority.setUserDAO(userDAO);
		 this.userManagement.setUserDAO(userDAO);
		 this.patientManagement.setPatientDAO(patientDAO);
	}
	
	/**
	 * Create a new controller
	 * @param userDao The DAO responsible for user CRUD operations
	 * @param patientDao The DAO responsible for Patient CRUD operations
	 */
	public UserController(IDataDAO<SystemUser> userDAO, IDataDAO<Patient> patientDAO){
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
