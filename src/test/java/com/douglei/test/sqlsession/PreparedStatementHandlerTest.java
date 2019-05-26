package com.douglei.test.sqlsession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
//		System.out.println(session.query("select * from sys_user where name = ?", parameters) == session.query("select * from sys_user where name = ?", parameters));
		
		List<Object> p2 = new ArrayList<Object>();
		p2.add("哈哈");
		System.out.println(session.query("select * from sys_user where name = ?", parameters) == session.query("select * from sys_user where name = ?", p2));
	}
	
	@Test
	public void executeUpdateTest() {
		parameters.add("id_" + new Date().getTime());
		parameters.add("name_" + new Date().getTime());
		session.executeUpdate("insert into sys_user (id, name) values(?,?)", parameters);
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
