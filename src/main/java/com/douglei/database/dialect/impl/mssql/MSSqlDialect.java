package com.douglei.database.dialect.impl.mssql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.TransactionIsolationLevel;

/**
 * 
 * @author DougLei
 */
public final class MSSqlDialect implements Dialect{
	private static final Logger logger = LoggerFactory.getLogger(MSSqlDialect.class);
	
	private MSSqlDialect() {}
	private static final MSSqlDialect instance =new MSSqlDialect();
	public static final MSSqlDialect singleInstance() {
		return instance;
	}
	
	public String getDatabaseCode() {
		return "MSSQL";
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
