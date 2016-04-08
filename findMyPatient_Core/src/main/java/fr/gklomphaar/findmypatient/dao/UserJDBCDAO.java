/**
 * 
 */
package fr.gklomphaar.findmypatient.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.User;
import fr.gklomphaar.findmypatient.helpers.IMatcher;


public class UserJDBCDAO extends JDBCDAO<User> {

	public UserJDBCDAO()
	{
		// Pass the table name
		super("USERS");
	}

	/* (non-Javadoc)
	 * @see fr.gklomphaar.findmypatient.dao.JDBCDAO#parseQueryResultSet(java.sql.ResultSet)
	 */
	@Override
	protected List<User> parseQueryResultSet(ResultSet resultSet) throws SQLException {
		List<User> userList = new ArrayList<User>();
		
		// Iterate through the results and add to list
		while (resultSet.next()) {
			String name = resultSet.getString("NAME");
			String pass = resultSet.getString("PASSWORD");
			String rights = resultSet.getString("RIGHTS");
			String id = resultSet.getString("ID");

			User user = new User(name, pass, rights, id);
			userList.add(user);
		}
		return userList;
	}

	/* (non-Javadoc)
	 * @see fr.gklomphaar.findmypatient.dao.JDBCDAO#insertData(java.lang.Object)
	 */
	@Override
	protected PreparedStatement insertData(User user) throws SQLException {
		PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO USERS(NAME, PASSWORD) VALUES(?,?)");
		stmt.setString(1, user.getName());
		stmt.setString(2, user.getPassword());
		return stmt;
	}

	/* (non-Javadoc)
	 * @see fr.gklomphaar.findmypatient.dao.JDBCDAO#updateData(java.lang.Object)
	 */
	@Override
	protected PreparedStatement updateData(User user) throws SQLException {
		PreparedStatement stmt = this.connection.prepareStatement("UPDATE USERS SET NAME=?,PASSWORD=? WHERE ID=?");
		stmt.setString(1, user.getName());
		stmt.setString(2, user.getPassword());
		stmt.setString(3, user.getId());
		return stmt;
	}

	/* (non-Javadoc)
	 * @see fr.gklomphaar.findmypatient.dao.JDBCDAO#deleteData(java.lang.Object)
	 */
	@Override
	protected PreparedStatement deleteData(User user) throws SQLException {
		PreparedStatement stmt = this.connection.prepareStatement("DELETE FROM USERS WHERE ID=?");
		stmt.setString(3, user.getId());
		return stmt;
	}
}
