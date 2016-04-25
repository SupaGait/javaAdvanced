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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import fr.gklomphaar.findmypatient_webview.servlet.DeletePatient;

/**
 * Unit test for find my patient
 */
public class FindMyPatientTest extends Mockito
{
	 
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
