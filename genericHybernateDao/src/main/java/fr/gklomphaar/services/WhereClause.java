package fr.gklomphaar.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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