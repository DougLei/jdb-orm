package com.douglei.sessionfactory;

import com.douglei.database.dialect.TransactionIsolationLevel;
import com.douglei.sessions.session.sql.SQLSession;
import com.douglei.sessions.session.table.TableSession;
import com.douglei.sessions.sqlsession.SqlSession;

/**
 * 创建session的factory接口
 * @author DougLei
 */
public interface SessionFactory {
	
	/**
	 * 动态添加映射, 如果存在, 则覆盖
	 * @param mappingConfigurationContent
	 */
	void dynamicAddMapping(String mappingConfigurationContent);
	
	/**
	 * 移除映射
	 * @param mappingCode
	 */
	void removeMapping(String mappingCode);
	
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
	 * 	开启TableSession实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果传入null, 则使用jdbc默认的隔离级别
	 * @return
	 */
	TableSession openTableSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel);
	
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
	 * 	开启SQLSession实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果传入null, 则使用jdbc默认的隔离级别
	 * @return
	 */
	SQLSession openSQLSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel);
	
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
	
	/**
	 * <pre>
	 * 	开启sql session实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果传入null, 则使用jdbc默认的隔离级别
	 * @return
	 */
	SqlSession openSqlSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel);
}
