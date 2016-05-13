/**
 * 
 */
package fr.gklomphaar.findmypatient.dao.exceptions;

/**
 * A problem occurred while initializing the DAO.
 * 
 */
public class DaoInitializationException extends Exception {
	private static final long serialVersionUID = 1L;

	public DaoInitializationException(Object problemObject, Throwable cause) {
		super("A problem occured while initializing the DAO: " + String.valueOf(problemObject), cause);
	}
}
