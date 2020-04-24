package com.douglei.orm.core.dialect.impl.mysql.db.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.dialect.db.sql.SqlHandler;
import com.douglei.orm.core.sql.pagequery.PageSqlStatement;
import com.douglei.orm.core.sql.pagerecursivequery.PageRecursiveSqlStatement;

/**
 * 
 * @author DougLei
 */
public class SqlHandlerImpl extends SqlHandler{
	private static final Logger logger = LoggerFactory.getLogger(SqlHandlerImpl.class);
	
	@Override
	public String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement) {
		StringBuilder pageQuerySql = new StringBuilder(80 + statement.length());
		if(statement.getWithClause() != null)
			pageQuerySql.append(statement.getWithClause()).append(' ');
		pageQuerySql.append("SELECT JDB_ORM_SECOND_QUERY_.* FROM (");
		pageQuerySql.append(statement.getSql());
		pageQuerySql.append(") JDB_ORM_SECOND_QUERY_ +这里加上递归的条件语句+ LIMIT ");
		pageQuerySql.append((pageNum-1)*pageSize);
		pageQuerySql.append(",");
		pageQuerySql.append(pageSize);
		if(logger.isDebugEnabled()) 
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass().getName(), pageQuerySql);
		return pageQuerySql.toString();
	}

	@Override
	public String getPageRecursiveQuerySql(int pageNum, int pageSize, PageRecursiveSqlStatement pageRecursiveSqlStatement) {
		// TODO Auto-generated method stub
		return null;
	}
}
