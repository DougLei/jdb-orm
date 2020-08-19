package com.douglei.orm.session.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.orm.session.SysUser;
import com.douglei.orm.sessionfactory.sessions.Session;

public class SessionTest {
	
	@Test
	public void queryTest() {
		PageResult<SysUser> page = session.getTableSession().pageQuery(SysUser.class, 1, 10, "with b as (select"
				+ ""
				+ " * from "
				+ ""
				+ "\nsys_user"
				+ ""
				+ "\n) select * from b");
		
		System.out.println(page.toString());
		
		List<SysUser> users = page.getResultDatas();
		System.out.println(users);
		for (SysUser sysUser : users) {
			System.out.println(sysUser);
		}
	}
	
	@Test
	public void saveTest() {
		SysUser user = new SysUser(null, "石磊", 28, "男");
		session.getTableSession().save(user);
		System.out.println(user.getId());
		
		Map<String, Object> user2 = new HashMap<String, Object>();
		user2.put("NAME", "成荣");
		user2.put("AGE", 25);
		user2.put("SEX", "女");
		session.getTableSession().save("SYS_USER2", user2);
		System.out.println(user2.get("DD"));
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
