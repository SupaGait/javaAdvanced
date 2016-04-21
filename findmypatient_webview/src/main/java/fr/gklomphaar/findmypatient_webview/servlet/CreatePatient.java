package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.PatientJDBCDAO;
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
		JSONObject json = new JSONObject(str);
		
		// Get the parameters
		try {
			String firstName = json.getString("firstName");
			String lastName = json.getString("lastName");
			String email = json.getString("email");
			
			System.out.println(firstName+" "+lastName+" "+email);
			
			// Create the new patient
			Patient newPatient = new Patient();
			newPatient.setfName(firstName);
			newPatient.setlName(lastName);
			newPatient.setEmail(email);
			
			// Save it
			try {
				userController.getPatientManagement().add(newPatient);
				response.getWriter().append("Thank you for the new user.");	
			} catch (DaoSaveObjectException | NoAuthorityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().append("Error while creating the new user.");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
