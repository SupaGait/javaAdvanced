/**
 * 
 */
package fr.gklomphaar.findmypatient.datamodel.exceptions;

/**
 * The user already exists in the system.
 *
 */
public class UserAlreadyExistsException extends Exception {
	public UserAlreadyExistsException() {
		super("The user already exists in the system.");
	}
}
