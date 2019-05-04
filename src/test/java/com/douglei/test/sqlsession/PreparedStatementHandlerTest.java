package com.douglei.test.sqlsession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.impl.xml.XmlConfiguration;
import com.douglei.sessions.sqlsession.SqlSession;

public class PreparedStatementHandlerTest {
	
	@Test
	public void queryTest() {
		parameters.add("哈哈");
		List<Map<String, Object>> list = session.query("select * from sys_user where name = ?", parameters);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void executeUpdateTest() {
		parameters.add("id_" + new Date().getTime());
		parameters.add("name_" + new Date().getTime());
		session.executeUpdate("insert into sys_user (id, name) values(?,?)", parameters);
	}
	
	@Test
	public void procedureTest() {
		
	}

	
	
	// --------------------------------------------------------------------------------------
	
	private Configuration conf;
	private SqlSession session;
	private List<Object> parameters;
	
	@Before
	public void before() {
		conf = new XmlConfiguration();
		session = conf.buildSessionFactory().openSqlSession();
		parameters = new ArrayList<Object>();
	}
	
	@After
	public void end() {
		session.close();
	}
}
