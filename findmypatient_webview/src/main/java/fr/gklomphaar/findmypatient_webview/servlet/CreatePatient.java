package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient_webview.JSONResult;

/**
 * Servlet implementation class CreatePatient
 */
@WebServlet("/CreatePatient")
public class CreatePatient extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;

    public CreatePatient() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(userController.getUserAuthority().getUserRights().getValue() > UserRights.None.getValue()){
			// Dispatch request to the JSP
	        try {
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/createPatient.jsp");
	            rd.forward(request, response);
	        } catch (Exception e) {
	            e.printStackTrace();
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Check for minimal WRITE rights
		if(userController.getUserAuthority().getUserRights().getValue() > UserRights.ReadWrite.getValue()){
			// Interpret input data as a JSON object
			JSONObject jsonRequest = getRequestAsJson(request);
			JSONObject jsonResult;
			
			// Get the parameters
			try {
				String firstName = jsonRequest.getString("firstName");
				String lastName = jsonRequest.getString("lastName");
				String dateOfBirth = jsonRequest.getString("dateOfBirth");
				String roomNumber = jsonRequest.getString("roomNumber");
				String socialSecurityNumber = jsonRequest.getString("socialSecurityNumber");
				String telephoneNumber = jsonRequest.getString("telephoneNumber");
				String email = jsonRequest.getString("email");
				
				System.out.println(firstName+" "+lastName+" "+email);
				
				// Create the new patient
				Patient newPatient = new Patient();
				newPatient.setFirstName(firstName);
				newPatient.setLastName(lastName);
				newPatient.setDateOfBirth(dateOfBirth);
				newPatient.setRoomNumber(roomNumber);
				newPatient.setSocialSecurityNumber(socialSecurityNumber);
				newPatient.setTelephoneNumber(telephoneNumber);
				newPatient.setEmail(email);
				
				// Save the new patient
				try {
					userController.getPatientManagement().add(newPatient);
					jsonResult = JSONResult.CreateSimpleMessage(true, "Thank you for the new user.");
					
				} catch (DaoSaveObjectException e) {
					jsonResult = JSONResult.CreateSimpleMessage(false, "Error while creating the new patient.");
				} catch (NoAuthorityException e) {
					jsonResult = JSONResult.CreateSimpleMessage(false, "Not sufficient rights to peform the requested action.");
				}
				
			} catch (JSONException e) {
				jsonResult = JSONResult.CreateSimpleMessage(false, "Error while retrieving patient info.");
			}
			
			// Write the resulting JSON string back
			response.setContentType("application/json");
			jsonResult.write(response.getWriter());
		}
	}
}
