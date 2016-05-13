package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.services.WhereClauseBuilder;
import fr.gklomphaar.services.exception.WhereClauseGenerateException;

/**
 * Servlet implementation class CreatePatient
 */
@WebServlet("/ListPatients")
public class ListPatients extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;
	private List<String> searchFields;

    public ListPatients() throws WhereClauseGenerateException {
        super();
        // Get the search fields from the Patient model
        WhereClauseBuilder whereClauseBuilder = new WhereClauseBuilder(Patient.class);
        whereClauseBuilder.init();
		this.searchFields = whereClauseBuilder.getFields();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check for minimal read rights
		if( userController.getUserAuthority().getUserRights().getValue() > UserRights.None.getValue())
		{
			List<Patient> patientList = null;
	
			// Read the patients
			try {
				patientList = userController.getPatientManagement().readAll();
				
			} catch (NoAuthorityException | DaoLoadObjectException e1) {
				response.getWriter().append("Internal server error.");
			}
			// Pass in the session for to the JSP
	        request.getSession().setAttribute("patientsList", patientList);
	        
	        // Set search fields
	        request.getSession().setAttribute("searchFieldList", searchFields);
	        
	        // Dispatch request to the JSP
	        try {
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/listPatients.jsp");
	            rd.forward(request, response);
	        } catch (Exception e) {
	        	response.getWriter().append("Internal server error.");
	        }
		}
		else {
			// If no rights, forward to the MainPage servlet
	        try {
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/MainPage");
	            rd.forward(request, response);
	        } catch (Exception e) {
	        	response.getWriter().append("Internal server error.");
	        }
		}
			
	}
}
