package fr.gklomphaar.GenericHybernateDao;

import java.sql.Connection;
import java.sql.SQLException;
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

import fr.gklomphaar.findmypatient.datamodel.Patient;

@RunWith(SpringJUnit4ClassRunner.class) //This is to tell Junit to run with spring
@ContextConfiguration(locations={"application-context.xml"}) // to tell spring to load the required context
public class HybernateGenericDaoTest {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Test
	public void springSetup(){
		Assert.notNull(ds);
	}
/*
	@Test
	public void dataSourceUsage() throws SQLException{
		Connection connection = ds.getConnection();
		System.out.println(connection.getSchema());
		connection.close();
		
	}
	
	
	@Test
	public void sessionFactoryUsage(){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(new Patient());
		tx.commit();
	}
	
	
	@Test
	public void hibernateSearch(){
		Session session = sessionFactory.openSession();
		List<Patient> identities = session.createQuery("from Identity").list();
		System.out.println(identities);
		
	}*/
}