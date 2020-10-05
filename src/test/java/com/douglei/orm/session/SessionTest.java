package com.douglei.orm.session;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.douglei.orm.Configuration;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.orm.sql.pagequery.PageResult;

public class SessionTest {
	String sql = "select id, pid, name from classes order by id asc";
	
	Configuration configuration = new Configuration("jdb-orm.test.conf.xml");
	SessionFactory sf = configuration.buildSessionFactory();
	
	@Test
	public void testRecursiveQuery() {
		List<Map<String, Object>> list = sf.openSession(false).getSqlSession().recursiveQuery(0, "id", "pid", "1", "CHILDREN", sql);
		System.out.println("\n\n" + JSONObject.toJSONString(list));
	}
	
	@Test
	public void testRecursiveQuery2Class() {
		List<Classes> list = sf.openSession(false).getSqlSession().recursiveQuery(Classes.class, 0, "id", "pid", null, "subClasses", sql);
		System.out.println("\n\n" + JSONObject.toJSONString(list));
	}
	
	@Test
	public void testPageRecursiveQuery() {
		PageResult<Map<String, Object>> list = sf.openSession(false).getSqlSession().pageRecursiveQuery(2, 2, 3, "id", "pid", null, "CHILDREN", sql);
		System.out.println("\n\n" + JSONObject.toJSONString(list));
	}
	
	@Test
	public void testPageRecursiveQuery2Class() {
		PageResult<Classes> list = sf.openSession(false).getSqlSession().pageRecursiveQuery(Classes.class, 2, 2, 3, "id", "pid", null, "subClasses", sql);
		System.out.println("\n\n" + JSONObject.toJSONString(list));
	}
	
	@Test
	public void testCountQuery() {
		long count = sf.openSession(false).getSqlSession().countQuery(sql);
		System.out.println(count);
	}
}
