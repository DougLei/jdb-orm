package com.douglei.orm.sessionfactory.sessions;

import java.sql.Connection;

import com.douglei.orm.configuration.environment.datasource.TransactionIsolationLevel;
import com.douglei.orm.sessionfactory.sessions.session.sql.SQLSession;
import com.douglei.orm.sessionfactory.sessions.session.sqlquery.SQLQuerySession;
import com.douglei.orm.sessionfactory.sessions.session.table.TableSession;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSession;

/**
 * 
 * @author DougLei
 */
public interface Session {
	
	/**
	 * 
	 * @return
	 */
	SqlSession getSqlSession();
	
	/**
	 * 
	 * @return
	 */
	TableSession getTableSession();
	
	/**
	 * 
	 * @return
	 */
	SQLSession getSQLSession();
	
	/**
	 * 
	 * @return
	 */
	SQLQuerySession getSQLQuerySession();
	
	/**
	 * 提交, 遇到异常时会记录日志, 并自动回滚
	 */
	void commit();
	
	/**
	 * 回滚, 遇到异常时会记录日志
	 */
	void rollback();
	
	/**
	 * 关闭, 遇到异常时会记录日志
	 */
	void close();
	
	/**
	 * 获取数据库原生连接; 该连接使用完成后<b>禁止直接关闭</b>, 其会随着session实例的关闭而关闭
	 * <p>
	 * 通过该Connection创建的其他对象需要手动关闭, 例如Statement
	 * @return
	 */
	Connection getConnection();

	/**
	 * 是否开启事物
	 * @return
	 */
	boolean isBeginTransaction();
	
	/**
	 * 开启事物
	 */
	void beginTransaction();
	
	/**
	 * 更新事物的隔离级别
	 * @param transactionIsolationLevel
	 */
	void updateTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel);
}
