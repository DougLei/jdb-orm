package com.douglei.test.session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.impl.xml.XmlConfiguration;
import com.douglei.session.EntityMap;
import com.douglei.session.Session;

public class SessionTest {
	
	@Test
	public void queryTest() {
		
	}
	
	@Test
	public void saveTest() {
		session.save(SysUser.getList().get(0));
		
		EntityMap entity = new EntityMap("SYS_USER");
		entity.setProperty("id", "id12");
		entity.setProperty("name", "name12");
		entity.setProperty("age", 32);
		entity.setProperty("sex", "22");
		session.save(entity );
	}
	
	@Test
	public void procedureTest() {
		
	}
	
	
	
	// --------------------------------------------------------------------------------------
	
	private Configuration conf;
	private Session session;
	
	@Before
	public void before() {
		conf = new XmlConfiguration();
		session = conf.buildSessionFactory().openSession();
	}
	@After
	public void end() {
		session.close();
	}
}
