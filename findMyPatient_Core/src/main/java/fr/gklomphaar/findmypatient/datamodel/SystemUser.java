
package fr.gklomphaar.findmypatient.datamodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;

/**
 * Model class, containing specific data about a single user
 *
 */
@Entity
public class SystemUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String userName;
	private String password;
	private UserRights rights;

	/**
	 * Creates a new SystemUser object
	 * @param name of the user
	 * @param password of the user
	 */
	public SystemUser(String name, String password, UserRights rights)
	{
		this.id = 0;
		this.userName = name;
		this.password = password;
		this.rights = rights;
	}
	
	/**
	 *	Default constructor 
	 */
	public SystemUser() {
		this.userName = "";
		this.password = "";
		this.rights = UserRights.None;
		this.id = 0;
	}

	/**
	 * Creates a new SystemUser object
	 * @param name of the user
	 * @param password of the user
	 * @param rights associated to the user
	 * @param id of the user
	 */
	public SystemUser(String name, String password, String rights, int id)
	{
		this.userName = name;
		this.password = password;
		this.rights = UserRights.None;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param rights the rights to set
	 */
	public void setRights(UserRights rights) {
		this.rights = rights;
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
	public UserRights getRights() {
		return rights;
	}
}
