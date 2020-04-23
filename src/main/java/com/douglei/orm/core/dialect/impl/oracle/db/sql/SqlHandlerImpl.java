package com.douglei.orm.core.dialect.impl.oracle.db.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.dialect.db.sql.SqlHandler;
import com.douglei.orm.core.sql.pagequery.PageSqlStatement;

/**
 * 
 * @author DougLei
 */
public class SqlHandlerImpl extends SqlHandler{
	private static final Logger logger = LoggerFactory.getLogger(SqlHandlerImpl.class);
	
	@Override
	public String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement) {
		int maxIndex = pageNum*pageSize;
		
		StringBuilder pageQuerySql = new StringBuilder(200 + statement.getWithClause()==null?0:statement.getWithClause().length() + statement.getSql().length() + statement.getOrderByClause()==null?0:statement.getOrderByClause().length());
		if(statement.getWithClause() != null)
			pageQuerySql.append(statement.getWithClause()).append(' ');
		pageQuerySql.append("SELECT JDB_ORM_THIRD_QUERY_.* FROM (SELECT JDB_ORM_SECOND_QUERY_.*, ROWNUM RN FROM (");
		pageQuerySql.append(statement.getSql());
		pageQuerySql.append(") JDB_ORM_SECOND_QUERY_ WHERE ROWNUM <= ");
		pageQuerySql.append(maxIndex);
		pageQuerySql.append(") JDB_ORM_THIRD_QUERY_ WHERE JDB_ORM_THIRD_QUERY_.RN > ");
		pageQuerySql.append(maxIndex-pageSize);
		if(logger.isDebugEnabled()) {
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass().getName(), pageQuerySql);
		}
		return pageQuerySql.toString();
	}
}
