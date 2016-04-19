package fr.gklomphaar.findmypatient_webview.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.gklomphaar.findmypatient.controller.IdentityController;
import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.findmypatient.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.findmypatient.datamodel.User;
import fr.gklomphaar.findmypatient.datamodel.exceptions.NoAuthorityException;
import fr.gklomphaar.findmypatient_webview.WebController;
import fr.gklomphaar.findmypatient.dao.GenericHybernateDAO;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;

	// TODO: For now... 
	WebController webController = new WebController();

	@Autowired
	@Qualifier("userDAO")
	//GenericHybernateDAO<User> userGenericDAO;
	IDataDAO<User> userDAO;
	//IDataDAO<User> userDAO = (IDataDAO<User>) userGenericDAO;
	
	@Autowired
	@Qualifier("patientDAO")
	//GenericHybernateDAO<Patient> patientGenericDAO;
	IDataDAO<Patient> patientDAO;
	//IDataDAO<Patient> patientDAO = (IDataDAO<Patient>) patientGenericDAO; 
	
	
    /**
     * Default constructor. 
     */
    public Login() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
/*		try {
			webController.getUserAuthority().login("test", "hello");
		} catch (NoAuthorityException | DaoLoadObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		try {
			patientDAO.create(new Patient());
		} catch (DaoSaveObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
