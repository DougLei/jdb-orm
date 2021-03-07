package com.douglei.orm.dialect.impl.oracle.sqlhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.orm.mapping.impl.table.metadata.ConstraintMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sql.query.page.PageSqlStatement;

/**
 * 
 * @author DougLei
 */
public class SqlStatementHandlerImpl extends SqlStatementHandler{
	private static final Logger logger = LoggerFactory.getLogger(SqlStatementHandlerImpl.class);
	
	
	// --------------------------------------------------------------------------------------------
	// query
	// --------------------------------------------------------------------------------------------
	@Override
	public String queryTableExists() {
		return "select count(1) from user_objects where object_name =? and object_type = 'TABLE'";
	}

	@Override
	public String queryViewExists() {
		return "select count(1) from user_objects where object_name =? and object_type = 'VIEW'";
	}

	@Override
	public String queryProcedureExists() {
		return "select count(1) from user_objects where object_name =? and object_type = 'PROCEDURE'";
	}
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	@Override
	protected String createDefaultValue(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("alter table ").append(tableName).append(" modify ").append(constraint.getColumnNameList().get(0)).append(" default ").append(constraint.getDefaultValue());
		return sql.toString();
	}

	@Override
	protected String dropDefaultValue(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("alter table ").append(tableName).append(" modify ").append(constraint.getColumnNameList().get(0)).append(" default null");
		return sql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// 自增主键
	// --------------------------------------------------------------------------------------------
	@Override
	public String createAutoincrementPrimaryKey(TableMetadata table) {
		return "create sequence "+table.getAutoincrementPrimaryKey().getSequenceName()+" nocache";
	}
	
	@Override
	public String dropAutoincrementPrimaryKey(TableMetadata table) {
		return "drop sequence " + table.getAutoincrementPrimaryKey().getSequenceName();
	}
	
	// --------------------------------------------------------------------------------------------
	// sql拼装
	// --------------------------------------------------------------------------------------------
	@Override
	public String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement) {
		StringBuilder pageQuerySql = new StringBuilder(300 + statement.getTotalLength());
		
		if(statement.getWithClause() != null)
			pageQuerySql.append(statement.getWithClause()).append(' ');
		
		int maxIndex = pageNum*pageSize;
		pageQuerySql.append("SELECT JDB_ORM_THIRD_QUERY_.* FROM (SELECT JDB_ORM_SECOND_QUERY_.*, ROWNUM RN FROM (");
		pageQuerySql.append(statement.getQuerySQL());
		pageQuerySql.append(") JDB_ORM_SECOND_QUERY_ WHERE ROWNUM <= ");
		pageQuerySql.append(maxIndex);
		pageQuerySql.append(" ) JDB_ORM_THIRD_QUERY_ WHERE JDB_ORM_THIRD_QUERY_.RN > ");
		pageQuerySql.append(maxIndex-pageSize);
		
		logger.debug("进行分页查询的sql语句为: {}", pageQuerySql);	
		return pageQuerySql.toString();
	}
}
