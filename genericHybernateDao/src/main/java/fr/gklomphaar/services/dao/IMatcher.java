//The Imatcher file
package fr.gklomphaar.services.dao;


public interface IMatcher<DataType> {

	/**
	 * @return The SQL match statement to be executed.
	 */
	public String getSQLMatchStatement(String tableName, DataType data);
	
	/**
	 * @return name of the matcher
	 */
	@Override
	public String toString();
}
