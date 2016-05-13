package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.UserAuthority.UserRights;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;

/**
 * Servlet implementation class DeletePatient
 */
@WebServlet("/DeletePatient")
public class DeletePatient extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePatient() {
        super();
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check for minimal write rights
		if(userController.getUserAuthority().getUserRights().getValue() > UserRights.ReadWrite.getValue()){
			// Interpret input data as a JSON object
			JSONObject requestAsJson = getRequestAsJson(request);
			JSONObject jsonResult = new JSONObject();
			
			// Remove the patient from the database, based on Id
			if(requestAsJson.has("patientId")) {
				int patientId = requestAsJson.getInt("patientId");
				Patient patient = new Patient();
				patient.setId(patientId);
				try {
					userController.getPatientManagement().delete(patient);
					jsonResult.put("succes", true);
					jsonResult.put("message", "Patient deleted");
				} catch (NoAuthorityException e) {
					jsonResult.put("succes", false);
					jsonResult.put("message", "Not sufficient rights to peform the requested action.");
				} catch (DaoSaveObjectException e) {
					jsonResult.put("succes", false);
					jsonResult.put("message", "Error while deleting the patient.");
				}
			}
			else {
				// patientId not included in the request
				jsonResult.put("succes", false);
				jsonResult.put("message", "Error while deleting the patient, no id given.");
			}
			
			// Write the resulting JSON string back
			response.setContentType("application/json");
			jsonResult.write(response.getWriter());
		}
	}

}
