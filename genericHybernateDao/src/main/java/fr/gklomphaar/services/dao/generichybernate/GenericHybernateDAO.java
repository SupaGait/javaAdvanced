package fr.gklomphaar.services.dao.generichybernate;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.gklomphaar.services.dao.IDataDAO;
import fr.gklomphaar.services.dao.IMatcher;
import fr.gklomphaar.services.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.services.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.services.dao.generichybernate.WhereClauseBuilder.WhereClause;

/**
 * @author Gerard
 *
 * @param <DataType>
 */
public class GenericHybernateDAO<DataType> implements IDataDAO<DataType> {

	private DataSource ds;	
	private SessionFactory sessionFactory;
	private Class<DataType> typeClass;
	private WhereClauseBuilder whereClauseBuilder;
	
	/**
	 * @param typeClass
	 */
	public GenericHybernateDAO(Class<DataType> typeClass,DataSource ds, SessionFactory sessionFactory) {
		this.typeClass = typeClass;
		this.ds = ds;
		this.sessionFactory = sessionFactory;
		this.whereClauseBuilder = new WhereClauseBuilder(typeClass);
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
	public List<DataType> search(DataType data, String fieldNameToMatch) throws DaoLoadObjectException {

		// New session
		Session session = sessionFactory.openSession();
		
		// Do the query
		final Map<String, WhereClause> whereClauses = whereClauseBuilder.getWhereClauses();
		WhereClause whereClause = whereClauses.get(fieldNameToMatch);
		if(whereClause == null)
			throw new DaoLoadObjectException(this, new Throwable("Unable to get where clause for field: " + fieldNameToMatch));
		
		Query query = session.createQuery(whereClause.getClauseQuery());
		try {
			query.setString(whereClause.getAssignName(), (String)whereClause.getValue(data));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace(); // TODO: remove
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
	
}
/*
public class GenericHybernateDAO implements IdentityDAOInterface {

	@Autowired
	SessionFactory factory;
	
	@Autowired
	@Qualifier("selectIdentityByEmail")
	String searchEmailQuery; 
	
	
	@Override
	public List<Identity> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Identity> search(Identity identity) {
		Session session = factory.openSession();
		
		// Do the query
		Query query = session.createQuery(searchEmailQuery);
		query.setString("email",identity.getEmail());
		
		return query.list();
	}

	@Override
	public void write(Identity identity) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(identity);
		tx.commit();
		session.close();
	}

	@Override
	public void update(Identity identity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Identity identity) {
		// TODO Auto-generated method stub

	}
}
*/