package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient_webview.JSONResult;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Login() {
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	// Check for logout
    	String logoutParameter = req.getParameter("logout");
    	if(logoutParameter!=null && logoutParameter.equals("yes")) {
    		userController.getUserAuthority().logout();
    		
    		// Hide menu
    		req.getSession().setAttribute("showMenu", false);
    	}
    	
    	// Login
    	if(userController.getUserAuthority().getUserRights().getValue() > UserRights.None.getValue())
    	{
	    	// Forward to the welcome page
	        try {
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/welcome.jsp");
	            rd.forward(req, resp);
	            
	            // Set show menu
	            req.getSession().setAttribute("showMenu", true);
	            
	        } catch (Exception e) {
	            //TODO
	        }
    	}
    	else
    	{
    		// Forward to the login page
	        try {
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/login.jsp");
	            rd.forward(req, resp);
	        } catch (Exception e) {
	            //TODO
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
