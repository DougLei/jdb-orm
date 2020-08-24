package com.douglei.orm.session.sql;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;
import com.douglei.orm.session.SysUser;
import com.douglei.orm.sessionfactory.sessions.Session;

public class SessionTest {
	
	@Test
	public void insertTest() {
		SysUser user = new SysUser(0, "石磊333", 28, "男");
		session.getSQLSession().executeUpdate("com.test", "insertSysUser", user);
		session.getSQLSession().executeUpdate("com.test", "insertSysUser", new SysUser(0, "成荣", 25, "女"));
		System.out.println("==============>" + user);
		
		Map<String, Object> user2 = new HashMap<String, Object>();
		user2.put("name", "张亮2");
		user2.put("age", 25);
		user2.put("sex", "男");
//		session.getSQLSession().executeUpdate("com.test", "insertSysUser2", user2);
//		System.out.println("==============>" + user2);
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
