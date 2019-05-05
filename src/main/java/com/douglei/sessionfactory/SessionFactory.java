package com.douglei.sessionfactory;

import com.douglei.sessions.session.sql.SQLSession;
import com.douglei.sessions.session.table.TableSession;
import com.douglei.sessions.sqlsession.SqlSession;

/**
 * 创建session的factory接口
 * @author DougLei
 */
public interface SessionFactory {
	
	/**
	 * <pre>
	 * 	开启TableSession实例
	 * 	默认开启事物
	 * </pre>
	 * @return
	 */
	TableSession openTableSession();
	
	/**
	 * <pre>
	 * 	开启TableSession实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @return
	 */
	TableSession openTableSession(boolean beginTransaction);
	
	/**
	 * <pre>
	 * 	开启SQLSession实例
	 * 	默认开启事物
	 * </pre>
	 * @return
	 */
	SQLSession openSQLSession();
	
	/**
	 * <pre>
	 * 	开启SQLSession实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @return
	 */
	SQLSession openSQLSession(boolean beginTransaction);
	
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
