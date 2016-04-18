
package fr.gklomphaar.findmypatient.testcases;

import java.util.List;

import org.apache.derby.jdbc.ClientDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.gklomphaar.findmypatient.config.exceptions.ConfigurationFileException;
import fr.gklomphaar.findmypatient.controller.IdentityController;
import fr.gklomphaar.findmypatient.dao.PatientJDBCDAO;
import fr.gklomphaar.findmypatient.dao.UserJDBCDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoInitializationException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient.helpers.MatchPatientDisplayName;
import fr.gklomphaar.findmypatient.helpers.MatchPatientfrontName;

/**
 * Testing different functionalities of the PatientFind application
 * 
 */
public class ProtoTypeTesting {

	@Before
	public void beforeEachTest()
	{
		//System.out.println("Before");
	}
	@After
	public void afterEatchTest()
	{
		//System.out.println("After");
	}
	
	public ClientDataSource getDataSource(){
		ClientDataSource clientDataSource = new ClientDataSource();
		clientDataSource.setServerName("jdbc:derby://localhost:1527/PatientsDB;create=true");
		clientDataSource.setUser("root");
		clientDataSource.setPassword("root");
		
		return clientDataSource;
	}
	
	/**
	 * Test if DAO create works
	 */
	@Test
	public void testDAOCreate(){
		try {
			System.out.println("--Test DAO create--");
			PatientJDBCDAO patientDAO = new PatientJDBCDAO(getDataSource());
			patientDAO.connect();
			System.out.println("Database connected !");
			
			Patient patientToAdd = new Patient("ssnNo", "fName", "lName" ,"displayName", "dob", "cellNo","Email","roomNo");
			patientDAO.create(patientToAdd);
			printPatients(patientDAO.readAll() );
			patientDAO.disconnect();
		} catch (DaoInitializationException | DaoSaveObjectException | DaoLoadObjectException e) {
			Assert.fail(e.getMessage());
		}
				
	}
	
	/**
	 * Test delete an object from the DAO
	 * @return
	 */
	@Test
	public void testDAOAddAndDelete(){

		try {
			final String tempPatientDisplayName = "NewPatientToBeDeleted";
			
			System.out.println("--Test DAO delete--");
			PatientJDBCDAO patientDAO = new PatientJDBCDAO(getDataSource());
			patientDAO.connect();
			
			// Add a patient
			Patient patientToAdd = new Patient("SSNO", "fName", "lName", "dob", "cellNo","Email",tempPatientDisplayName,"roomNo");
			patientDAO.create(patientToAdd);
			
			// Search the new patient
			Patient patientToSearch = new Patient();
			patientToSearch.setDisplayName(tempPatientDisplayName);
			List<Patient> patientList = patientDAO.search(patientToSearch, new MatchPatientDisplayName());
			
			Assert.assertEquals("Added patient did not succeed",0, patientList.size());
			/*
			if(patientList.size() <= 0)
				throw new Exception("Added patient did not succeed");
			*/
			printPatients(patientList);
			// Delete the patients
			for (Patient patient : patientList) {
				patientDAO.delete(patient);
			}
			
			Assert.assertEquals(0, patientList.size());
			/*
			if(patientList.size() > 0)
				throw new Exception("Removing patient did not succeed");
			else
				System.out.println("Succesfully deleted the newly added patient.s");
			*/
			
			patientDAO.disconnect();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
	/**
	 * Test DAO search functionality
	 * @return
	 */
	@Test
	public void testDAOSearch() {
		try {
			System.out.println("--Test DAO search--");
			PatientJDBCDAO patientDAO = new PatientJDBCDAO(getDataSource());
			patientDAO.connect();
			
			Patient patientToSearch = new Patient("Gerard", null, null, null, null, null, null, null);
			MatchPatientfrontName matcher = new MatchPatientfrontName();
			List<Patient> patientsMatched = patientDAO.search(patientToSearch, matcher);
			
			printPatients(patientsMatched);
			
			patientDAO.disconnect();
		} catch (DaoInitializationException | DaoLoadObjectException e) {
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * Test if setting up connection and reading patients works
	 * @return
	 */
	@Test
	public void testDAOConnectionWithReadAll() {
		try {
			System.out.println("--Test DAO connection--");
			PatientJDBCDAO patientDAO = new PatientJDBCDAO(getDataSource());
			patientDAO.connect();
			
			List<Patient> patients = patientDAO.readAll();
			printPatients(patients);
			
			patientDAO.disconnect();
		} catch (DaoInitializationException | DaoLoadObjectException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	/**
	 * Test if the Login of a user works
	 * @throws Exception 
	 */
	@Test
	public void testUserAuthority(){
		try {
			System.out.println("--Test User authority --");
			
			// Create a user DAO
			UserJDBCDAO userDAO = new UserJDBCDAO(getDataSource());
			userDAO.connect();
			
			// Test Authority functionality
			UserAuthority userAuthority = new UserAuthority(userDAO);
			
			// Log in as admin and read and get rights
			userAuthority.login("admin", "admin");
			System.out.println(String.format("Logged in as: %s, Rights: %s", 
					userAuthority.getUserName(),
					userAuthority.getUserRights()) );
			
			// Test logout
			userAuthority.logout();
			Assert.assertEquals("", userAuthority.getUserName());
			/*
			if(!userAuthority.getUserName().equals(""))
				throw new Exception("Logout not working");
			*/
			// Test without password
			{
				boolean userRejected = false;
				try {
					userAuthority.login("admin", "");
				} catch (NoAuthorityException e) {
					userRejected = true;
				}
				if(!userRejected)
					Assert.fail("Password check not working");
					//throw new Exception("Password check not working");
			}
			
			// Test unknown user, should be rejected
			{
				boolean userRejected = false;
				try {
					userAuthority.login("348942njasdh", "");
				} catch (NoAuthorityException e) {
					userRejected = true;
				}
				if(!userRejected)
					Assert.fail("Rejecting unkown users not working");
					//throw new Exception("Rejecting unkown users not working");
			}

			// Disconnect
			userDAO.disconnect();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	
	/**
	 * Test if the authorization integrated with controller works
	 * @return
	 */
	@Test
	public void testControllerAndDAO()
	{
		try {
			System.out.println("--Test identityController with login and dao--");
			
			IdentityController identityController = new IdentityController();
			
			// For testing, hard-code the address of DB now
			identityController.setupDatabase();
			
			// Login : Make sure admin admin is in database
			try {
				identityController.getUserAuthority().login("admin", "admin");
			} catch (NoAuthorityException e1) {
				e1.printStackTrace();
			}
			
			// DAO : get all patients
			List<Patient> patients = null;
			try {
				patients = identityController.getPatientManagement().readAll();
			} catch (NoAuthorityException e) {
				Assert.fail("Error retrieve patients");
			}
			
			System.out.println("Fetched: " + patients.size() + " patients from the DB.");
			
			identityController.closeDataBase();
		} catch (ConfigurationFileException | DaoInitializationException | DaoLoadObjectException e) {
			Assert.fail(e.getMessage());
		}
			
	}
	
	/**
	 * Print patients
	 * 
	 */
	private void printPatients(List<Patient> patients)
	{
		if(patients != null)
		{
			for (Patient patient : patients) {
				System.out.println( String.format("%s %s", patient.getpId() , patient) );
			}
		}
	}
}
