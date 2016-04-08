/**
 * 
 */
package fr.gklomphaar.findmypatient.controller;

import fr.gklomphaar.findmypatient.config.Configuration;
import fr.gklomphaar.findmypatient.config.exceptions.ConfigurationFileException;
import fr.gklomphaar.findmypatient.dao.IDAOManagement;
import fr.gklomphaar.findmypatient.dao.PatientJDBCDAO;
import fr.gklomphaar.findmypatient.dao.UserJDBCDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoInitializationException;
import fr.gklomphaar.findmypatient.datamodel.PatientManagement;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority;
import fr.gklomphaar.findmypatient.datamodel.UserManagement;

/**
 *
 * 
 * The Identity identityController is in charge of reading local settings, 
 * creating the sub classes and providing access based on current login 
 * User. 
 *
 */
public class IdentityController {
	private Configuration configuration;
	
	private UserManagement userManagement;
	private UserAuthority userAuthority;
	private PatientManagement patientManagement;
	private IDAOManagement patientDAOManagement;
	private IDAOManagement userDAOManagement;
	
	private PatientJDBCDAO patientJDBCDAO;
	private UserJDBCDAO userJDBCDAO;
	
	/**
	 * Create a new instance of the identity controller
	 * @throws ConfigurationFileException 
	 */
	public IdentityController() throws ConfigurationFileException
	{
		// Create the configuration
		this.configuration = new Configuration();
		
		// Create UserJDBCDAO, save the management for cleanup
		this.userJDBCDAO = new UserJDBCDAO();
		this.userDAOManagement = this.userJDBCDAO;

		// Create UserJDBCDAO, save the management for cleanup
		this.patientJDBCDAO = new PatientJDBCDAO();
		this.patientDAOManagement = this.patientJDBCDAO;
		
		// Create the User Authority
		this.userAuthority = new UserAuthority(this.userJDBCDAO);
		
		// Create the Patient and User management, pass the relative DAO's
		this.userManagement = new UserManagement(this.userJDBCDAO, this.userAuthority);
		this.patientManagement = new PatientManagement(this.patientJDBCDAO, this.userAuthority);
		
		// Load the configuration
		this.configuration.load();
	}
	
	/**
	 * Setup and enable the database connection
	 * @param url Url to the JDBC database
	 * @param name User name for connection to the DB
	 * @param password password for the connection to the DB
	 * @throws DaoInitializationException 
	 */
	public void setupDatabase() throws DaoInitializationException
	{
		// Retrieve the configuration
		final String databaseURL = this.configuration.getDatabaseURL();
		final String databaseUser = this.configuration.getDatabaseUser();
		final String databasePass = this.configuration.getDatabasePass();
		
		// Connect both DAO's ( currently same DB, but could be separated)
		patientJDBCDAO.setDatabaseConnection(databaseURL, databaseUser, databasePass);
		userJDBCDAO.setDatabaseConnection(databaseURL, databaseUser, databasePass);
		
		// Enable connections
		userDAOManagement.connect();
		patientDAOManagement.connect();
	}
	
	/**
	 * Closes the connections to the database
	 * Always needs to be called before exiting the program
	 */
	public void closeDataBase()
	{
		userDAOManagement.disconnect();
		patientDAOManagement.disconnect();
	}
	
	/**
	 * @return UserManagement 
	 */
	public UserManagement getUserManagement()
	{
		return this.userManagement;
	}
	
	/**
	 * @return Patient management
	 */
	public PatientManagement getPatientManagement()
	{
		return this.patientManagement;
	}

	/**
	 * @return the userAuthority
	 */
	public UserAuthority getUserAuthority() {
		return userAuthority;
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return configuration;
	}
}
