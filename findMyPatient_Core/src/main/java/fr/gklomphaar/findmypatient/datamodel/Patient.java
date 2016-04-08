
package fr.gklomphaar.findmypatient.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Patient 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String pId;
	
	@Column(name="PATIENT_SERIALNUMBER")
	private String ssnNo;
	
	@Column(name="PATIENT_FIRSTNAME")
	private String fName;
	
	@Column(name="PATIENT_LASTNAME")
	private String lName;
	
	@Column(name="PATIENT_DOB")
	private String dob;
	
	
	@Column(name="PATIENT_CELLNO")
	private String cellNo;
	
	@Column(name="PATIENT_EMAIL")
	private String email;
	
	@Column(name="PATIENT_DISPLAYNAME")
	private String displayName;
	
	@Column(name="PATIENT_ROOMNO")
	private String roomNo;
	
	public Patient(String pId, String ssnNo, String fName, String lName, String dob, String cellNo, String Email, String displayName, String roomNo) 
	{
		this.pId = pId;
		this.ssnNo = ssnNo;
		this.fName = fName;
		this.lName = lName;
		this.dob = dob;
		this.cellNo = cellNo;
		this.email = Email;
		this.displayName = displayName;	
		this.roomNo = roomNo;
	}
	public Patient(String ssnNo, String fName, String lName, String dob, String cellNo, String Email, String displayName, String roomNo) 
	{
		this.pId = "new patient";
		this.ssnNo = ssnNo;
		this.fName = fName;
		this.lName = lName;
		this.dob = dob;
		this.cellNo = cellNo;
		this.email = Email;
		this.displayName = displayName;	
		this.roomNo = roomNo;
	}
	public Patient()
	{
		this.pId = "new patient";
		this.ssnNo = "";
		this.fName = "";
		this.lName = "";
		this.dob = "";
		this.cellNo = "";
		this.email = "";
		this.displayName = "";	
		this.roomNo = "";
	}

	public String getpId() {
		return pId;
	}


	public String getSsnNo() {
		return ssnNo;
	}
	
	public void setSsnNo(String ssnNo) {
		this.ssnNo = ssnNo;
	}

	public String getfName() {
		return fName;
	}
	
	public void setfName(String fName) {
		this.fName = fName;
	}
	
	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCellNo() {
		return cellNo;
	}
	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getroomNo() {
		return roomNo;
	}

	public void setroomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	public String toString() {
	return "Identity is displayed pId=" + pId + ", ssnNo=" + ssnNo + ", fName =" + fName + ", lName =" + lName + ", Dob =" +dob + 
				", cellNo=" + cellNo + ", Email=" + email + ", displayName=" + displayName + " Room No " + roomNo   ;
	}

	
}
