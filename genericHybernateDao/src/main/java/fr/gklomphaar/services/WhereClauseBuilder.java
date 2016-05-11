/**
 * 
 */
package fr.gklomphaar.services;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import fr.gklomphaar.services.exception.HybernateQueryException;
import fr.gklomphaar.services.exception.WhereClauseGenerateException;

/**
 * Build where clauses based on the Data type given
 * 
 * @author Gerard
 *
 */
public class WhereClauseBuilder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<?> dataClass;
	private Map<String, String> simpleClauseMap;
	private Map<String, WhereClause> methodClauseMap;
	private List<String> fields;
		
	/**
	 * Create a whereclause builder to generate whereclauses
	 * init method needs to be called to generate the whereClauses
	 * 
	 * @param dataClass type of data to create clauses for
	 */
	public WhereClauseBuilder(Class<?> dataClass){
		this.dataClass = dataClass;
		this.simpleClauseMap = new HashMap<>();
		this.methodClauseMap = new HashMap<>();
		this.fields = new ArrayList<String>();
	}
	
	/**
	 * Generate where clauses
	 * @throws WhereClauseGenerateException 
	 */
	public void init() throws WhereClauseGenerateException {
		generateClauseMaps();
	}

	/**
	 * Update the generated clause map.
	 * @throws WhereClauseGenerateException 
	 */
	public void update() throws WhereClauseGenerateException {
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
	
	private void generateClauseMaps() throws WhereClauseGenerateException {
		this.simpleClauseMap.clear();
		this.methodClauseMap.clear();
		this.fields.clear();
		
		// Get the table name
		String tableName = this.dataClass.getSimpleName();
		
		// Generate all possible where clauses
		final Field[] classFfields = this.dataClass.getDeclaredFields();
		for (Field field : classFfields) {
			
			// Get field, skipp if its the serialVersionUID field
			String fieldName = field.getName();
			if(fieldName.equals("serialVersionUID"))
				continue;
			
			// add to list
			this.fields.add(fieldName);
			
			// Generate a where clause
			String whereClauseFrom = String.format("FROM %s %s_object ", tableName, tableName); 
			String whereClauseWhere = String.format("%s_object.%s =:%s", tableName, fieldName, fieldName);
			
			// Simple clause map
			this.simpleClauseMap.put(fieldName, whereClauseFrom+whereClauseWhere);
			
			// Method clause map, search for getter using reflection
			Method instanceGetMethod = null;
			try {
				String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
				instanceGetMethod = this.dataClass.getDeclaredMethod(methodName, (Class<?>[])null);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new WhereClauseGenerateException(e);
			}
			
			WhereClause whereClause = new WhereClause(this.dataClass, fieldName, whereClauseFrom, whereClauseWhere, fieldName, instanceGetMethod);
			this.methodClauseMap.put(fieldName, whereClause);
		}
	}
	
	/**
	 * Generate an Hybernate search query using a list of whereClauses for a specific object 
	 * where the values to match are retrieved from.
	 * 
	 * @param dataObject object which is used as data source
	 * @param whereClauses for the specific object
	 * @param session the session
	 * @return a querry based on the wherecluases and the values of the given data object
	 * @throws HybernateQueryException
	 */
	public static Query generateHybernateQuery(Object dataObject, List<WhereClause> whereClauses, Session session) throws HybernateQueryException{
		
		// Build the query String and create the query
		String queryString = whereClauses.get(0).getWhereClauseFrom();
		boolean firstWhere = true;
		for (WhereClause whereClause : whereClauses) {
			if(!firstWhere){
				queryString += " AND ";
			}
			else {
				queryString += " WHERE ";
				firstWhere = false;
			}
			
			queryString += whereClause.getWhereClauseWhere();
		}
		Query query = session.createQuery(queryString);
		
		// Fill the query with name and values
		try {
			for(WhereClause whereClause : whereClauses){
				// TODO: Use a better method, this is not maintainable and only Date and String supported
				final Object valueObject = whereClause.getValue(dataObject);
				final String assignName = whereClause.getAssignName();
				if(valueObject instanceof String) {
					query.setString(assignName, (String)valueObject);
				}
				else if(valueObject instanceof Integer){
					query.setInteger(assignName, (int)valueObject);
				}
				else if(valueObject instanceof Date) {
					query.setDate(assignName, (Date)valueObject);
				}
			}				
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new HybernateQueryException(e);
		}
		return query;
	}
}
