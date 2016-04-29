package fr.gklomphaar.findmypatient.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.metamodel.relational.Datatype;

import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.helpers.IMatcher;
import fr.gklomphaar.services.WhereClause;
import fr.gklomphaar.services.WhereClauseBuilder;

/**
 * @author Gerard
 *
 * @param <DataType> class on which CRUD operations will be done
 */
public class GenericHybernateDAO<DataType> implements IDataDAO<DataType> {

	private SessionFactory sessionFactory;
	private Class<DataType> typeClass;
	
	/**
	 * @param typeClass
	 */
	public GenericHybernateDAO(Class<DataType> typeClass,SessionFactory sessionFactory) {
		this.typeClass = typeClass;
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void create(DataType data) throws DaoSaveObjectException {
		// New session
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		// Save the DataType		
		session.saveOrUpdate(data);
		tx.commit();
		session.close();
	}

	@Override
	public List<DataType> readAll() throws DaoLoadObjectException {
		// New session
		Session session = sessionFactory.openSession();
		return session.createCriteria(typeClass).list();
	}

	@Override
	public List<DataType> search(DataType data, IMatcher<DataType> matcher) throws DaoLoadObjectException {
		List<String> fields = matcher.getFields();
		List<DataType> foundObjects = null;
		
		// New session
		Session session = sessionFactory.openSession();
		
		WhereClauseBuilder whereClauseBuilder = new WhereClauseBuilder(typeClass);
		Map<String, WhereClause> dataTypeWhereClauses = whereClauseBuilder.getWhereClauses();
		
		// Find the where clauses for the given fields
		List<WhereClause> whereClauses = new ArrayList<WhereClause>();
		for (String field : fields) {
			if(dataTypeWhereClauses.containsKey(field)){
				whereClauses.add(dataTypeWhereClauses.get(field));
			}
		}
		
		// Construct the query
		if(!whereClauses.isEmpty() ){
			// Build the query String and create the query
			String queryString = whereClauses.get(0).getWhereClauseFrom();
			boolean firstWhere = true;
			for (WhereClause whereClause : whereClauses) {
				if(!firstWhere){
					queryString += " AND ";
				}
				else {
					firstWhere = false;
				}
				queryString += whereClause.getWhereClauseWhere();
			}
			Query query = session.createQuery(queryString);
			
			// Fill the query with name and values
			try {
				for(WhereClause whereClause : whereClauses){
					// TODO: Use a better method, this is not maintainable and only Date and String supported
					final Object valueObject = whereClause.getValue(data);
					final String assignName = whereClause.getAssignName();
					if(valueObject instanceof String) {
						query.setString(assignName, (String)valueObject);
					}
					else if(valueObject instanceof Date) {
						query.setDate(assignName, (Date)valueObject);
					}
				}				
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new DaoLoadObjectException(this, e);
			}
			foundObjects = query.list();
		}
		else {
			throw new DaoLoadObjectException(this, new Exception("No fields given to search."));
		}
		
		return foundObjects;
	}

	@Override
	public void update(DataType data) throws DaoSaveObjectException {
		// New session
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		// Save the DataType		
		session.saveOrUpdate(data);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(DataType data) throws DaoSaveObjectException {
		// New session
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		// Delete the DataType		
		session.delete(data);
		tx.commit();
		session.close();
	}
}