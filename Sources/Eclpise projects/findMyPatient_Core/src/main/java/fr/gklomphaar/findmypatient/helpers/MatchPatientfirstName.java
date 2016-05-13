//The match patient front name file
package fr.gklomphaar.findmypatient.helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.gklomphaar.findmypatient.datamodel.Patient;

public class MatchPatientfirstName implements IMatcher<Patient>, Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public String getSQLMatchStatement(String tableName, Patient patient) {
		return "select * from "+tableName+" where FNAME='"+patient.getFirstName()+"'";
	}

	@Override
	public String toString() {
		return "First name";
	}

	@Override
	public List<String> getFields() {
		List<String> fieldList = new ArrayList<String>();
		fieldList.add("firstName");
		return fieldList;
	}
}
