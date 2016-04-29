// The matcher username file
package fr.gklomphaar.findmypatient.helpers;
import java.util.List;

import fr.gklomphaar.findmypatient.datamodel.User;

public class MatchUserName implements IMatcher<User> {
	
	@Override
	public String getSQLMatchStatement(String tableName, User user) {
		return "select * from "+tableName+" where NAME='"+user.getName()+"'";
	}
	
	@Override
	public String toString() {
		return "user name";
	}

	@Override
	public List<String> getFields() {
		return null;
	}
}
