
package fr.gklomphaar.findmypatient.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Patient data
 * @author Gerard
 *
 */
@Entity
public class Patient 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String socialSecurityNumber;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String telephoneNumber;
	private String email;
	private String roomNumber;
	
	public Patient(int pId, String ssnNo, String fName, String lName, String dob, String cellNo, String Email, String displayName, String roomNo) 
	{
		this.id = pId;
		this.socialSecurityNumber = ssnNo;
		this.firstName = fName;
		this.lastName = lName;
		this.dateOfBirth = dob;
		this.telephoneNumber = cellNo;
		this.email = Email;	
		this.roomNumber = roomNo;
	}
	public Patient(String ssnNo, String fName, String lName, String dob, String cellNo, String Email, String displayName, String roomNo) 
	{
		this.id = 0;
		this.socialSecurityNumber = ssnNo;
		this.firstName = fName;
		this.lastName = lName;
		this.dateOfBirth = dob;
		this.telephoneNumber = cellNo;
		this.email = Email;
		this.roomNumber = roomNo;
	}
	public Patient()
	{
		this.id = 0;
		this.socialSecurityNumber = "";
		this.firstName = "";
		this.lastName = "";
		this.dateOfBirth = "";
		this.telephoneNumber = "";
		this.email = "";
		this.roomNumber = "";
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the socialSecurityNumber
	 */
	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}
	/**
	 * @param socialSecurityNumber the socialSecurityNumber to set
	 */
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the telephoneNumber
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	/**
	 * @param telephoneNumber the telephoneNumber to set
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}
	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	/**
	 * @return String containing the values of all fields
	 */
	public String toString() {
	return "Identity is displayed id=" + id + ", socialSecurityNumber=" + socialSecurityNumber + ", firstName =" + firstName + ", lastName =" + lastName + ", Dob =" +dateOfBirth + 
				", telephoneNumber=" + telephoneNumber + ", Email=" + email + ", displayName=" + " Room No " + roomNumber   ;
	}

	
}
