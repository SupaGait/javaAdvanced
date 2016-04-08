/**
 * 
 */
package fr.gklomphaar.GenericHybernateDao.exceptions;

/**
 * An problem occurred loading loading the instance
 * 
 */
public class DaoLoadObjectException extends Exception {
	public DaoLoadObjectException(Object objectToLoad, Throwable cause)	{
		super("a problem occured while loading instance: " + String.valueOf(objectToLoad), cause);	
	}
}
