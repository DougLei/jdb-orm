package com.douglei.orm.session;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;
import com.douglei.orm.factory.OrmFactory;
import com.douglei.orm.factory.sessions.Session;

public class SessionTest {
	public static void main(String[] args) {
		Configuration configuration = new XmlConfiguration();
		OrmFactory sf = configuration.buildSessionFactory();
		
		Session session = sf.openSession(true);
		
		session.getTableSession().save(SysUser.getList().get(1));
		
		session.close();
	}
}
