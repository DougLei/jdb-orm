package com.douglei.core.dialect.impl.mysql.db.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.core.dialect.db.sql.SqlHandler;

/**
 * 
 * @author DougLei
 */
public class SqlHandlerImpl implements SqlHandler{
	private static final Logger logger = LoggerFactory.getLogger(SqlHandlerImpl.class);
	
	@Override
	public String installPageQuerySql(int pageNum, int pageSize, String withClause, String sql) {
		StringBuilder pageQuerySql = new StringBuilder(80 + withClause.length() + sql.length());
		pageQuerySql.append(withClause);
		pageQuerySql.append(" select jdb_orm_second_query_.* from (");
		pageQuerySql.append(sql);
		pageQuerySql.append(") jdb_orm_second_query_ limit ");
		pageQuerySql.append((pageNum-1)*pageSize);
		pageQuerySql.append(",");
		pageQuerySql.append(pageSize);
		if(logger.isDebugEnabled()) {
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass().getName(), pageQuerySql.toString());
		}
		return pageQuerySql.toString();
	}
}
