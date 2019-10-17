package com.douglei.orm.session.sql;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;
import com.douglei.orm.sessionfactory.sessions.Session;

public class SessionTest {
	
	@Test
	public void queryTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PID", "A3F056DE-365C-4AB0-A85F-BCCDD34C427D");
//		map.put("op", "");
		Object obj = session.getSQLSession().executeProcedure("test", null, map);
		System.out.println(JSONObject.toJSONString(obj));
		
		
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
