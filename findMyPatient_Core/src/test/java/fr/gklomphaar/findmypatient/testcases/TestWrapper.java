/**
 * 
 */
package fr.gklomphaar.findmypatient.testcases;

import org.junit.Assert;

/**
 * Wrapper to test an implementation which can throw lots of exceptions
 * @author Gerard
 */
public abstract class TestWrapper {
	/**
	 * Specific test implementations
	 * @throws Exception
	 */
	abstract protected void test() throws Exception;
	
	/**
	 * run the test 
	 */
	public void run()
	{
		try {
			test();
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), false);
			//e.printStackTrace();
		}
	}

}
