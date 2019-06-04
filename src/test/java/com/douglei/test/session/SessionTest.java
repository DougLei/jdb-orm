package com.douglei.test.session;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.impl.xml.XmlConfiguration;
import com.douglei.sessionfactory.SessionFactory;
import com.douglei.sessions.Session;

public class SessionTest {
	public static void main(String[] args) {
		Configuration configuration = new XmlConfiguration();
		SessionFactory sf = configuration.buildSessionFactory();
		
		Session session = sf.openSession(true);
		
		session.createTableSession().save(SysUser.getList().get(1));
		
		session.close();
		
		
	}
}
