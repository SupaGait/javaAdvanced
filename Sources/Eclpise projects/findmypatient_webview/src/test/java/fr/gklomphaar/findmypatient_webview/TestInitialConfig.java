package fr.gklomphaar.findmypatient_webview;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.gklomphaar.findmypatient.dao.UserHybernateDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.SystemUser;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;

@RunWith(SpringJUnit4ClassRunner.class) //This is to tell Junit to run with spring
@ContextConfiguration(locations={"file:WebContent/WEB-INF/applicationContext.xml"}) // to tell spring to load the required context
@ActiveProfiles("test")
@WebAppConfiguration
public class TestInitialConfig
{
	@Autowired
	UserHybernateDAO userDAO;
	
	@Autowired
	Configuration configuration;
	
	@Test
	public void testObjectAutoWiring(){
		Assert.assertNotNull(userDAO);
		Assert.assertNotNull(configuration);
	}
	
	@Before
	public void beforeEachTest() throws DaoLoadObjectException, DaoSaveObjectException{
		// Delete all users
		List<SystemUser> readAll = userDAO.readAll();
		for (SystemUser systemUser : readAll) {
			userDAO.delete(systemUser);
		}
	}
	@After
	public void afterEachTest() throws DaoLoadObjectException, DaoSaveObjectException{
		// Delete all users
		List<SystemUser> readAll = userDAO.readAll();
		for (SystemUser systemUser : readAll) {
			userDAO.delete(systemUser);
		}
	}
	
	/**
	 * Test cases:
	 *  - No users: no configuration
	 *  - 1 Admin user: configured
	 *  - 1+ Admin users: configured
	 * @throws DaoLoadObjectException 
	 */
	@Test
	public void testNoConfigu() throws DaoLoadObjectException{
		boolean configured = configuration.isConfigured();
		Assert.assertEquals(false, configured);
	}
	@Test
	public void testOneAdmin() throws DaoLoadObjectException, DaoSaveObjectException{
		userDAO.create(new SystemUser("admin","admin",UserRights.ReadWriteAndUserManagement));
		userDAO.create(new SystemUser("normalUser","pass",UserRights.ReadOnly));
		boolean configured = configuration.isConfigured();
		Assert.assertEquals(true, configured);
	}
	//@Test
	public void testMultipleAdmins() throws DaoLoadObjectException, DaoSaveObjectException{
		userDAO.create(new SystemUser("admin1","admin1",UserRights.ReadWriteAndUserManagement));
		userDAO.create(new SystemUser("admin2","admin2",UserRights.ReadWriteAndUserManagement));
		userDAO.create(new SystemUser("normalUser1","pass",UserRights.ReadOnly));
		userDAO.create(new SystemUser("normalUser2","pass",UserRights.ReadWrite));
		boolean configured = configuration.isConfigured();
		Assert.assertEquals(true, configured);
	}
}
