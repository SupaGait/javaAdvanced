/**
 * 
 */
package fr.gklomphaar.findmypatient.datamodel.exceptions;

/**
 * Current user doesn't have sufficient rights
 */
public class NoAuthorityException extends Exception {
	public NoAuthorityException() {
		super("Not sufficient rights to access.");
	}
}
