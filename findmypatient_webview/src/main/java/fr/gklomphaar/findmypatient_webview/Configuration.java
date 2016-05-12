package fr.gklomphaar.findmypatient_webview;

import java.util.List;

import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.datamodel.SystemUser;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;

/**
 * Responsible for checking and setting initial configuration for server
 * @author Gerard
 *
 */
public class Configuration {
	private IDataDAO<SystemUser> userDAO;
	private boolean isFirstLoad = true;
	private boolean isConfigured = false;

	/**
	 * Constructor
	 * @param userDAO SystemUser DAO which is used to check for admin rights
	 */
	public Configuration(IDataDAO<SystemUser> userDAO){
		this.userDAO = userDAO;
	}

	
	/**
	 * Checks server status if administrator configuration is OK.
	 * @return the isConfigured
	 * @throws DaoLoadObjectException 
	 */
	public boolean isConfigured() throws DaoLoadObjectException {
		// On first load query the DB.
		if(isFirstLoad){
			isFirstLoad = false;
			
			// Check if minimal 1 user has userManagement rights
			List<SystemUser> allUsers = userDAO.readAll();
			for(SystemUser user : allUsers){
				if(user.getRights() == UserRights.ReadWriteAndUserManagement)
					isConfigured = true;
			}
		}
		return isConfigured;
	}

	/**
	 * Set the flag which indicate configuration status
	 * @param isConfigured the isConfigured to set
	 */
	public void setConfigured(boolean isConfigured) {
		this.isConfigured = isConfigured;
	}
}
