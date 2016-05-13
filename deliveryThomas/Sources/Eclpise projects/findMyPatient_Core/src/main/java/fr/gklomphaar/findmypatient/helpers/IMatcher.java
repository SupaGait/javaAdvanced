//The Imatcher file
package fr.gklomphaar.findmypatient.helpers;

import java.util.List;

public interface IMatcher<DataType> {

	/**
	 * @return The SQL match statement to be executed.
	 */
	public String getSQLMatchStatement(String tableName, DataType data);
	
	
	/**
	 * @return List of String containing fields used as criteria
	 */
	public List<String> getFields();
	
	/**
	 * @return name of the matcher
	 */
	@Override
	public String toString();
}
