/**
 * 
 */
package fr.gklomphaar.generichybernate;

import org.junit.runner.RunWith;

import fr.gklomphaar.datamodel.Identity;
import fr.gklomphaar.services.dao.generichybernate.WhereClauseBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author Gerard
 * 
 * Simply test reflection concepts
 *
 */
public class TestReflection {

	Identity testIdentity = new Identity("Frans","Bonnes","Frans@TestReflection.fun");
	
	//@Test
	public void testGetFields() {
		
		// Get all fields of the class
		Class<?> dataClass = testIdentity.getClass();
		final Field[] fields = dataClass.getDeclaredFields();
		
		System.out.println(fields.length);
		for(Field field : fields) {
			System.out.println(field.getName()+":  ");
			final Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
				System.out.println(annotation.toString());
			}
			System.out.println(" ");
		}
	}
	
	//@Test
	public void testGetTableName() {
		// Get all fields of the class
		Class<?> dataClass = testIdentity.getClass();
		final Annotation[] annotations = dataClass.getAnnotations();
		final String name = dataClass.getSimpleName();
		
		System.out.println(name);
		for (Annotation annotation : annotations) {
			System.out.println(annotation instanceof javax.persistence.Table);
			System.out.println(annotation.toString());
			if(annotation instanceof javax.persistence.Table) {
				System.out.println(((javax.persistence.Table)annotation).name());
			}
		}
	}
}
