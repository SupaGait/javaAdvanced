/**
 * 
 */
package fr.gklomphaar.services.dao.generichybernate;

import java.lang.annotation.Annotation;
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
	
	/**
	 * Helper class for whereClauses
	 */
	public class WhereClause{
		private String whereClause;
		private Method instanceGetMethod;
		private String assignName;
		
		/**
		 * Helper class for Where Clauses
		 * @param dataClass Class object where the where clauses will be created for
		 * @param whereClause query clause
		 * @param assignName name of the assignment part in where clause
		 * @param instanceGetMethod method used for getting value
		 */
		public WhereClause(Class<?> dataClass, String whereClause, String assignName, Method instanceGetMethod) {
			this.whereClause = whereClause;
			this.instanceGetMethod = instanceGetMethod;
			this.assignName = assignName;
		}
		/**
		 * @return Where query string
		 */
		public String getClauseQuery(){
			return whereClause;
		}
		/**
		 * @return Assignment string used in where query (....:='Assignment')
		 */
		public String getAssignName(){
			return assignName;
		}
		/**
		 * Try to get the value of the field using reflection.
		 * Suspected method is generated based on field name
		 * 
		 * @param obj the object to get the value from.
		 * @return The value object using the get method on the object.
		 * @throws IllegalAccessException
		 * @throws IllegalArgumentException
		 * @throws InvocationTargetException
		 */
		public Object getValue(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			return instanceGetMethod.invoke(obj, (Object[])null);
		}
	}
	
	/**
	 * @param dataClass type of data to create clauses for
	 */
	public WhereClauseBuilder(Class<?> dataClass){
		this.dataClass = dataClass;
		this.simpleClauseMap = new HashMap<>();
		this.methodClauseMap = new HashMap<>();
		generateClauseMaps();
	}

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
		
		// Get the table name
		String tableName = this.dataClass.getSimpleName();
		
		// Generate all possible where clauses
		final Field[] classFfields = this.dataClass.getDeclaredFields();
		for (Field field : classFfields) {
			String fieldName = field.getName();
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
