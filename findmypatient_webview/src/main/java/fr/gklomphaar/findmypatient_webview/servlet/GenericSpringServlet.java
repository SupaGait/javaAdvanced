package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.gklomphaar.findmypatient_webview.UserController;

public class GenericSpringServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Autowired
	UserController userController;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// Set Servlet context
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	/**
	 * Interpret the content of a request as a JSON object
	 * 
	 * @param request the request to interpret the data from
	 * @return JSON object with data
	 * @throws IOException
	 */
	protected JSONObject getRequestAsJson(HttpServletRequest request) throws IOException{
		// Read the buffer to get all data
		String str = "";
		String part = "";
		BufferedReader reader = request.getReader();
		while(part != null) {
			str += part;
			part = reader.readLine();
		}
		// Enable for debugging:
		//System.out.println(request.getServletPath()+" Received: "+str);
				
		return new JSONObject(str);
	}
}
