/**
 * 
 */
package fr.gklomphaar.GenericHybernateDao.exceptions;

/**
 * An problem occurred while saving the instance.
 *
 */
public class DaoSaveObjectException extends Exception {
	public DaoSaveObjectException(Object objectToSave, Throwable cause)	{
		super("a problem occured while saving instance: " + String.valueOf(objectToSave), cause);	
	}
}
