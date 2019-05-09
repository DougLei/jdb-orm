package com.douglei.test.session.table;

import java.sql.DriverManager;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.impl.xml.XmlConfiguration;
import com.douglei.database.sql.pagequery.PageResult;
import com.douglei.sessions.session.table.TableSession;

public class SessionTest {
	
	@Test
	public void queryTest() {
		PageResult<SysUser> page = session.pageQuery(SysUser.class, 3, 5, "select * from sys_user");
		
		System.out.println(page.toString());
		
		List<SysUser> users = page.getResultDatas();
		for (SysUser sysUser : users) {
			System.out.println(sysUser);
		}
	}
	
	@Test
	public void saveTest() {
		session.save(SysUser.getList().get(1));
		session.delete(SysUser.getList().get(1));
		session.delete(SysUser.getList().get(1));
	}
	
	@Test
	public void procedureTest() throws Exception {
		String className = "com.mysql.cj.jdbc.Driver";
		String url =  "jdbc:mysql://localhost:3306/douglei?characterEncoding=utf8&useSSL=true";
		String username = "root";
		String pwd = "root";
		
		Class.forName(className);
		System.out.println(DriverManager.getConnection(url, username, pwd).getTransactionIsolation());
	}
	
	
	
	// --------------------------------------------------------------------------------------
	
	private Configuration conf;
	private TableSession session;
	
	@Before
	public void before() {
		conf = new XmlConfiguration();
		session = conf.buildSessionFactory().openTableSession();
	}
	@After
	public void end() {
		session.close();
	}
}
