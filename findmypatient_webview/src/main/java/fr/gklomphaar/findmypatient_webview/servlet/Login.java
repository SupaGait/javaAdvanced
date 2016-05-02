package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.gklomphaar.findmypatient.controller.IdentityController;
import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.SystemUser;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient_webview.JSONResult;
import fr.gklomphaar.findmypatient_webview.UserController;
import fr.gklomphaar.findmypatient_webview.UserHybernateDAO;
import fr.gklomphaar.findmypatient.dao.GenericHybernateDAO;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;
 
	@Autowired
	UserController userController;
	
	@Autowired
	UserHybernateDAO userDAO;
	
    /**
     * Default constructor. 
     */
    public Login() {
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	//TODO: enable, check for user rights
    	//if( userController.getUserAuthority().getUserRights().getValue() > UserRights.None.getValue())
    	{
	    	// Forward to the welcome page
	        try {
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/welcomePage.jsp");
	            rd.forward(req, resp);
	        } catch (Exception e) {
	            
	        }
    	}
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Interpret input data as a JSON object
		JSONObject jsonRequest = getRequestAsJson(request);
		JSONObject jsonResult= new JSONObject();
		
		try {
			// Get the user info
			String userName = jsonRequest.getString("userName");
			String password = jsonRequest.getString("password");
			
			// Try to login
			try {
				userController.getUserAuthority().login(userName, password);
				jsonResult = JSONResult.CreateSimpleMessage(true, "Login OK, redirecting to welcome page.");
			} catch (NoAuthorityException e) {
				jsonResult = JSONResult.CreateSimpleMessage(false, "Incorrect login information.");
			} catch (DaoLoadObjectException e) {
				jsonResult = JSONResult.CreateSimpleMessage(false, "Error while trying to login, please try again later.");
			}
			
		} catch (JSONException e1) {
			jsonResult = JSONResult.CreateSimpleMessage(false, "Error while retrieving login info.");
		}
		
		// Write the resulting JSON string back
		response.setContentType("application/json");
		jsonResult.write(response.getWriter());
	}
}
