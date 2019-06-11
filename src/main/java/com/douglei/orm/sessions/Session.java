package com.douglei.orm.sessions;

import java.sql.Connection;

import com.douglei.orm.core.dialect.TransactionIsolationLevel;
import com.douglei.orm.sessions.session.sql.SQLSession;
import com.douglei.orm.sessions.session.table.TableSession;
import com.douglei.orm.sessions.sqlsession.SqlSession;

/**
 * 
 * @author DougLei
 */
public interface Session {
	
	SqlSession getSqlSession();
	
	TableSession getTableSession();
	
	SQLSession getSQLSession();
	
	void commit();
	
	void rollback();
	
	void close();
	
	/**
	 * 获取数据库连接
	 * 该连接使用完成后<b>禁止直接关闭</b>, 其会随着session实例的关闭而关闭
	 * 通过该connection创建的其他对象需要自己手动关闭, 例如CallableStatement
	 * @return
	 */
	Connection getConnection();

	boolean isBeginTransaction();
	void beginTransaction();
	void setTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel);
}
