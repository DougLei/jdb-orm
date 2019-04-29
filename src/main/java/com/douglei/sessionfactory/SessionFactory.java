package com.douglei.sessionfactory;

import com.douglei.sessions.Session;
import com.douglei.sessions.SqlSession;

/**
 * 创建session的factory接口
 * @author DougLei
 */
public interface SessionFactory {
	
	/**
	 * <pre>
	 * 	开启session实例
	 * 	默认开启事物
	 * </pre>
	 * @return
	 */
	Session openSession();
	
	/**
	 * <pre>
	 * 	开启session实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @return
	 */
	Session openSession(boolean beginTransaction);
	
	/**
	 * <pre>
	 * 	开启sql session实例
	 * 	默认开启事物
	 * </pre>
	 * @return
	 */
	SqlSession openSqlSession();
	
	/**
	 * <pre>
	 * 	开启sql session实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @return
	 */
	SqlSession openSqlSession(boolean beginTransaction);
}
