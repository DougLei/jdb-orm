package com.douglei.test.session.table;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.impl.xml.XmlConfiguration;
import com.douglei.sessions.session.table.TableSession;

public class SessionTest {
	
	@Test
	public void queryTest() {
		List<SysUser> users = session.query(SysUser.class, "select * from sys_user", null);
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
	public void procedureTest() {
		
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
