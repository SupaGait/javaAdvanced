package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
 * Servlet implementation class ModifyPatient
 */
@WebServlet("/ModifyPatient")
public class ModifyPatient extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;
	
    public ModifyPatient() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Check for minimal WRITE rights
		if(userController.getUserAuthority().getUserRights().getValue() > UserRights.ReadWrite.getValue()){
			// Interpret input data as a JSON object
			JSONObject jsonRequest = getRequestAsJson(request);
			JSONObject jsonResult;
			
			// Get the parameters
			try {
				int id = jsonRequest.getInt("id");
				
				String firstName = jsonRequest.getString("firstName");
				String lastName = jsonRequest.getString("lastName");
				String dateOfBirth = jsonRequest.getString("dateOfBirth");
				String roomNumber = jsonRequest.getString("roomNumber");
				String socialSecurityNumber = jsonRequest.getString("socialSecurityNumber");
				String telephoneNumber = jsonRequest.getString("telephoneNumber");
				String email = jsonRequest.getString("email");
				
				System.out.println(id + " " +firstName+" "+lastName+" "+email);
				
				// Set the values of the new patient
				Patient modifyPatient = new Patient();
				modifyPatient.setId(id);
				modifyPatient.setFirstName(firstName);
				modifyPatient.setLastName(lastName);
				modifyPatient.setDateOfBirth(dateOfBirth);
				modifyPatient.setRoomNumber(roomNumber);
				modifyPatient.setSocialSecurityNumber(socialSecurityNumber);
				modifyPatient.setTelephoneNumber(telephoneNumber);
				modifyPatient.setEmail(email);
				
				// Save the patient, its updated based on Id
				try {
					userController.getPatientManagement().modify(modifyPatient);
					jsonResult = JSONResult.CreateSimpleMessage(true, "Patient updated");
					
				} catch (DaoSaveObjectException e) {
					jsonResult = JSONResult.CreateSimpleMessage(false, "Error while updating the patient.");
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
