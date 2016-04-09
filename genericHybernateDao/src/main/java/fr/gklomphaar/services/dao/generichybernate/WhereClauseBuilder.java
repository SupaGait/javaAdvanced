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
 * @author Gerard
 *
 */
public class WhereClauseBuilder {
	
	private Class<?> dataClass;
	private String tableName;
	private List<String> clauses;
	private Map<String, String> simpleClauseMap;
	private Map<String, WhereClause> methodClauseMap;
	
	public class WhereClause{
		private String whereClause;
		private Method instanceGetMethod;
		private String assignName;
		
		public WhereClause(Class<?> dataClass, String whereClause, String assignName, Method instanceGetMethod) {
			this.whereClause = whereClause;
			this.instanceGetMethod = instanceGetMethod;
			this.assignName = assignName;
		}
		public String getClauseQuery(){
			return whereClause;
		}
		public String getAssignName(){
			return assignName;
		}
		public Object getValue(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			return instanceGetMethod.invoke(obj, (Object[])null);
		}
	}
	
	public WhereClauseBuilder(Class<?> dataClass){
		this.dataClass = dataClass;
		this.clauses = new ArrayList<String>();
		this.simpleClauseMap = new HashMap<>();
		this.methodClauseMap = new HashMap<>();
		init();
	}

	public void update() {
		init();
	}
	
	public List<String> getStringClauses() {
		return this.clauses;
	}
	public Map<String, String> getSimpleClauses(){
		return this.simpleClauseMap;
	}
	public Map<String, WhereClause> getWhereClauses(){
		return this.methodClauseMap;
	}
	
	private void init() {
		// Extract the class annotation
		final Annotation[] classAnnotations = dataClass.getAnnotations();
		
		// Get the table name
		// Check for an Table Annotation/*
		for (Annotation annotation : classAnnotations) {
			if(annotation instanceof javax.persistence.Table) {
				this.tableName = ((javax.persistence.Table)annotation).name();
			}
		}
		// No class Table name annotation found, used class name.
		if(this.tableName == null) {
			this.tableName = this.dataClass.getSimpleName();
		}
		
		// Generate all possible where clauses
		buildWhereClauses();
	}
	
	private void buildWhereClauses() {
		this.clauses.clear();
		
		// Extract fields
		final Field[] classFfields = this.dataClass.getDeclaredFields();
		for (Field field : classFfields) {
			// Get field name, default used it as DB access field
			String fieldName = field.getName();
			String fieldDbName = fieldName;
			
			// Get annotation of the found field
			final Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
				if(annotation instanceof javax.persistence.Column) {
					fieldDbName = ((javax.persistence.Column)annotation).name();
				}
			}
			// Generate a where clause
			String whereClause = String.format("FROM %s %s_object WHERE %s_object.%s =:%s", 
					this.tableName, this.tableName, this.tableName, fieldDbName, fieldName);
			
			// Simple clause map
			this.clauses.add(whereClause);
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
	
	//from Identiy identity where identity.email =:email
	
}
