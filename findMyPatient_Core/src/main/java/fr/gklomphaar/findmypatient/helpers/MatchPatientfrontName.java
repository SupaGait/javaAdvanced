//The match patient front name file
package fr.gklomphaar.findmypatient.helpers;

import fr.gklomphaar.findmypatient.datamodel.Patient;

public class MatchPatientfrontName implements IMatcher<Patient> {

	@Override
	public String getSQLMatchStatement(String tableName, Patient patient) {
		return "select * from "+tableName+" where FNAME='"+patient.getFirstName()+"'";
	}

	@Override
	public String toString() {
		return "First name";
	}
}
