package fr.gklomphaar.generichybernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import fr.gklomphaar.datamodel.Identity;
import fr.gklomphaar.services.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.services.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.services.dao.generichybernate.GenericHybernateDAO;
import fr.gklomphaar.services.dao.generichybernate.WhereClauseBuilder;
//import fr.gklomphaar.findmypatient.datamodel.Patient;

@RunWith(SpringJUnit4ClassRunner.class) //This is to tell Junit to run with spring
@ContextConfiguration(locations={"application-context.xml"}) // to tell spring to load the required context
public class TestHybernateGenericDao {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Test
	public void springSetup(){
		Assert.notNull(ds);
	}

	@Test
	public void dataSourceUsage() throws SQLException{
		Connection connection = ds.getConnection();
		System.out.println(connection.getSchema());
		connection.close();
		
	}
	
	//@Test
	public void testAddAndRead() throws DaoLoadObjectException, DaoSaveObjectException {
		GenericHybernateDAO<Identity> identityDao = new GenericHybernateDAO<>(Identity.class, this.ds, this.sessionFactory);
		
		// Add test identities
		identityDao.create(new Identity("Frans","Bonnes","Frans@Bonnes.fun"));
		identityDao.create(new Identity("Sjohn","Transquit","Sjohn@Transquit.fun"));
		
		// Read DAO, and test number of entities
		final List<Identity> readAllList = identityDao.readAll();
		org.junit.Assert.assertEquals(2, readAllList.size());

		// Print all identities		
		for (Identity identity : readAllList) {
			System.out.println(identity.toString());
		}
	}
	
	@Test
	public void testFind() throws DaoSaveObjectException, DaoLoadObjectException {
		GenericHybernateDAO<Identity> identityDao = new GenericHybernateDAO<>(Identity.class, this.ds, this.sessionFactory);
		
		// Add test identities
		final Identity idFrans = new Identity("Frans","Bonnes","Frans@Bonnes.fun");
		final Identity idSjohn = new Identity("Sjohn","Transquit","Sjohn@Transquit.fun");
		
		identityDao.create(idFrans);
		identityDao.create(idSjohn);
		
		final List<Identity> searchResult = identityDao.search(idFrans, "firstName");
		org.junit.Assert.assertEquals(1, searchResult.size());
	}
	
	
	//@Test
	public void sessionFactoryUsage(){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(new Identity());
		//session.saveOrUpdate(new Patient());
		tx.commit();
	}
	
	/*	
	@Test
	public void hibernateSearch(){
		Session session = sessionFactory.openSession();
		List<Patient> identities = session.createQuery("from Identity").list();
		System.out.println(identities);
		
	}*/
}