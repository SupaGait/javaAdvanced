package fr.gklomphaar.generichybernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import fr.gklomphaar.datamodel.Identity;
import fr.gklomphaar.services.dao.exceptions.DaoLoadObjectException;
import fr.gklomphaar.services.dao.exceptions.DaoSaveObjectException;
import fr.gklomphaar.services.dao.generichybernate.GenericHybernateDAO;
import fr.gklomphaar.services.dao.generichybernate.WhereClauseBuilder;
//import fr.gklomphaar.findmypatient.datamodel.Patient;
import fr.gklomphaar.services.dao.generichybernate.WhereClauseBuilder.WhereClause;

@RunWith(SpringJUnit4ClassRunner.class) //This is to tell Junit to run with spring
@ContextConfiguration(locations={"application-context.xml"}) // to tell spring to load the required context
public class TestHybernateGenericDao {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Before
	public void beforeEachTest(){
		// Delete all Identities
		Session session = sessionFactory.openSession();
		session.createQuery("delete from "+Identity.class.getSimpleName()).executeUpdate();
	}
	@After
	public void afterEatchTest() {
		// Nothing yet
	}
	
	@Test
	public void springSetup(){
		Assert.notNull(ds);
	}

	@Test
	public void dataSourceUsage() throws SQLException{
		Connection connection = ds.getConnection();
		System.out.println(connection.getSchema());
		connection.close();
		
	}

	//@Test
	public void testAddAndRead() throws DaoLoadObjectException, DaoSaveObjectException {
		GenericHybernateDAO<Identity> identityDao = new GenericHybernateDAO<>(Identity.class, this.sessionFactory);
		
		// Add test identities
		identityDao.create(new Identity("Frans","Bonnes","Frans@Bonnes.fun"));
		identityDao.create(new Identity("Sjohn","Transquit","Sjohn@Transquit.fun"));
		
		// Read DAO, and test number of entities
		final List<Identity> readAllList = identityDao.readAll();
		org.junit.Assert.assertEquals(2, readAllList.size());

		// Print all identities		
		for (Identity identity : readAllList) {
			System.out.println(identity.toString());
		}
	}
	
	@Test
	public void testFindFields() throws DaoSaveObjectException, DaoLoadObjectException {
		GenericHybernateDAO<Identity> identityDao = new GenericHybernateDAO<>(Identity.class, this.sessionFactory);
		
		// Generate where clauses, check that there are 5 clauses
		WhereClauseBuilder whereClauseBuilder = new WhereClauseBuilder(Identity.class);
		final Map<String, WhereClause> whereClauses = whereClauseBuilder.getWhereClauses();
		org.junit.Assert.assertEquals(5, whereClauses.size());

		
		// Add test identities
		final List<Identity> populateIdentities = populateIdentities(identityDao, 5);
		
		// Iterate over dynamically created where clauses
		int identityNr = 0;
		for (Entry<String, WhereClause> entry : whereClauses.entrySet())
		{
		    //System.out.println(entry.getKey() + "/" + entry.getValue());
			if(entry.getKey().endsWith("id"))
				continue; // id currently not updated in local Identity...TODO..
			
			List<Identity> searchResult = identityDao.search(populateIdentities.get(identityNr), entry.getValue());
			org.junit.Assert.assertEquals(1, searchResult.size());
			
			identityNr++;
		}
	}
	
	@Test
	public void testUpdate() throws DaoSaveObjectException, DaoLoadObjectException {
		GenericHybernateDAO<Identity> identityDao = new GenericHybernateDAO<>(Identity.class, this.sessionFactory);
		
		final List<Identity> populateIdentities = populateIdentities(identityDao, 1);
		final Identity identity = populateIdentities.get(0);
		
		// Change name and update
		final String changedFirstName = "ChangedFirstName";
		identity.setFirstName(changedFirstName);
		identityDao.update(identity);
		
		WhereClauseBuilder whereClauseBuilder = new WhereClauseBuilder(Identity.class);
		
		// Verify that the new name is in the DAO using a search
		final List<Identity> searchResult = identityDao.search(identity, whereClauseBuilder.getWhereClauses().get("firstName"));
		org.junit.Assert.assertEquals(1, searchResult.size());
		org.junit.Assert.assertEquals(identity.getFirstName(), searchResult.get(0).getFirstName());
	}
	
	@Test
	public void testDelete() throws DaoSaveObjectException, DaoLoadObjectException {
		GenericHybernateDAO<Identity> identityDao = new GenericHybernateDAO<>(Identity.class, this.sessionFactory);
		
		// Populate and verify size
		populateIdentities(identityDao, 10);
		final List<Identity> initialIdentities = identityDao.readAll();
		org.junit.Assert.assertEquals(10, initialIdentities.size());
		
		// Delete first 5 identities
		for (int i = 0; i < 5; i++) {
			identityDao.delete(initialIdentities.get(i));
		}
		
		// Verify that only last 5 identities are available
		final List<Identity> afterDelete = identityDao.readAll();
		org.junit.Assert.assertEquals(5, afterDelete.size());
		for (int i = 0; i < 5; i++) {
			org.junit.Assert.assertEquals(afterDelete.get(i).getFirstName(), initialIdentities.get(i+5).getFirstName());
		}
	}
	
	/**
	 * Populate the dao with identities, and return the added Identities
	 * 
	 * @param dao Dao to fill
	 * @param numberOfIdentites amount of identities to create
	 * @return List of unique created identities
	 * @throws DaoSaveObjectException
	 */
	public List<Identity> populateIdentities(GenericHybernateDAO<Identity> dao, int numberOfIdentites) throws DaoSaveObjectException {

		List<Identity> identities = new ArrayList<Identity>();
		
		for (int identityNr = 0; identityNr < numberOfIdentites; identityNr++) {
			String iNr = Integer.toString(identityNr);
			final Identity identity = new Identity("Name_"+iNr,"LastName_"+iNr,"Email_"+iNr);
			
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Calendar.YEAR, 1970 + identityNr);
	
			identity.setBirthDate(calendar.getTime());
			identities.add(identity);
			dao.create(identity);
		}
		return identities;
	}
	
}