package com.douglei.orm.session.table;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.orm.session.SysUser;
import com.douglei.orm.sessions.session.table.TableSession;

public class SessionTest {
	
	@Test
	public void queryTest() {
		PageResult<SysUser> page = session.pageQuery(SysUser.class, 1, 10, "select * from sys_user");
		
		System.out.println(page.toString());
		
		List<SysUser> users = page.getResultDatas();
		for (SysUser sysUser : users) {
			System.out.println(sysUser);
		}
	}
	
	@Test
	public void saveTest() {
		session.save(SysUser.getList().get(0));
	}
	
	// --------------------------------------------------------------------------------------

	@SuppressWarnings("unused")
	private Configuration conf;
	private TableSession session;
	
	@Before
	public void before() {
		conf = new XmlConfiguration();
//		session = conf.buildSessionFactory().openTableSession();
	}
	@After
	public void end() {
//		session.close();
	}
}
