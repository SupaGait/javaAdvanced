/**
 * 
 */
package fr.gklomphaar.findmypatient.config.exceptions;

/**
 * A Problem occurred while getting or saving configuration
 *
 */
public class ConfigurationFileException extends Exception{
	public ConfigurationFileException(Throwable cause)	{
		super("a problem occured while loading or saving configuration ", cause);	
	}
}
