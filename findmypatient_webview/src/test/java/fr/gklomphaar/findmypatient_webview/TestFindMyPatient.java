package fr.gklomphaar.findmypatient_webview;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.gklomphaar.findmypatient.dao.PatientHybernateDAO;
import fr.gklomphaar.findmypatient.dao.UserHybernateDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.SystemUser;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;

@RunWith(SpringJUnit4ClassRunner.class) //This is to tell Junit to run with spring
@ContextConfiguration(locations={"file:WebContent/WEB-INF/applicationContext.xml"}) // to tell spring to load the required context
@WebAppConfiguration
public class TestFindMyPatient
{
	@Autowired
	UserHybernateDAO userDAO;
	
	@Autowired
	PatientHybernateDAO patientDAO;
	
	@Before
	public void beforeEachTest() throws DaoSaveObjectException, DaoLoadObjectException {
		// Delete all users
		List<SystemUser> readAll = userDAO.readAll();
		for (SystemUser systemUser : readAll) {
			userDAO.delete(systemUser);
		}
	}
	
	@Test
	public void testObjectAutoWiring(){
		Assert.assertNotNull(userDAO);
		Assert.assertNotNull(patientDAO);
	}
	
	@Test
	public void testLoginAdmin() throws NoAuthorityException, DaoLoadObjectException, DaoSaveObjectException{
		
		// Add single administrator
		userDAO.create(new SystemUser("admin", "admin", UserRights.ReadWriteAndUserManagement));
		
		UserController userController = new UserController(userDAO, patientDAO);
		userController.getUserAuthority().login("admin", "admin");
		
		String userName = userController.getUserAuthority().getUserName();
		Assert.assertEquals("admin", userName);
	}
}
