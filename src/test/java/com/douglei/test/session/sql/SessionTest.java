package com.douglei.test.session.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.impl.xml.XmlConfiguration;
import com.douglei.sessions.session.sql.SQLSession;

public class SessionTest {
	
	@Test
	public void queryTest() {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("name", "哈哈");
		
		List<Map<String, Object>> list = session.query("com.test", "queryUser", p);
		System.out.println(list.size());
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	// --------------------------------------------------------------------------------------
	
	private Configuration conf;
	private SQLSession session;
	
	@Before
	public void before() {
		conf = new XmlConfiguration();
		session = conf.buildSessionFactory().openSQLSession();
	}
	@After
	public void end() {
		session.close();
	}
}
