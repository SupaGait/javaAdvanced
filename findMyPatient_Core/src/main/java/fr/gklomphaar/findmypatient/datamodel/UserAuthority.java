/**
 * 
 */
package fr.gklomphaar.findmypatient.datamodel;

import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient.helpers.MatchUserName;

import java.io.Serializable;
import java.util.List;


public class UserAuthority implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final MatchUserName userMatcher = new MatchUserName();
	private IDataDAO<SystemUser> userDAO;
	private SystemUser currentUser = null;
	
	public enum UserRights{
		None(0, "ReadOnly"),
		ReadOnly(1, "ReadWrite"),
		ReadWrite(2, "ReadWriteAndUserManagement"),
		ReadWriteAndUserManagement(3, "");
		
		private final int rightValue;
		private final String rightString;
		
		UserRights(int value, String rightString){
			this.rightValue = value;
			this.rightString = rightString;
		}
		public int getValue(){
			return this.rightValue;
		}
		public String getString(){
			return this.rightString;
		}
	}
	
	public UserAuthority(IDataDAO<SystemUser> userDAO)
	{
		this.userDAO = userDAO;
	}
	
	/**
	 * @param userName name to log in with
	 * @param password password to log in with
	 * @throws NoAuthorityException
	 * @throws DaoLoadObjectException 
	 */
	public void login(String userName, String password) throws NoAuthorityException, DaoLoadObjectException
	{
		SystemUser searchUser = new SystemUser(userName, password, UserRights.None);
		
		// Search for the user
		List<SystemUser> foundUsers = this.userDAO.search(searchUser, userMatcher);
		if(foundUsers.size() > 0)
		{
			SystemUser foundUser = foundUsers.get(0);
			// Check the password matches
			if(foundUser.getPassword().equals(password)){
				this.currentUser = foundUser;
				return;
			}
		}
		
		// SystemUser with matching password not founds
		throw new NoAuthorityException();
	}
	
	/**
	 * Current user is logged out
	 */
	public void logout()
	{
		this.currentUser = null;
	}
	
	/**
	 * Get the username of the current user
	 * @return The current user, or empty of no user logged in
	 */
	public String getUserName()
	{
		if(this.currentUser != null)
			return this.currentUser.getName();
		else
			return "";
	}
	
	/**
	 * Get the rights of the current user
	 * @return user rights
	 */
	public UserRights getUserRights()
	{
		UserRights rights = UserRights.None;
		if(this.currentUser != null)
		{
			return this.currentUser.getRights();
		}
		return UserRights.None;
	}
}
