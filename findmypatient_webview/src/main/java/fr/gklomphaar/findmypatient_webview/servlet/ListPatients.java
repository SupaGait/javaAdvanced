package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.PatientJDBCDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient_webview.UserController;

/**
 * Servlet implementation class CreatePatient
 */
@WebServlet("/ListPatients")
public class ListPatients extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	UserController userController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListPatients() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Patient> patientList = null;

		// Read the patients
		try {
			patientList = userController.getPatientManagement().readAll();
			
		} catch (NoAuthorityException | DaoLoadObjectException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Pass to the JSP
        HttpSession session =request.getSession();
        session.setAttribute("patientsList", patientList);
        
        // Dispatch request to the JSP
        try {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/listPatients.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
