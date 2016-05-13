/**
 * 
 */
package fr.gklomphaar.findmypatient.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import fr.gklomphaar.findmypatient.datamodel.SystemUser;


public class UserJDBCDAO extends JDBCDAO<SystemUser> {

	public UserJDBCDAO(DataSource dataSource)
	{
		// Pass the table name
		super("USERS", dataSource);
	}

	/* (non-Javadoc)
	 * @see fr.gklomphaar.findmypatient.dao.JDBCDAO#parseQueryResultSet(java.sql.ResultSet)
	 */
	@Override
	protected List<SystemUser> parseQueryResultSet(ResultSet resultSet) throws SQLException {
		List<SystemUser> userList = new ArrayList<SystemUser>();
		
		// Iterate through the results and add to list
		while (resultSet.next()) {
			String name = resultSet.getString("NAME");
			String pass = resultSet.getString("PASSWORD");
			String rights = resultSet.getString("RIGHTS");
			int id = resultSet.getInt("ID");

			SystemUser user = new SystemUser(name, pass, rights, id);
			userList.add(user);
		}
		return userList;
	}

	/* (non-Javadoc)
	 * @see fr.gklomphaar.findmypatient.dao.JDBCDAO#insertData(java.lang.Object)
	 */
	@Override
	protected PreparedStatement insertData(SystemUser user) throws SQLException {
		PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO USERS(NAME, PASSWORD) VALUES(?,?)");
		stmt.setString(1, user.getName());
		stmt.setString(2, user.getPassword());
		return stmt;
	}

	/* (non-Javadoc)
	 * @see fr.gklomphaar.findmypatient.dao.JDBCDAO#updateData(java.lang.Object)
	 */
	@Override
	protected PreparedStatement updateData(SystemUser user) throws SQLException {
		PreparedStatement stmt = this.connection.prepareStatement("UPDATE USERS SET NAME=?,PASSWORD=? WHERE ID=?");
		stmt.setString(1, user.getName());
		stmt.setString(2, user.getPassword());
		stmt.setInt(3, user.getId());
		return stmt;
	}

	/* (non-Javadoc)
	 * @see fr.gklomphaar.findmypatient.dao.JDBCDAO#deleteData(java.lang.Object)
	 */
	@Override
	protected PreparedStatement deleteData(SystemUser user) throws SQLException {
		PreparedStatement stmt = this.connection.prepareStatement("DELETE FROM USERS WHERE ID=?");
		stmt.setInt(3, user.getId());
		return stmt;
	}
}
