package com.douglei.database.dialect.impl.oracle.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.sql.SqlHandler;

/**
 * 
 * @author DougLei
 */
public class SqlHandlerImpl implements SqlHandler{
	private static final Logger logger = LoggerFactory.getLogger(SqlHandlerImpl.class);
	
	@Override
	public String installPageQuerySql(int pageNum, int pageSize, String withClause, String sql) {
		int maxIndex = pageNum*pageSize;
		
		StringBuilder pageQuerySql = new StringBuilder(200 + withClause.length() + sql.length());
		pageQuerySql.append(withClause);
		pageQuerySql.append(" select jdb_orm_thrid_query_.* from (select jdb_orm_second_query_.*, rownum rn from (");
		pageQuerySql.append(sql);
		pageQuerySql.append(") jdb_orm_second_query_ where rownum <= ");
		pageQuerySql.append(maxIndex);
		pageQuerySql.append(") jdb_orm_thrid_query_ where jdb_orm_thrid_query_.rn > ");
		pageQuerySql.append(maxIndex-pageSize);
		if(logger.isDebugEnabled()) {
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass().getName(), pageQuerySql.toString());
		}
		return pageQuerySql.toString();
	}
}
