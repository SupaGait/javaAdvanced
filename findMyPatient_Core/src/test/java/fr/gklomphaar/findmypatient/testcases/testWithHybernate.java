/**
 * 
 */
package fr.gklomphaar.findmypatient.testcases;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.gklomphaar.findmypatient.dao.IDataDAO;
import fr.gklomphaar.findmypatient.dao.PatientJDBCDAO;

/**
 * @author Gerard
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"application-context.xml"})
public class testWithHybernate {
	
	//@Autowired
	//TestClass testclass;
	
	//@Autowired
	//IDataDAO<PatientJDBCDAO> patientDao;
	
	@Test
	public void testHappyFail()
	{
		Assert.fail("I failed happy");
	}
	
	@Test
	public void testPatientDao()
	{
		// use the patientDAO
	}
}
