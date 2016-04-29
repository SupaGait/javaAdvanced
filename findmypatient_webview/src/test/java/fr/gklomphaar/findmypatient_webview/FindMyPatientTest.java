package fr.gklomphaar.findmypatient_webview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.User;
import fr.gklomphaar.findmypatient_webview.servlet.DeletePatient;

@RunWith(SpringJUnit4ClassRunner.class) //This is to tell Junit to run with spring
@ContextConfiguration(locations={"file:WebContent/WEB-INF/applicationContext.xml"}) // to tell spring to load the required context
public class FindMyPatientTest extends Mockito
{
	@Autowired
	UserHybernateDAO userDAO;
	
	@Autowired
	PatientHybernateDAO patientDAO;
	
	@Test
	public void testObjectAutoWiring(){
		Assert.assertNotNull(userDAO);
		Assert.assertNotNull(patientDAO);
	}
	
	@Test
	public void resetDataBase() throws DaoSaveObjectException {
		User adminUser = new User("admin", "admin");
		userDAO.create(adminUser); 
		
		/*Patient patient = new Patient();
		patient.setFirstName("Gerard");
		patient.setLastName("Klomphaar");
		patientDAO.create(patient);*/
	}
	 
	@Test
	public void deletePatientServletTest() throws ServletException, IOException{
/*
		// Create necessary stubs
		HttpServletRequest stubHttpRequest = mock(HttpServletRequest.class);       
		HttpServletResponse stubHttpResponse = mock(HttpServletResponse.class); 
		HttpSession stubHttpSession = mock(HttpSession.class);
		
		// Writers
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		StringReader sr = new StringReader("{\"id\":\"1\"}");
		BufferedReader br = new BufferedReader(sr); 
		
		// Set stub behavior
		when(stubHttpRequest.getServletPath()).thenReturn("/DeletePatient");
		when(stubHttpRequest.getMethod()).thenReturn("GET");
	    when(stubHttpRequest.getPathInfo()).thenReturn("/");
	        
		// Response
		when(stubHttpResponse.getWriter()).thenReturn(pw);
		// Request
		when(stubHttpRequest.getReader()).thenReturn(br);
				
		//when(stubHttpRequest.getParameter(“firstname”)).thenReturn(“Kapil”);
		
		// Do the delete
		DeletePatient deletePatientServlet = new DeletePatient();
		deletePatientServlet.service(stubHttpRequest, stubHttpResponse);*/
	}
}
