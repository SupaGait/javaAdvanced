
package fr.gklomphaar.findmypatient.datamodel;

import java.io.Serializable;
import java.util.List;

import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient.datamodel.exceptions.UserAlreadyExistsException;
import fr.gklomphaar.findmypatient.helpers.IMatcher;
import fr.gklomphaar.findmypatient.helpers.MatchUserName;

/**
 * Contains the logic for modifying the users in the system.
 *
 */
public class UserManagement implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private IDataDAO<SystemUser> userDAO;
	private UserAuthority userAuthority;
	private final MatchUserName userMatcher = new MatchUserName();

	/**
	 * Creates a new instance of the SystemUser management
	 * @param userDAO DAO which will take care of the CRUD operations
	 * @param userAuthority authority manager
	 */
	public UserManagement(IDataDAO<SystemUser> userDAO, UserAuthority userAuthority)
	{
		this.userDAO = userDAO;
		this.userAuthority = userAuthority;
	}
	
	/**
	 * Add a new user
	 * Username should be unique
	 * @param user SystemUser to be added
	 * @throws NoAuthorityException
	 * @throws UserAlreadyExistsException
	 * @throws DaoSaveObjectException  
	 */
	public void add(SystemUser user) throws NoAuthorityException, UserAlreadyExistsException, DaoSaveObjectException
	{
		checkMinimalRights(UserRights.ReadWriteAndUserManagement);
		
		List<SystemUser> foundUsers;
		// Check if the uses does not already exists
		try {
			foundUsers = this.userDAO.search(user, userMatcher);
		} catch (DaoLoadObjectException e) {
			// There was a problem with searching, so saving is impossible.
			throw new DaoSaveObjectException(user,e);
		}
		
		// Exception if the user already exists
		if(foundUsers.size() > 0){
			throw new UserAlreadyExistsException();
		}
		
		// SystemUser does not exists, so add it
		this.userDAO.create(user);
	}
	/**
	 * Delete the user
	 * @param user SystemUser to be deleted
	 * @throws NoAuthorityException
	 * @throws DaoSaveObjectException 
	 */
	public void delete(SystemUser user) throws NoAuthorityException, DaoSaveObjectException
	{
		checkMinimalRights(UserRights.ReadWriteAndUserManagement);
		this.userDAO.delete(user);
	}
	/**
	 * Update the SystemUser
	 * @param user SystemUser to be updated
	 * @throws NoAuthorityException
	 * @throws DaoSaveObjectException 
	 */
	public void update(SystemUser user) throws NoAuthorityException, DaoSaveObjectException
	{
		checkMinimalRights(UserRights.ReadWriteAndUserManagement);
		this.userDAO.update(user);
	}
	
	/**
	 * Find a user using a specific matcher
	 * @param user SystemUser to be find
	 * @param matcher Matched containing match logic
	 * @return a List of found Users
	 * @throws NoAuthorityException
	 * @throws DaoLoadObjectException 
	 */
	public List<SystemUser> find(SystemUser user, IMatcher<SystemUser> matcher) throws NoAuthorityException, DaoLoadObjectException
	{
		checkMinimalRights(UserRights.ReadWriteAndUserManagement);
		return this.userDAO.search(user, userMatcher);
	}

	/**
	 * Checks if the current user has the minimal authority level
	 * @param rights minimal right level
	 * @throws NoAuthorityException
	 */
	private void checkMinimalRights(UserRights rights) throws NoAuthorityException
	{
		if(this.userAuthority.getUserRights().getValue() >= rights.getValue()){
			return;
		}
		else{
			throw new NoAuthorityException();
		}
	}

	/**
	 * Set the userDAO
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(IDataDAO<SystemUser> userDAO) {
		this.userDAO = userDAO;
	}
}
