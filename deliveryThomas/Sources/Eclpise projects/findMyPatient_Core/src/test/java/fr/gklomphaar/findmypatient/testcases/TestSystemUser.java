package fr.gklomphaar.findmypatient.testcases;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoInitializationException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.SystemUser;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient.dao.GenericHybernateDAO;

@RunWith(SpringJUnit4ClassRunner.class) //This is to tell Junit to run with spring
@ContextConfiguration(locations={"application-context.xml"}) // to tell spring to load the required context
public class TestSystemUser {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Before
	public void beforeEachTest(){
		// Delete all users
		Session session = sessionFactory.openSession();
		session.createQuery("delete from "+SystemUser.class.getSimpleName()).executeUpdate();
	}
	@After
	public void afterEatchTest() {
		// Nothing yet
	}
	
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

	@Test
	public void testAddAndReadSystemUser() throws DaoLoadObjectException, DaoSaveObjectException, DaoInitializationException {
		GenericHybernateDAO<SystemUser> userDao = new GenericHybernateDAO<SystemUser>(SystemUser.class, this.sessionFactory);
		
		// Add test
		userDao.create(new SystemUser("admin","admin",UserRights.ReadWriteAndUserManagement));
		userDao.create(new SystemUser("sjohn","pass",UserRights.ReadOnly));
		
		// Read DAO, and test number of entities
		final List<SystemUser> readAllList = userDao.readAll();
		org.junit.Assert.assertEquals(2, readAllList.size());

		// Print all identities		
		for (SystemUser user : readAllList) {
			System.out.println(user.toString());
		}
	}
}