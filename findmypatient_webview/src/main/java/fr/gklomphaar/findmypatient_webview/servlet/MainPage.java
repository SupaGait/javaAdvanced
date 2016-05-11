package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import fr.gklomphaar.findmypatient.dao.UserHybernateDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.SystemUser;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient_webview.Configuration;
import fr.gklomphaar.findmypatient_webview.JSONResult;

/**
 * Servlet implementation class Login
 */
@WebServlet(urlPatterns = {"/index.html", "/ConfigWebsite", "/MainPage"})
public class MainPage extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;
 
	@Autowired
	UserHybernateDAO userDAO;
	
	@Autowired
	Configuration configuration;
	
    /**
     * Default constructor. 
     */
    public MainPage() {
    }
    
	@Override 
	public void init() throws ServletException {
		super.init();
	}
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	boolean configured = false;
    	
    	// Check if configured
    	try {
			configured = configuration.isConfigured();
		} catch (DaoLoadObjectException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	System.out.println(request.getServletPath()+" configured: " + configured);
    	
    	if(configured)
    	{
	    	if( userController.getUserAuthority().getUserRights().getValue() > UserRights.None.getValue())
	    	{
		    	// Forward to the welcome page
		        try {
		            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/welcome.jsp");
		            rd.forward(request, response);
		        } catch (Exception e) {
		        	response.getWriter().append("Server error");
		        }
	    	}
	    	else
	    	{
		    	// Forward to the login page
		        try {
		            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/login.jsp");
		            rd.forward(request, response);
		        } catch (Exception e) {
		        	response.getWriter().append("Server error");
		        }
	    	}
    	}
    	else
    	{
	    	// Forward to the configuration page
	        try {
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/configuration.jsp");
	            rd.forward(request, response);
	        } catch (Exception e) {
	        	response.getWriter().append("Server error");
	        }
    	}
    	
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Interpret input data as a JSON object
		JSONObject jsonRequest = getRequestAsJson(request);
		JSONObject jsonResult = new JSONObject();
		
		// Get the parameters
		try {
			String adminName = jsonRequest.getString("userName");
			String adminPassword = jsonRequest.getString("password");
			
			System.out.println(adminName+" "+adminPassword);
			
			// Create the new admin SystemUser
			SystemUser newUser = new SystemUser(adminName, adminPassword, UserRights.ReadWriteAndUserManagement);

			// Save the new user
			try {
				userDAO.create(newUser);
				
				// Admin set, configuration OK
				configuration.setConfigured(true);
				
				jsonResult = JSONResult.CreateSimpleMessage(true, "New admin set");
			} catch (DaoSaveObjectException e) {
				jsonResult = JSONResult.CreateSimpleMessage(false, "Error while creating the user.");
			}
			
		} catch (JSONException e) {
			jsonResult = JSONResult.CreateSimpleMessage(false, "Error while retrieving admin info.");
		}
		
		// Write the resulting JSON string back
		response.setContentType("application/json");
		jsonResult.write(response.getWriter());
	}
}
