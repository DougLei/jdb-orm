package com.douglei.test.sqlsession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.impl.xml.XmlConfiguration;
import com.douglei.session.SqlSession;

public class PreparedStatementHandlerTest {
	
	@Test
	public void queryTest() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add("哈哈");
		
		List<Map<String, Object>> list = session.query("select * from sys_user where name = ?", parameters);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void insertTest() {
		
	}
	
	@Test
	public void deleteTest() {
		
	}
	
	@Test
	public void updateTest() {
		
	}
	
	@Test
	public void procedureTest() {
		
	}

	
	
	// --------------------------------------------------------------------------------------
	
	private Configuration conf;
	private SqlSession session;
	
	@Before
	public void before() {
		conf = new XmlConfiguration();
		session = conf.buildSessionFactory().openSqlSession(false);
	}
	
	@After
	public void end() {
		session.close();
	}
}
