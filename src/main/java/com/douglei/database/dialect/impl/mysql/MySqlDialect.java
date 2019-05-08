package com.douglei.database.dialect.impl.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.TransactionIsolationLevel;

/**
 * 
 * @author DougLei
 */
public final class MySqlDialect implements Dialect{
	private static final Logger logger = LoggerFactory.getLogger(MySqlDialect.class);
	
	private MySqlDialect() {}
	private static final MySqlDialect instance =new MySqlDialect();
	public static final MySqlDialect singleInstance() {
		return instance;
	}

	public String getDatabaseCode() {
		return "MYSQL";
	}
	
	public TransactionIsolationLevel getDefaultTransactionIsolationLevel() {
		return TransactionIsolationLevel.READ_COMMITTED;
	}
	
	@Override
	public String installPageQuerySql(int pageNum, int pageSize, String sql) {
		int maxIndex = pageNum*pageSize;
		
		StringBuilder pageQuerySql = new StringBuilder(10000 + sql.length());
	
		
		
		
		
		
		
		
		
		if(logger.isDebugEnabled()) {
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass(), pageQuerySql.toString());
		}
		return pageQuerySql.toString();
	}
}
