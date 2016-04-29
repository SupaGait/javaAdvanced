
package fr.gklomphaar.findmypatient.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Model class, containing specific data about a single user
 *
 */
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="USER_USERNAME")
	private String userName;
	
	@Column(name="USER_PASSWORD")
	private String password;
	
	@Column(name="USER_RIGHTS")
	private String rights;

	/**
	 * Creates a new User object
	 * @param name of the user
	 * @param password of the user
	 */
	public User(String name, String password)
	{
		this.userName = name;
		this.password = password;
		this.id = 0;
	}
	
	/**
	 * Creates a new User object
	 * @param name of the user
	 * @param password of the user
	 * @param rights associated to the user
	 * @param id of the user
	 */
	public User(String name, String password, String rights, int id)
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
	public int getId() {
		return id;
	}
	/**
	 * @return the rights
	 */
	public String getRights() {
		return rights;
	}
}
