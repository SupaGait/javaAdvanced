/**
 * 
 */
package fr.gklomphaar.findmypatient.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import fr.gklomphaar.findmypatient.datamodel.Patient;


public class PatientJDBCDAO extends JDBCDAO<Patient> {

	public PatientJDBCDAO(DataSource dataSource)
	{
		// Pass the table name
		super("PATIENTS", dataSource);
	}

	
	protected List<Patient> parseQueryResultSet(ResultSet rs) throws SQLException {
		List<Patient> patientList = new ArrayList<Patient>();
		
		// Iterate through the results and add to list
		while (rs.next()) {
			int pId = rs.getInt("id");
			String ssnNo = rs.getString("ssn");
			String fName = rs.getString("fname");
			String lName = rs.getString("lname");
			String dob = rs.getString("dob");
			String cellNo = rs.getString("cellNo");
			String email = rs.getString("email");
			String displayname = rs.getString("displayname");
			String roomNo = rs.getString("roomNo");
			
			Patient Patient = new Patient(pId, ssnNo, fName, lName, dob, cellNo, email, displayname, roomNo);
			patientList.add(Patient);
		}
		return patientList;
	}
	
	@Override
	protected PreparedStatement insertData(Patient patient) throws SQLException
	{
		PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO PATIENTS(SSN, FNAME, LNAME, DOB, CELLNO, EMAIL, DISPLAYNAME, ROOMNO) VALUES(?,?,?,?,?,?,?,?)");
		
		stmt.setString(1, patient.getSocialSecurityNumber());
		stmt.setString(2, patient.getFirstName());
		stmt.setString(3, patient.getLastName());
		stmt.setString(4, patient.getDateOfBirth());
		stmt.setString(5, patient.getTelephoneNumber());
		stmt.setString(6, patient.getEmail());
		stmt.setString(8, patient.getRoomNumber());
		
		return stmt;
	}
	@Override
	protected PreparedStatement updateData(Patient patient) throws SQLException
	{
		PreparedStatement stmt = this.connection.prepareStatement("UPDATE PATIENTS SET SSN=?, FNAME=?, LNAME=?, DOB=?, CELLNO=?, EMAIL=?, DISPLAYNAME=?, ROOMNO=? WHERE ID=?");
		stmt.setString(1, patient.getSocialSecurityNumber());
		stmt.setString(2, patient.getFirstName());
		stmt.setString(3, patient.getLastName());
		stmt.setString(4, patient.getDateOfBirth());
		stmt.setString(5, patient.getTelephoneNumber());
		stmt.setString(6, patient.getEmail());
		stmt.setString(8, patient.getRoomNumber());
		
		stmt.setInt(9, patient.getId());
		return stmt;
	}
	@Override
	protected PreparedStatement deleteData( Patient patient) throws SQLException
	{
		PreparedStatement stmt = this.connection.prepareStatement("DELETE FROM PATIENTS WHERE ID=? ");
		stmt.setInt(1, patient.getId());
		return stmt;
	}
}
