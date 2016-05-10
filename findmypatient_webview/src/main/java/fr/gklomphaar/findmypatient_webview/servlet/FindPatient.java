package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient.helpers.IMatcher;
import fr.gklomphaar.findmypatient.helpers.MatchPatientfirstName;
import fr.gklomphaar.findmypatient_webview.JSONResult;
import fr.gklomphaar.services.WhereClauseBuilder;

/**
 * Servlet implementation class FindPatient
 */
@WebServlet("/FindPatient")
public class FindPatient extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;
	private List<String> searchFields;
       
    public FindPatient() {
        super();
        this.searchFields = (new WhereClauseBuilder(Patient.class)).getFields();
    }
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check for minimal read rights
		if(userController.getUserAuthority().getUserRights().getValue() > UserRights.None.getValue()){
			// Generate the patientlist and return
	        try {
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/generatePatientList.jsp");
	            rd.forward(req, resp);
	        } catch (Exception e) {
	        	resp.getWriter().append("Internal server error");
	        }        
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 if(userController.getUserAuthority().getUserRights().getValue() > UserRights.None.getValue()){
				// Interpret input data as a JSON object
				JSONObject jsonRequest = getRequestAsJson(request);
				JSONObject jsonResult = JSONResult.CreateSimpleMessage(false, "Unkown error");
				
				// Get the parameters
				try {
					// Create the new patient
					Patient findPatient = new Patient();
					List<String> searchFieldsUsed = new ArrayList<String>();
					
					// Get the fields, use reflection to set the values
					for (String searchField : searchFields) {
						// Get the value from the request
						String value = jsonRequest.getString(searchField);
						
						// Skip empty or not available values
						if(value == null || value.equals(""))
							continue;
						
						// Add to the valid fields for use with matcher
						searchFieldsUsed.add(searchField);
						
						// Get the set method
						Method instanceSetMethod = null;
						String methodName = "set" + Character.toUpperCase(searchField.charAt(0)) + searchField.substring(1);
						if(searchField.equals("id")){
							// ID: int
							instanceSetMethod = Patient.class.getDeclaredMethod(methodName, int.class);
							instanceSetMethod.invoke(findPatient, new Object[]{Integer.parseInt(value)});
						}
						else{
							// Other: String
							instanceSetMethod = Patient.class.getDeclaredMethod(methodName, String.class);
							instanceSetMethod.invoke(findPatient, new Object[]{value});
						}
					}
					
					// Search the patients
					try {
						// Create a new matcher which uses the valid fields
						IMatcher<Patient> matcher = new IMatcher<Patient>() {
							@Override
							public String getSQLMatchStatement(String tableName, Patient data) {
								return null;
							}
							@Override
							public List<String> getFields() {
								return searchFieldsUsed;
							}
						};
						
						// Execute the search
						List<Patient> foundMatchingPatients = userController.getPatientManagement().search(findPatient, matcher);
						
						// Pass in the session for to the JSP
				        request.getSession().setAttribute("patientsList", foundMatchingPatients);
				        
						jsonResult = JSONResult.CreateSimpleMessage(true, "Found " +foundMatchingPatients.size()+" matching users");

					} catch (NoAuthorityException e) {
						jsonResult = JSONResult.CreateSimpleMessage(false, "Not sufficient rights to peform the requested action.");
					} catch (DaoLoadObjectException e) {
						jsonResult = JSONResult.CreateSimpleMessage(false, "Error while receiving patients");
					}
				} catch (JSONException e) {
					jsonResult = JSONResult.CreateSimpleMessage(false, "Error while retrieving search info.");
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Write the resulting JSON string back
				response.setContentType("application/json");
				jsonResult.write(response.getWriter());
			 }
	}

}
