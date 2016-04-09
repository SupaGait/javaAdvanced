/**
 * 
 */
package fr.gklomphaar.generichybernate;

import fr.gklomphaar.datamodel.Identity;
import fr.gklomphaar.services.dao.generichybernate.WhereClauseBuilder;
import fr.gklomphaar.services.dao.generichybernate.WhereClauseBuilder.WhereClause;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Gerard
 * 
 * Simply test reflection concepts
 *
 */
public class TestWhereClauseBuilder {
	
	@Test 
	public void testWhereClauseConstructor() {
		Identity testIdentity = new Identity("Frans","Bonnes","Frans@TestReflection.fun");
		
		WhereClauseBuilder builder = new WhereClauseBuilder(testIdentity.getClass());
		final Set<Entry<String, String>> entrySet = builder.getSimpleClauses().entrySet();
		System.out.println(entrySet.size());
		for (Entry<String, String> entry : entrySet) {
			System.out.println(entry.getKey()+":\t"+entry.getValue());
		}
		
		final Map<String, String> clauses = builder.getSimpleClauses();
		System.out.println(clauses.keySet());
	}
	
	@Test
	public void testGetWhereClause() {
		Identity testIdentity = new Identity("Frans","Bonnes","Frans@TestReflection.fun");
		
		WhereClauseBuilder whereClauseBuilder = new WhereClauseBuilder(testIdentity.getClass());
		final Map<String, WhereClause> whereClauses = whereClauseBuilder.getWhereClauses();
		
		System.out.println(whereClauses.keySet());
		System.out.println(whereClauses.entrySet());
		
		WhereClause whereClause = whereClauses.get("firstName");
		Assert.assertNotNull(whereClause);
	}
}
