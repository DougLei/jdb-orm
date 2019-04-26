package com.douglei.test.sqlsession;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.impl.xml.XmlConfiguration;
import com.douglei.session.SqlSession;

public class StatementHandlerTest {
	
	@Test
	public void queryTest() {
		Configuration conf = new XmlConfiguration();
		SqlSession session = conf.buildSessionFactory().openSqlSession(false);
		List<Map<String, Object>> list = session.query("select * from sys_user");
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
		session.close();
	}
}
