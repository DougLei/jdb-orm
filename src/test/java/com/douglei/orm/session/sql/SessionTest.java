package com.douglei.orm.session.sql;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;
import com.douglei.orm.sessionfactory.sessions.Session;

public class SessionTest {
	
	@Test
	public void insertTest() {
		session.getSQLSession().executeUpdate("com.test", "insertSysUser", "1");
		
		
		
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
