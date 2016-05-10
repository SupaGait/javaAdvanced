package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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

/**
 * Servlet implementation class FindPatient
 */
@WebServlet("/FindPatient")
public class FindPatient extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;
       
    public FindPatient() {
        super();
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
				JSONObject jsonResult;
				
				// Get the parameters
				try {
					String firstName = jsonRequest.getString("firstName");
					/*String lastName = jsonRequest.getString("lastName");
					String dateOfBirth = jsonRequest.getString("dateOfBirth");
					String roomNumber = jsonRequest.getString("roomNumber");
					String socialSecurityNumber = jsonRequest.getString("socialSecurityNumber");
					String telephoneNumber = jsonRequest.getString("telephoneNumber");
					String email = jsonRequest.getString("email");
					*/
					System.out.println("Finding patient:" + firstName);
					
					// Create the new patient
					Patient findPatient = new Patient();
					findPatient.setFirstName(firstName);
					/*newPatient.setLastName(lastName);
					newPatient.setDateOfBirth(dateOfBirth);
					newPatient.setRoomNumber(roomNumber);
					newPatient.setSocialSecurityNumber(socialSecurityNumber);
					newPatient.setTelephoneNumber(telephoneNumber);
					newPatient.setEmail(email);
					*/
					
					// Search the patients
					try {
						IMatcher<Patient> matcher = new MatchPatientfirstName();
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
				}
				
				// Write the resulting JSON string back
				response.setContentType("application/json");
				jsonResult.write(response.getWriter());
			 }
	}

}
