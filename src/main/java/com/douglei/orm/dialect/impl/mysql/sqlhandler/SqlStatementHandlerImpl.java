package com.douglei.orm.dialect.impl.mysql.sqlhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.ConstraintMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sql.pagequery.PageSqlStatement;
import com.douglei.orm.sql.pagerecursivequery.PageRecursiveSqlStatement;

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
		return "select count(1) from information_schema.tables where table_schema = (select database()) and table_name = ? and table_type='BASE TABLE'";
	}

	@Override
	public String queryViewExists() {
		return "select count(1) from information_schema.tables where table_schema = (select database()) and table_name = ? and table_type='VIEW'";
	}
	
	@Override
	public String queryProcedureExists() {
		return "select count(1) from information_schema.routines where routine_schema = (select database()) and routine_type='PROCEDURE' and routine_name = ?";
	}
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	@Override
	protected String createDefaultValue(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("alter table ").append(tableName).append(" alter column ").append(constraint.getColumnNameList().get(0)).append(" set default ").append(constraint.getDefaultValue());
		return sql.toString();
	}

	@Override
	protected String dropPrimaryKey(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("alter table ").append(tableName).append(" drop primary key");
		return sql.toString();
	}

	@Override
	protected String dropUnique(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("alter table ").append(tableName).append(" drop index ").append(constraint.getName());
		return sql.toString();
	}

	@Override
	protected String dropDefaultValue(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("alter table ").append(tableName).append(" alter column ").append(constraint.getColumnNameList().get(0)).append(" set default null");
		return sql.toString();
	}
	
	@Override
	protected String dropCheck(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("alter table ").append(tableName).append(" drop ").append(constraint.getType().getSqlKey()).append(" ").append(constraint.getName());
		return sql.toString();
	}
	
	@Override
	protected String dropForeignKey(String tableName, ConstraintMetadata constraint) {
		return dropCheck(tableName, constraint);
	}
	
	// --------------------------------------------------------------------------------------------
	// 自增主键
	// --------------------------------------------------------------------------------------------
	@Override
	public String createAutoincrementPrimaryKey(TableMetadata table) {
		ColumnMetadata column = table.getColumnMap4Name().get(table.getAutoincrementPrimaryKey().getColumn());
		return "alter table "+table.getName()+" change column "+column.getName()+" "+column.getName()+" "+column.getDBDataType().getName()+" auto_increment";
	}
	
	@Override
	public String dropAutoincrementPrimaryKey(TableMetadata table) {
		ColumnMetadata column = table.getColumnMap4Name().get(table.getAutoincrementPrimaryKey().getColumn());
		return "alter table "+table.getName()+" change column "+column.getName()+" "+column.getName()+" "+column.getDBDataType().getName();
	}
	
	// --------------------------------------------------------------------------------------------
	// sql拼装
	// --------------------------------------------------------------------------------------------
	@Override
	public String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement) {
		StringBuilder pageQuerySql = new StringBuilder(180 + statement.length());
		if(statement.getWithClause() != null)
			pageQuerySql.append(statement.getWithClause()).append(' ');
		pageQuerySql.append("SELECT JDB_ORM_SECOND_QUERY_.* FROM (");
		pageQuerySql.append(statement.getSql());
		pageQuerySql.append(") JDB_ORM_SECOND_QUERY_");
		if(statement instanceof PageRecursiveSqlStatement) { // 分页递归查询
			pageQuerySql.append(" WHERE ");
			appendConditionSql2RecursiveSql(pageQuerySql, (PageRecursiveSqlStatement)statement);
		}
		pageQuerySql.append(" LIMIT ");
		pageQuerySql.append((pageNum-1)*pageSize);
		pageQuerySql.append(",");
		pageQuerySql.append(pageSize);
		if(logger.isDebugEnabled()) 
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass().getName(), pageQuerySql);
		return pageQuerySql.toString();
	}
}
