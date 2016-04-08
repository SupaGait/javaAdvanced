//The match patient display name
package fr.gklomphaar.findmypatient.helpers;

import fr.gklomphaar.findmypatient.datamodel.Patient;

/**
 *
 */
public class MatchPatientDisplayName implements IMatcher<Patient> {
	
	@Override
	public String getSQLMatchStatement(String tableName, Patient patient) {
		return "select * from "+tableName+" where DISPLAYNAME='"+patient.getDisplayName()+"'";
	}
	
	@Override
	public String toString() {
		return "Full name";
	}
}
