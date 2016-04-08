/**
 * 
 */
package fr.gklomphaar.findmypatient.dao.exceptions;

/**
 * A problem occurred while initializing the DAO.
 * 
 */
public class DaoInitializationException extends Exception {
	public DaoInitializationException(Object problemObject, Throwable cause) {
		super("A problem occured while initializing the DAO: " + String.valueOf(problemObject), cause);
	}
}
