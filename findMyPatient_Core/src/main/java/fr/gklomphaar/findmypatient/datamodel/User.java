
package fr.gklomphaar.findmypatient.datamodel;

import sun.net.NetworkClient;

/**
 * Model class, containing specific data about a single user
 *
 */
public class User {
	
	private String userName;
	private String password;
	private String rights;
	private String id;

	/**
	 * Creates a new User object
	 * @param name of the user
	 * @param password of the user
	 */
	public User(String name, String password)
	{
		this.userName = name;
		this.password = password;
		this.id = "New";
	}
	
	/**
	 * Creates a new User object
	 * @param name of the user
	 * @param password of the user
	 * @param rights associated to the user
	 * @param id of the user
	 */
	public User(String name, String password, String rights, String id)
	{
		this.userName = name;
		this.password = password;
		this.rights = rights;
		this.id = id;
	}
	/**
	 * @return a String containing the name
	 */
	public String getName() {
		return userName;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.userName = name;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the rights
	 */
	public String getRights() {
		return rights;
	}
}
