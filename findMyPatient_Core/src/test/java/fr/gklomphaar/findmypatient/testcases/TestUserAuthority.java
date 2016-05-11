package fr.gklomphaar.findmypatient.testcases;

import static org.junit.Assert.*;

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
import fr.gklomphaar.findmypatient.datamodel.UserAuthority;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient.dao.GenericHybernateDAO;

@RunWith(SpringJUnit4ClassRunner.class) //This is to tell Junit to run with spring
@ContextConfiguration(locations={"application-context.xml"}) // to tell spring to load the required context
public class TestUserAuthority {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Before
	public void beforeEachTest() throws DaoSaveObjectException, DaoInitializationException{
		// Delete all users
		Session session = sessionFactory.openSession();
		session.createQuery("delete from "+SystemUser.class.getSimpleName()).executeUpdate();
		
		// Add admin
		GenericHybernateDAO<SystemUser> userDao = new GenericHybernateDAO<SystemUser>(SystemUser.class, this.sessionFactory);
		userDao.create(new SystemUser("admin", "admin", UserRights.ReadWriteAndUserManagement));
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
	public void testUserAuthority() throws DaoLoadObjectException, NoAuthorityException, DaoInitializationException{
		// Create a user DAO
		GenericHybernateDAO<SystemUser> userDao = new GenericHybernateDAO<SystemUser>(SystemUser.class, this.sessionFactory);
		
		// Test Authority functionality
		UserAuthority userAuthority = new UserAuthority(userDao);
		
		// Log in as admin and read and get rights
		userAuthority.login("admin", "admin");
		System.out.println(String.format("Logged in as: %s, Rights: %s", 
				userAuthority.getUserName(),
				userAuthority.getUserRights()) );
		
		// Test logout
		userAuthority.logout();
		assertEquals("", userAuthority.getUserName());
		// Test without password
		{
			boolean userRejected = false;
			try {
				userAuthority.login("admin", "");
			} catch (NoAuthorityException e) {
				userRejected = true;
			}
			if(!userRejected)
				fail("Password check not working");
		}
		
		// Test unknown user, should be rejected
		{
			boolean userRejected = false;
			try {
				userAuthority.login("348942njasdh", "");
			} catch (NoAuthorityException e) {
				userRejected = true;
			}
			if(!userRejected)
				fail("Rejecting unkown users not working");
		}
	}

}