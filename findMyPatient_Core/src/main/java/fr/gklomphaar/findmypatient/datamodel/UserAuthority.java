/**
 * 
 */
package fr.gklomphaar.findmypatient.datamodel;

import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient.helpers.MatchUserName;

import java.util.List;


public class UserAuthority {
	
	private final MatchUserName userMatcher = new MatchUserName();
	private IDataDAO<User> userDAO;
	private User currentUser = null;
	
	public enum UserRights{
		None(0),
		ReadOnly(1),
		ReadWrite(2),
		ReadWriteAndUserManagement(3);
		
		private final int rightValue;
		UserRights(int value){
			this.rightValue = value;
		}
		public int getValue(){
			return this.rightValue;
		}
	}
	
	public UserAuthority(IDataDAO<User> userDAO)
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
		User searchUser = new User(userName, password);
		
		// Search for the user
		List<User> foundUsers = this.userDAO.search(searchUser, userMatcher);
		if(foundUsers.size() > 0)
		{
			User foundUser = foundUsers.get(0);
			
			// Check the password matches
			if(foundUser.getPassword().equals(password)){
				this.currentUser = foundUser;
				return;
			}
		}
		
		// User with matching password not founds
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
			switch(this.currentUser.getRights())
			{
				case "ReadOnly":
					rights = UserRights.ReadOnly;
					break;
				case "ReadWrite":
					rights = UserRights.ReadWrite;
					break;
				case "ReadWriteAndUserManagement":
					rights = UserRights.ReadWriteAndUserManagement;
					break;
				default:
					rights = UserRights.None;
					break;
			}
		}
		return rights;
	}
}
