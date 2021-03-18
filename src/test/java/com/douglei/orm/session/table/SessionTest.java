package com.douglei.orm.session.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.mapping.handler.MappingHandleException;
import com.douglei.orm.session.SysUser;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.orm.sessionfactory.sessions.Session;

public class SessionTest {
	
	@Test
	public void queryTest() {
		List<Map<String, Object>> list = session.getSqlSession().query("select * from bpm_ru_procinst");
		for (Map<String, Object> map : list) {
			System.out.println(map.get("START_TIME"));
			System.out.println(map.get("START_TIME").getClass());
		}
	}
	
	@Test
	public void saveTest() {
		SysUser user = new SysUser(0, "石磊", 28, "男");
		session.getTableSession().save(user);
		System.out.println("==============>" + user);
		
		Map<String, Object> user2 = new HashMap<String, Object>();
		user2.put("NAME", "成荣2");
		user2.put("AGE", 25);
		user2.put("SEX", "女");
		session.getTableSession().save("SYS_USER2", user2);
		System.out.println(user2);
	}
	
	
//	public static void main(String[] args) {
//		Configuration conf = new Configuration();
//		SessionFactory sf = conf.buildSessionFactory();
//		System.out.println("start");
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				SysUser user = new SysUser(0, "石磊33333", 28, "男");
//				Session session = sf.openSession();
//				session.getTableSession().save(user);
//				session.close();
//				System.out.println(Thread.currentThread().getName() + "==============>" + user);
//			}
//		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				SysUser user = new SysUser(0, "成荣3333", 25, "女");
//				Session session = sf.openSession();
//				session.getTableSession().save(user);
//				session.close();
//				System.out.println(Thread.currentThread().getName() + "==============>" + user);
//			}
//		}).start();
//		System.out.println("end");
//	}
	
	@Test
	public void test() throws MappingHandleException {
		System.out.println("测试存储过程映射");
		
//		sessionFactory.getMappingHandler().execute(new AddOrCoverMappingEntity("C:\\Users\\Administrator.USER-20190410XF\\Desktop\\SysMenu.tmp.xml"));
	}
	
	// --------------------------------------------------------------------------------------

	private Configuration conf;
	private SessionFactory sessionFactory;
	private Session session;
	
	@Before
	public void before() {
		conf = new Configuration();
		sessionFactory = conf.buildSessionFactory();
		session = sessionFactory.openSession();
	}
	@After
	public void end() {
		session.close();
	}
}
