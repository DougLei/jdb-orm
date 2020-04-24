package com.douglei.orm.core.dialect.impl.sqlserver.db.sql;

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
	public boolean needExtractOrderByClause() {
		return true;
	}

	@Override
	public String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement) {
		int maxIndex = pageNum*pageSize;
		
		StringBuilder pageQuerySql = new StringBuilder(240 + statement.length());
		if(statement.getWithClause() != null)
			pageQuerySql.append(statement.getWithClause()).append(' ');
		pageQuerySql.append("SELECT JDB_ORM_THIRD_QUERY_.* FROM (SELECT TOP ");
		pageQuerySql.append(maxIndex);
		pageQuerySql.append(" ROW_NUMBER() OVER(").append((statement.getOrderByClause()==null?"ORDER BY CURRENT_TIMESTAMP":statement.getOrderByClause())).append(") AS RN, JDB_ORM_SECOND_QUERY_.* FROM (");
		pageQuerySql.append(statement.getSql());
		pageQuerySql.append(") JDB_ORM_SECOND_QUERY_) JDB_ORM_THIRD_QUERY_ WHERE JDB_ORM_THIRD_QUERY_.RN >");
		pageQuerySql.append(maxIndex-pageSize);
		if(logger.isDebugEnabled()) {
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass().getName(), pageQuerySql);
		}
		return pageQuerySql.toString();
	}

	@Override
	public String getPageRecursiveQuerySql(int pageNum, int pageSize, PageRecursiveSqlStatement pageRecursiveSqlStatement) {
		// TODO Auto-generated method stub
		return null;
	}
}
