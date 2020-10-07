package com.douglei.orm.sessionfactory.sessions;

import java.sql.Connection;

import com.douglei.orm.environment.datasource.TransactionIsolationLevel;
import com.douglei.orm.sessionfactory.sessions.session.sql.SQLSession;
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
	 * 提交后会自动close
	 */
	void commit();
	
	/**
	 * 回滚后会自动close
	 */
	void rollback();
	
	/**
	 * 关闭
	 * 如果开启了事物会默认进行commit, 如果commit失败, 则自动进行回滚
	 */
	void close();
	
	/**
	 * 获取数据库连接
	 * 该连接使用完成后<b>禁止直接关闭</b>, 其会随着session实例的关闭而关闭
	 * 通过该connection创建的其他对象需要自己手动关闭, 例如CallableStatement
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
	 * 设置事物的隔离级别, 传入null则使用jdbc驱动中的默认隔离级别
	 * @param transactionIsolationLevel
	 */
	void setTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel);
}
