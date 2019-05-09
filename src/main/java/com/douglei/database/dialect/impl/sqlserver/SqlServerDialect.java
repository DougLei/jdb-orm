package com.douglei.database.dialect.impl.sqlserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.impl.AbstractDialect;

/**
 * 
 * @author DougLei
 */
public final class SqlServerDialect extends AbstractDialect{
	private static final Logger logger = LoggerFactory.getLogger(SqlServerDialect.class);
	
	private SqlServerDialect() {
		setDataTypeHandlerMapping(DataTypeHandlerMapping.singleInstance());
	}
	private static final SqlServerDialect instance =new SqlServerDialect();
	public static final SqlServerDialect singleInstance() {
		return instance;
	}
	
	@Override
	public String getDatabaseCode() {
		return "SQLSERVER";
	}
	
	@Override
	public String installPageQuerySql(int pageNum, int pageSize, String sql) {
		int maxIndex = pageNum*pageSize;
		
		StringBuilder pageQuerySql = new StringBuilder(240 + sql.length());
		pageQuerySql.append("select jdb_orm_thrid_query_.* from (select top ");
		pageQuerySql.append(maxIndex);
		pageQuerySql.append(" row_number() over(order by current_timestamp) as rn, jdb_orm_second_query_.* from (");
		pageQuerySql.append(sql);
		pageQuerySql.append(") jdb_orm_second_query_) jdb_orm_thrid_query_ where jdb_orm_thrid_query_.rn >");
		pageQuerySql.append(maxIndex-pageSize);
		if(logger.isDebugEnabled()) {
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass(), pageQuerySql.toString());
		}
		return pageQuerySql.toString();
	}
}
