package com.douglei.orm.factory.sessionfactory;

import com.douglei.orm.core.dialect.TransactionIsolationLevel;
import com.douglei.orm.core.dialect.db.table.TableSqlStatementHandler;
import com.douglei.orm.factory.sessions.Session;

/**
 * 
 * @author DougLei
 */
public interface SessionFactory {
	
	/**
	 * <pre>
	 * 	开启Session实例
	 * 	默认开启事物
	 * </pre>
	 * @return
	 */
	Session openSession();
	
	/**
	 * <pre>
	 * 	开启Session实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @return
	 */
	Session openSession(boolean beginTransaction);
	
	/**
	 * <pre>
	 * 	开启Session实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果传入null, 则使用jdbc默认的隔离级别
	 * @return
	 */
	Session openSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel);
	
	/**
	 * 获取表sql语句处理器
	 * @return
	 */
	TableSqlStatementHandler getTableSqlStatementHandler();
	
	/**
	 * 销毁
	 */
	void destroy();
	
	/**
	 * 
	 * @return
	 */
	String getId();
}
