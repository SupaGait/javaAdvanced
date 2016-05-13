// The matcher username file
package fr.gklomphaar.findmypatient.helpers;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.gklomphaar.findmypatient.datamodel.SystemUser;

public class MatchUserName implements IMatcher<SystemUser>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getSQLMatchStatement(String tableName, SystemUser user) {
		return "select * from "+tableName+" where NAME='"+user.getName()+"'";
	}
	
	@Override
	public String toString() {
		return "user name";
	}

	@Override
	public List<String> getFields() {
		List<String> fieldList = new ArrayList<String>();
		fieldList.add("userName");
		return fieldList;
	}
}
