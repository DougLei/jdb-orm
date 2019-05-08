package com.douglei.database.dialect.impl.oracle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.TransactionIsolationLevel;

/**
 * 
 * @author DougLei
 */
public final class OracleDialect implements Dialect{
	private static final Logger logger = LoggerFactory.getLogger(OracleDialect.class);
	
	private OracleDialect() {}
	private static final OracleDialect instance =new OracleDialect();
	public static final OracleDialect singleInstance() {
		return instance;
	}
	
	public String getDatabaseCode() {
		return "ORACLE";
	}
	
	public TransactionIsolationLevel getDefaultTransactionIsolationLevel() {
		return TransactionIsolationLevel.READ_COMMITTED;
	}

	@Override
	public String installPageQuerySql(int pageNum, int pageSize, String sql) {
		int maxIndex = pageNum*pageSize;
		
		StringBuilder pageQuerySql = new StringBuilder(180 + sql.length());
		pageQuerySql.append("select thrid_query_.* from (select second_query_rownum_.*, rownum rn from (");
		pageQuerySql.append(sql);
		pageQuerySql.append(") second_query_rownum_ where rownum <= ");
		pageQuerySql.append(maxIndex);
		pageQuerySql.append(") thrid_query_ where thrid_query_.rn > ");
		pageQuerySql.append(maxIndex-pageNum);
		if(logger.isDebugEnabled()) {
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass(), pageQuerySql.toString());
		}
		return pageQuerySql.toString();
	}
}
