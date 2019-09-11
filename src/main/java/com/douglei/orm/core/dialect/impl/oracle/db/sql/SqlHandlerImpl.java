package com.douglei.orm.core.dialect.impl.oracle.db.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.dialect.db.sql.SqlHandler;
import com.douglei.orm.core.sql.pagequery.PageSqlStatement;

/**
 * 
 * @author DougLei
 */
public class SqlHandlerImpl implements SqlHandler{
	private static final Logger logger = LoggerFactory.getLogger(SqlHandlerImpl.class);
	
	@Override
	public String installPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement) {
		int maxIndex = pageNum*pageSize;
		
		StringBuilder pageQuerySql = new StringBuilder(200 + statement.getWithClause().length() + statement.getSql().length());
		pageQuerySql.append(statement.getWithClause());
		pageQuerySql.append(" select jdb_orm_thrid_query_.* from (select jdb_orm_second_query_.*, rownum rn from (");
		pageQuerySql.append(statement.getSql());
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
