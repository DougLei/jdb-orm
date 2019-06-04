package com.douglei.sessions;

import java.sql.Connection;

import com.douglei.sessions.session.sql.SQLSession;
import com.douglei.sessions.session.table.TableSession;
import com.douglei.sessions.sqlsession.SqlSession;

/**
 * 
 * @author DougLei
 */
public interface Session {
	
	SqlSession createSqlSession();
	
	TableSession createTableSession();
	
	SQLSession createSQLSession();
	
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
}
