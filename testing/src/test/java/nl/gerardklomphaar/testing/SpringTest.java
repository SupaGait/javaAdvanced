package nl.gerardklomphaar.testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@ContextConfiguration(locations={"application-context.xml"})
public class SpringTest {
	
	@Autowired
	TestClass testclass;
	
	@Test
	public void test1()
	{
		Assert.fail("I failed happy");
	}
}

