package com.douglei.orm.sessionfactory;

import org.junit.Before;
import org.junit.Test;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.sessionfactory.SessionFactory;

public class SessionFactoryTest {
	
	@Test
	public void test() {
//		sessionFactory.dynamicAddMapping(new DynamicMapping(MappingType.TABLE, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
//				"<mapping-configuration>\r\n" + 
//				"	<table name=\"TEST\" createMode=\"drop_create\">\r\n" + 
//				"		<columns>\r\n" + 
//				"			<column name=\"id\" property=\"id\" dataType=\"string\" primaryKey=\"true\" length=\"50\" />\r\n" + 
//				"			<column name=\"name\" property=\"name\" dataType=\"string\" length=\"30\" />\r\n" + 
//				"			<column name=\"age\" property=\"age\" dataType=\"integer\" length=\"2\" />\r\n" + 
//				"			<column name=\"sex\" property=\"sex\" dataType=\"string\" length=\"30\" />\r\n" + 
//				"		</columns>\r\n" + 
//				"	</table>\r\n" + 
//				"</mapping-configuration>"));
//		
//		TableSession ts = sessionFactory.openTableSession();
//		Map<String, Object> propertyMap = new HashMap<String, Object>();
//		propertyMap.put("ID", IdentityUtil.getUUID());
//		propertyMap.put("NAME", "金石磊-DougLei");
//		ts.save("TEST", propertyMap );
//		ts.close();
	}
	
	// --------------------------------------------------------------------------------------
	
	private Configuration conf;
	@SuppressWarnings("unused")
	private SessionFactory sessionFactory;
	
	@Before
	public void before() {
		conf = new Configuration();
		sessionFactory = conf.buildSessionFactory();
	}
}
