/**
 * 
 */
package fr.gklomphaar.findmypatient.dao;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoInitializationException;


public interface IDAOManagement {
	/**
	 * Connect/initialize the DAO
	 *  - ! disconnect() should always be called when ready with the DAO.
	 * @throws DaoInitializationException 
	 */
	public void connect() throws DaoInitializationException;
	
	/**
	 * Disconnect the DAO, all resources will be released.
	 */
	public void disconnect();

}
