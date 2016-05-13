/**
 * 
 */
package fr.gklomphaar.findmypatient.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import fr.gklomphaar.findmypatient.config.exceptions.ConfigurationFileException;

/**
 *
 */
public class Configuration {
	private String databaseURL = "as";
	private String databasePass = "";
	private String databaseUser = "";
	
	/**
	 * Save the current configuration
	 * @throws ConfigurationFileException 
	 */
	public void save() throws ConfigurationFileException {
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream("config.properties");

			// set the properties value
			prop.setProperty("databasURL", databaseURL);
			prop.setProperty("databaseUser", databaseUser);
			prop.setProperty("databasePass", databasePass);

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			throw new ConfigurationFileException(io); 
		} finally 
		{
			if (output != null) {
				try {
					output.close();
				} 
				catch (IOException e) {
					throw new ConfigurationFileException(e); 
				}
			}

		}
	}

	/**
	 * Load the configuration
	 * @throws ConfigurationFileException 
	 */
	public void load() throws ConfigurationFileException {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value
			this.databaseURL = prop.getProperty("databasURL");
			this.databasePass = prop.getProperty("databaseUser");
			this.databaseUser = prop.getProperty("databaseUser");

		} catch (IOException ex) {
			throw new ConfigurationFileException(ex); 
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new ConfigurationFileException(e); 
				}
			}
		}
	}

	/**
	 * @return the databaseURL
	 */
	public String getDatabaseURL() {
		return databaseURL;
	}

	/**
	 * @param databaseURL the databaseURL to set
	 */
	public void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}

	/**
	 * @return the databasePass
	 */
	public String getDatabasePass() {
		return databasePass;
	}

	/**
	 * @param databasePass the databasePass to set
	 */
	public void setDatabasePass(String databasePass) {
		this.databasePass = databasePass;
	}

	/**
	 * @return the databaseUser
	 */
	public String getDatabaseUser() {
		return databaseUser;
	}

	/**
	 * @param databaseUser the databaseUser to set
	 */
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}
}
