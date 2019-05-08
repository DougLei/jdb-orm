package com.douglei.database.dialect;

/**
 * dialect处理器
 * @author DougLei
 */
public interface Dialect {
	
	/**
	 * 获取数据库编码值, 绝对唯一
	 * @return
	 */
	String getDatabaseCode();
	
	/**
	 * 获取数据库默认支持的事物隔离级别
	 * @return
	 */
	TransactionIsolationLevel getDefaultTransactionIsolationLevel();

	/**
	 * 组装成分页查询的sql语句
	 * @param pageSize 
	 * @param pageNo 
	 * @param sql
	 * @return
	 */
	String installPageQuerySql(int pageNo, int pageSize, String sql);
}
