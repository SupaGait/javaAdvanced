/**
 * 
 */
package fr.gklomphaar.findmypatient.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoInitializationException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.helpers.IMatcher;

/**
 *
 * @param <DataType> DataDAO type which is saved and retrieved through the DAO methods
 *
 */
public abstract class JDBCDAO<DataType> implements IDataDAO<DataType>, IDAOManagement {

	private final String DBTableName;
	private String url;
	private String userName;
	private String password;
	protected Connection connection;

	/**
	 * Implements the logic to parse a ResultSet to a List of specific data type(s).
	 * @param resultSet
	 * @return A list of data types
	 * @throws SQLException
	 */
	protected abstract List<DataType> parseQueryResultSet(ResultSet resultSet) throws SQLException;
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * Implements the SQL logic to insert a specific dataType into the DAO
	 * @param dataType
	 * @return
	 * @throws SQLException
	 */
	protected abstract PreparedStatement insertData(DataType dataType) throws SQLException;
	/**
	 * Implements the SQL logic to update a specific dataType in the DAO
	 * @param dataType
	 * @return
	 * @throws SQLException
	 */
	protected abstract PreparedStatement updateData(DataType dataType) throws SQLException;
	/**
	 * Implements the SQL logic to delete a specific dataType from the DAO
	 * @return a PreparedStatement containing the result
	 * @throws SQLException
	 */
	protected abstract PreparedStatement deleteData(DataType dataType) throws SQLException;
	
	/**
	 * @param DBTableName name of the main table in the DB containing the elements
	 */
	public JDBCDAO(String DBTableName)
	{
		this.DBTableName = DBTableName;
	}
	
	/**
	 * @param url to the JDBC database
	 * @param name User name for connection to the DB
	 * @param password password for the connection to the DB
	 */
	public void setDatabaseConnection(String url, String name, String password)
	{
		this.url = url;
		this.userName = name;
		this.password = password;
	}
	
	@Override
	public void connect() throws DaoInitializationException {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			
			// Set up the connection
			this.connection = DriverManager.getConnection(this.url, this.userName, this.password);
			System.out.println("SQL connection opened.");
		} 
		catch (ClassNotFoundException | SQLException e) {
			throw new DaoInitializationException(this, e);
		}
	}

	@Override
	public void disconnect() {
		try {
			this.connection.close();
			System.out.println("SQL connection closed.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void create(DataType data) throws DaoSaveObjectException {
		try {
			// Get and execute the query
			PreparedStatement insertDataStmt = insertData(data);
			insertDataStmt.execute();
		} catch (Exception e) {
			throw new DaoSaveObjectException(data, e);
		}
	}

	@Override
	public List<DataType> readAll() throws DaoLoadObjectException {
		List<DataType> dataList = new ArrayList<DataType>();
		try {

			// Execute readAll query
			PreparedStatement readAllStmt = this.connection.prepareStatement("SELECT * FROM " + this.DBTableName);
			ResultSet rs = readAllStmt.executeQuery();

			// Parses the data objects
			dataList = parseQueryResultSet(rs);
			
		} catch (Exception e) {
			throw new DaoLoadObjectException(dataList.getClass(), e);
		}
		return dataList;
	}

	@Override
	public List<DataType> search(DataType data, IMatcher<DataType> matcher) throws DaoLoadObjectException {
		List<DataType> dataList = new ArrayList<DataType>();

		try {
			// Setup and execute the query
			final String sqlMatchStatement = matcher.getSQLMatchStatement(this.DBTableName, data);
			PreparedStatement prepareStatement = this.connection.prepareStatement(sqlMatchStatement);
			ResultSet rs = prepareStatement.executeQuery();
			
			// Parses the data objects
			dataList = parseQueryResultSet(rs);

		} catch (Exception e) {
			throw new DaoLoadObjectException(dataList, e);
		}
		return dataList;
	}

	@Override
	public void update(DataType data) throws DaoSaveObjectException{
		try {
			// Get and execute the query
			PreparedStatement insertDataStmt = updateData(data);
			insertDataStmt.execute();
			
		} catch (Exception e) {
			throw new DaoSaveObjectException(data, e);
		}
	}

	@Override
	public void delete(DataType data) throws DaoSaveObjectException {
		try {
			// Get and execute the query
			PreparedStatement deleteDataStmt = deleteData(data);
			deleteDataStmt.execute();
			
		} catch (Exception e) {
			throw new DaoSaveObjectException(data, e);
		}
	}


}
