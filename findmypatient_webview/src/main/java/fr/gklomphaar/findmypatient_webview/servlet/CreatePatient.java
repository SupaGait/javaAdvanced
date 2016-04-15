package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.PatientJDBCDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;

/**
 * Servlet implementation class CreatePatient
 */
@WebServlet("/CreatePatient")
public class CreatePatient extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IDataDAO<Patient> dao = new PatientJDBCDAO();
       
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String str = "";
		String part = "";
		final BufferedReader reader = request.getReader();
		while(part != null) {
			str += part;
			part = reader.readLine();
		}
		
		System.out.println("Received: "+str);
		
		/*
		JSONObject json = new JSONObject(str);
		
		// Get the parameters
		final String firstName = json.getString("firstName");
		final String lastName = json.getString("lastName");
		final String email = json.getString("email");
		
		
		System.out.println(firstName+" "+lastName+" "+email);
		*/
			/*
		// Create the new patient
		Patient newPatient = new Patient();
		
		try {
			dao.create(newPatient);
		} catch (DaoSaveObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		response.getWriter().append("Thank you for the new user");
	}

}