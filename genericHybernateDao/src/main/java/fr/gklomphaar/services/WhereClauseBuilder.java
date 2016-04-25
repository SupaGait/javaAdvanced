/**
 * 
 */
package fr.gklomphaar.services;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Build where clauses based on the Data type given
 * 
 * @author Gerard
 *
 */
public class WhereClauseBuilder {
	
	private Class<?> dataClass;
	private Map<String, String> simpleClauseMap;
	private Map<String, WhereClause> methodClauseMap;
	private List<String> fields;
		
	/**
	 * @param dataClass type of data to create clauses for
	 */
	public WhereClauseBuilder(Class<?> dataClass){
		this.dataClass = dataClass;
		this.simpleClauseMap = new HashMap<>();
		this.methodClauseMap = new HashMap<>();
		this.fields = new ArrayList<String>();
		generateClauseMaps();
	}

	/**
	 * Update the generated clause map.
	 */
	public void update() {
		generateClauseMaps();
	}
	/**
	 * @return Map of generated where clauses
	 *  - Key is based on field
	 *  - Value contains where clause String
	 */
	public Map<String, String> getSimpleClauses(){
		return this.simpleClauseMap;
	}
	/**
	 * @return the fields of the class
	 */
	public List<String> getFields() {
		return fields;
	}
	/**
	 * @return Map of generated where clauses
	 *  - Key is based on field
	 *  - Value contains WhereClause
	 */
	public Map<String, WhereClause> getWhereClauses(){
		return this.methodClauseMap;
	}
	
	private void generateClauseMaps() {
		this.simpleClauseMap.clear();
		this.methodClauseMap.clear();
		this.fields.clear();
		
		// Get the table name
		String tableName = this.dataClass.getSimpleName();
		
		// Generate all possible where clauses
		final Field[] classFfields = this.dataClass.getDeclaredFields();
		for (Field field : classFfields) {
			
			// Get field and add to list
			String fieldName = field.getName();
			this.fields.add(fieldName);
			
			// Generate a where clause
			String whereClause = String.format("FROM %s %s_object WHERE %s_object.%s =:%s", 
					tableName, tableName, tableName, fieldName, fieldName);
			
			// Simple clause map
			this.simpleClauseMap.put(fieldName, whereClause);
			
			// Method clause map, search for getter using reflection
			Method instanceGetMethod = null;
			try {
				String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
				instanceGetMethod = this.dataClass.getDeclaredMethod(methodName, (Class<?>[])null);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace(); //TODO: remove
			}
			
			WhereClause whereClauseD = new WhereClause(this.dataClass, whereClause, fieldName, instanceGetMethod);
			this.methodClauseMap.put(fieldName, whereClauseD);
		}
	}
}
