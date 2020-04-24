package com.douglei.orm.session;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;
import com.douglei.orm.sessionfactory.SessionFactory;

public class SessionTest {
	Configuration configuration = new XmlConfiguration();
	SessionFactory sf = configuration.buildSessionFactory();
	
	@Test
	public void testRecursiveQuery() {
		String sql = "select id, pid, name from classes order by id asc";
		
		List<Map<String, Object>> list = sf.openSession(false).getSqlSession().recursiveQuery(0, "id", "pid", "1", "CHILDREN", sql);
		System.out.println("\n\n" + JSONObject.toJSONString(list));
		
		
	}
	
	@Test
	public void testRecursiveQuery2Class() {
		String sql = "select id, pid, name from classes order by id asc";
		
		List<Classes> list = sf.openSession(false).getSqlSession().recursiveQuery(Classes.class, 0, "id", "pid", "2", "subClasses", sql);
		System.out.println("\n\n" + JSONObject.toJSONString(list));
	}
}
