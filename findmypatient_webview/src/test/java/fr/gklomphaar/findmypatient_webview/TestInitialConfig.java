package fr.gklomphaar.findmypatient_webview;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations.Mock;
import org.mockito.MockitoAnnotations;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.SystemUser;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient_webview.servlet.DeletePatient;
import fr.gklomphaar.findmypatient_webview.servlet.Login;

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
