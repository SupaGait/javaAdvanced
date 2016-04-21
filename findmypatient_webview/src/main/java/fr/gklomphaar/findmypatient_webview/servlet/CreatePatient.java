package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient_webview.UserController;

/**
 * Servlet implementation class CreatePatient
 */
@WebServlet("/CreatePatient")
public class CreatePatient extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	UserController userController;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePatient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Dispatch request to the JSP
        try {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/createPatient.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String str = "";
		String part = "";
		
		// Read the buffer to get all data
		final BufferedReader reader = request.getReader();
		while(part != null) {
			str += part;
			part = reader.readLine();
		}
		
		System.out.println("Received: "+str);
		
		// Interpert data as a JSON object and parse it
		JSONObject jsonRequest = new JSONObject(str);
		JSONObject jsonResult = new JSONObject();
		
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
				jsonResult.put("succes", true);
				jsonResult.put("message", "Thank you for the new user.");
				
			} catch (DaoSaveObjectException | NoAuthorityException e) {
				jsonResult.put("succes", false);
				jsonResult.put("message", "Error while creating the new user.");
			}
			
		} catch (JSONException e) {
			jsonResult.put("succes", false);
			jsonResult.put("message", "Error while parsing the JSON request for the new user.");
		}
		
		// Write the resulting JSON string back
		response.setContentType("application/json");
		jsonResult.write(response.getWriter());
	}
}
