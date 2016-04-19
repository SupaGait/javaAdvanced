package fr.gklomphaar.findmypatient.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.helpers.IMatcher;
import fr.gklomphaar.services.WhereClauseBuilder.WhereClause;

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

	//@Override
	//TODO:Use correct search
	public List<DataType> search(DataType data, WhereClause whereClause) throws DaoLoadObjectException {

		// New session
		Session session = sessionFactory.openSession();
		
		// Construct the query
		// TODO: Use a better method, this is not maintainable and only Date en String supported
		Query query = session.createQuery(whereClause.getClauseQuery());
		try {
			final Object valueObject = whereClause.getValue(data);
			final String assignName = whereClause.getAssignName();
			if(valueObject instanceof String) {
				query.setString(assignName, (String)valueObject);
			}
			else if(valueObject instanceof Date) {
				query.setDate(assignName, (Date)valueObject);
			}
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new DaoLoadObjectException(this, e);
		}
		return query.list();
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

	@Override
	public List<DataType> search(DataType data, IMatcher<DataType> matcher) throws DaoLoadObjectException {
		// TODO Auto-generated method stub
		return null;
	}
	
}