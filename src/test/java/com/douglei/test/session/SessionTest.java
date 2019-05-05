package com.douglei.test.session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.impl.xml.XmlConfiguration;
import com.douglei.sessions.session.Session;

public class SessionTest {
	
	@Test
	public void queryTest() {
		
	}
	
	@Test
	public void saveTest() {
		session.save(SysUser.getList().get(0));
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
		session = conf.buildSessionFactory().openTableSession();
	}
	@After
	public void end() {
		session.close();
	}
}
