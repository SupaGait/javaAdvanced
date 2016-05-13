/**
 * 
 */
package fr.gklomphaar.findmypatient.dao.exceptions;

/**
 * An problem occurred loading loading the instance
 * 
 */
public class DaoLoadObjectException extends Exception {
	private static final long serialVersionUID = 1L;

	public DaoLoadObjectException(Object objectToLoad, Throwable cause)	{
		super("a problem occured while loading instance: " + String.valueOf(objectToLoad), cause);	
	}
}
