package com.douglei.orm.jdbc;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.impl.ConfigurationImpl;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSession;

public class StatementHandlerTest {
	
	@Test
	public void queryTest() {
		List<Map<String, Object>> list = session.query("select * from sys_user");
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void executeUpdateTest() {
		session.executeUpdate("insert into sys_user (id, name) values('id','name')");
		session.executeUpdate("insert into sys_user (id, name) values('id','name')");
	}
	
	@Test
	public void procedureTest() {
		
	}
	
	
	
	// --------------------------------------------------------------------------------------

	@SuppressWarnings("unused")
	private Configuration conf;
	private SqlSession session;
	
	@Before
	public void before() {
		conf = new ConfigurationImpl();
//		session = conf.buildSessionFactory().openSqlSession();
	}
	@After
	public void after() {
//		session.close();
	}
}
