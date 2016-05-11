/**
 * 
 */
package fr.gklomphaar.findmypatient.dao.exceptions;

/**
 * An problem occurred while saving the instance.
 *
 */
public class DaoSaveObjectException extends Exception {
	private static final long serialVersionUID = 1L;

	public DaoSaveObjectException(Object objectToSave, Throwable cause)	{
		super("a problem occured while saving instance: " + String.valueOf(objectToSave), cause);	
	}
}
