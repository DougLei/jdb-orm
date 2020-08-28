package com.douglei.orm.session.sql;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.impl.ConfigurationImpl;
import com.douglei.orm.session.SysUser;
import com.douglei.orm.session.UserP;
import com.douglei.orm.sessionfactory.sessions.Session;

public class SessionTest {
	
	@Test
	public void insertTest12() {
		SysUser user = new SysUser(0, "石磊1", 28, "男");
		session.getSQLSession().executeUpdate("com.test", "insertSysUser", user);
		SysUser user_ = new SysUser(0, "成荣2", 25, "女");
		session.getSQLSession().executeUpdate("com.test", "insertSysUser", user_);
		System.out.println("==============>" + user);
		System.out.println("==============>" + user_);
		
		Map<String, Object> user2 = new HashMap<String, Object>();
		user2.put("name", "douglei");
		user2.put("age", 25);
		user2.put("sex", "男");
		session.getSQLSession().executeUpdate("com.test", "insertSysUser2", user2);
		System.out.println("==============>" + user2);
	}
	
	@Test
	public void insertTest3() {
		UserP user = new UserP(new SysUser(0, "石磊", 28, "男"));
		session.getSQLSession().executeUpdate("com.test", "insertSysUser3", user);
		System.out.println("==============>" + user);
	}
	
	@Test
	public void insertTest4() {
		Map<String, Object> user = new HashMap<String, Object>();
		
		Map<String, Object> user2 = new HashMap<String, Object>();
		user2.put("name", "张亮2");
		user2.put("age", 25);
		user2.put("sex", "男");
		user.put("user", user2);
		
		session.getSQLSession().executeUpdate("com.test", "insertSysUser4", user);
		System.out.println("==============>" + user);
	}
	
	// --------------------------------------------------------------------------------------
	
	private Configuration conf;
	private Session session;
	
	@Before
	public void before() {
		conf = new ConfigurationImpl();
		session = conf.buildSessionFactory().openSession();
	}
	@After
	public void end() {
		session.close();
	}
}
