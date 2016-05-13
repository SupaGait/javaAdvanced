package fr.gklomphaar.findmypatient.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoInitializationException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.helpers.IMatcher;
import fr.gklomphaar.services.WhereClause;
import fr.gklomphaar.services.WhereClauseBuilder;
import fr.gklomphaar.services.exception.HybernateQueryException;
import fr.gklomphaar.services.exception.WhereClauseGenerateException;

/**
 * Generic DAO implementation which can be used on different data models
 * 
 * @author Gerard
 * @param <DataType> class on which CRUD operations will be done
 */
public class GenericHybernateDAO<DataType> implements IDataDAO<DataType>, Serializable {
	private static final long serialVersionUID = 1L;
	
	private transient SessionFactory sessionFactory;
	private Class<DataType> typeClass;
	private WhereClauseBuilder whereClauseBuilder;
	
	/**
	 * Create a new GenericHybernateDAO based on the given DataType
	 * 
	 * @param typeClass
	 * @throws WhereClauseGenerateException 
	 */
	public GenericHybernateDAO(Class<DataType> typeClass,SessionFactory sessionFactory) throws DaoInitializationException {
		this.typeClass = typeClass;
		this.sessionFactory = sessionFactory;
		this.whereClauseBuilder = new WhereClauseBuilder(typeClass);
		
		// Catch exception from dynamic where clause generation.
		try {
			this.whereClauseBuilder.init();
		} catch (WhereClauseGenerateException e) {
			throw new DaoInitializationException(this, e);
		}
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
			Query query;
			try {
				query = WhereClauseBuilder.generateHybernateQuery(data, whereClauses, session);
			} catch (HybernateQueryException e) {
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
	
	/**
	 * @return List of Strings with possible search fields
	 */
	public List<String> getSearchFields(){
		return whereClauseBuilder.getFields();
	}
}