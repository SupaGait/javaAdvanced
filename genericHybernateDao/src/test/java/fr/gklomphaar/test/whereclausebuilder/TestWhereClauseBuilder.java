/**
 * 
 */
package fr.gklomphaar.test.whereclausebuilder;

import fr.gklomphaar.datamodel.Test_Identity;
import fr.gklomphaar.services.WhereClauseBuilder;
import fr.gklomphaar.services.exception.WhereClauseGenerateException;
import fr.gklomphaar.services.WhereClause;

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
	public void testWhereClauseConstructorAndInit() throws WhereClauseGenerateException {
		Test_Identity testIdentity = new Test_Identity("Frans","Bonnes","Frans@TestReflection.fun");
		
		WhereClauseBuilder builder = new WhereClauseBuilder(testIdentity.getClass());
		builder.init();
		final Set<Entry<String, String>> entrySet = builder.getSimpleClauses().entrySet();
		System.out.println(entrySet.size());
		for (Entry<String, String> entry : entrySet) {
			System.out.println(entry.getKey()+":\t"+entry.getValue());
		}
		
		final Map<String, String> clauses = builder.getSimpleClauses();
		System.out.println(clauses.keySet());
	}
	
	@Test
	public void testGetWhereClause() throws WhereClauseGenerateException {
		Test_Identity testIdentity = new Test_Identity("Frans","Bonnes","Frans@TestReflection.fun");
		
		WhereClauseBuilder whereClauseBuilder = new WhereClauseBuilder(testIdentity.getClass());
		whereClauseBuilder.init();
		final Map<String, WhereClause> whereClauses = whereClauseBuilder.getWhereClauses();
		
		System.out.println(whereClauses.keySet());
		System.out.println(whereClauses.entrySet());
		
		WhereClause whereClause = whereClauses.get("firstName");
		Assert.assertNotNull(whereClause);
	}
}
