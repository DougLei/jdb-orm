package com.douglei.orm.dialect.db.sql;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.Constraint;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sql.pagequery.PageSqlStatement;
import com.douglei.orm.sql.recursivequery.RecursiveSqlStatement;

/**
 * sql语句处理器
 * @author DougLei
 */
public abstract class SqlStatementHandler {
	private static final Logger logger = LoggerFactory.getLogger(SqlStatementHandler.class);
	
	// --------------------------------------------------------------------------------------------
	// query
	// --------------------------------------------------------------------------------------------
	/**
	 * 获取 查询指定name是否存在的sql语句, 目前是查询表名, 视图名, 存储过程名的集合
	 */
	public abstract String queryNameExists();
	
	/**
	 * 获取 查询表名是否存在的sql语句
	 * @return
	 */
	public abstract String queryTableExists();
	
	/**
	 * 获取 查询视图名是否存在的sql语句
	 * @return
	 */
	public abstract String queryViewExists();
	
	/**
	 * 获取 查询存储过程名是否存在的sql语句
	 * @return
	 */
	public abstract String queryProcExists();
	
	/**
	 * 获取 查询视图的脚本
	 * @return
	 */
	public abstract String queryViewScript();
	
	/**
	 * 获取 查询存储过程的脚本
	 * @return
	 */
	public abstract String queryProcedureScript();
	
	// --------------------------------------------------------------------------------------------
	// view
	// --------------------------------------------------------------------------------------------
	/**
	 * 获取drop view的sql语句
	 * @param viewName
	 * @return
	 */
	public String dropView(String viewName) {
		return "drop view " + viewName;
	}
	
	// --------------------------------------------------------------------------------------------
	// procedure
	// --------------------------------------------------------------------------------------------
	/**
	 * 获取drop procedure的sql语句
	 * @param procName
	 * @return
	 */
	public String dropProcedure(String procName) {
		return "drop procedure " + procName;
	}
	
	// --------------------------------------------------------------------------------------------
	// table
	// --------------------------------------------------------------------------------------------
	/**
	 * 获取create table的sql语句
	 * @param table
	 * @return
	 */
	public String createTable(TableMetadata table) {
		StringBuilder sql = new StringBuilder(1000);
		sql.append("create table ").append(table.getName());
		sql.append("(");
		
		Collection<ColumnMetadata> columns = table.getDeclareColumns();
		for (ColumnMetadata column : columns) {
			sql.append(column.getName()).append(" ");
			sql.append(column.getDBDataType().getDBType4SqlStatement(column.getLength(), column.getPrecision())).append(" ");
			if(column.isPrimaryKeySequence()) {
				sql.append(primaryKeySequenceSqlKeyword()).append(" ");
			}
			if(!column.isNullable()) {
				sql.append("not null");
			}
			sql.append(",");
		}
		sql.setLength(sql.length()-1);
		sql.append(")");
		return sql.toString();
	}
	
	/**
	 * 主键序列的sql关键字
	 * @return
	 */
	protected String primaryKeySequenceSqlKeyword() {
		return "";
	}

	/**
	 * 获取drop table的sql语句
	 * @param tableName
	 * @return
	 */
	public String dropTable(String tableName) {
		return "drop table " + tableName;
	}
	
	/**
	 * 获取rename 表名的sql语句
	 * @param originTableName
	 * @param targetTableName
	 * @return
	 */
	public String renameTable(String originTableName, String targetTableName) {
		StringBuilder tmpSql = new StringBuilder(80);
		tmpSql.append("alter table ").append(originTableName).append(" rename to ").append(targetTableName);
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// column
	// --------------------------------------------------------------------------------------------
	/**
	 * 获取create column的sql语句
	 * @param tableName
	 * @param column
	 * @return
	 */
	public String createColumn(String tableName, ColumnMetadata column) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(tableName).append(" add ").append(column.getName()).append(" ");
		tmpSql.append(column.getDBDataType().getDBType4SqlStatement(column.getLength(), column.getPrecision())).append(" ");
		if(!column.isNullable()) {
			tmpSql.append("not null");
		}
		return tmpSql.toString();
	}
	
	/**
	 * 获取drop column的sql语句
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public String dropColumn(String tableName, String columnName) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(tableName).append(" drop column ").append(columnName);
		return tmpSql.toString();
	}
	
	/**
	 * 获取rename 列名的sql语句
	 * @param tableName
	 * @param originColumnName
	 * @param targetColumnName
	 * @return
	 */
	public String renameColumn(String tableName, String originColumnName, String targetColumnName) {
		StringBuilder tmpSql = new StringBuilder(80);
		tmpSql.append("alter table ").append(tableName).append(" rename column ").append(originColumnName).append(" to ").append(targetColumnName);
		return tmpSql.toString();
	}
	
	/**
	 * 获取修改列的sql语句
	 * @param tableName
	 * @param column
	 * @return
	 */
	public String modifyColumn(String tableName, ColumnMetadata column) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(tableName).append(" modify ").append(column.getName()).append(" ");
		tmpSql.append(column.getDBDataType().getDBType4SqlStatement(column.getLength(), column.getPrecision())).append(" ");
		if(!column.isNullable()) {
			tmpSql.append("not null");
		}
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	/**
	 * 获取create constraint的sql语句
	 * @param constraint
	 * @return
	 */
	public String createConstraint(Constraint constraint) {
		switch(constraint.getConstraintType()) {
			case PRIMARY_KEY:
			case UNIQUE:
				return createPK_UQ(constraint);
			case DEFAULT_VALUE:
				return createDefaultValue(constraint);
			case CHECK:
				return createCheck(constraint);
			case FOREIGN_KEY:
				return createForeignKey(constraint);
		}
		throw new IllegalArgumentException("没有处理到的约束: " + constraint.getConstraintType());
	}
	/**获取创建主键约束、唯一约束的sql语句*/
	protected String createPK_UQ(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" add constraint ").append(constraint.getName()).append(" ");
		tmpSql.append(constraint.getConstraintType().getSqlStatement()).append(" (").append(constraint.getConstraintColumnNames()).append(")");
		return tmpSql.toString();
	}
	/**获取创建默认值约束的sql语句*/
	protected abstract String createDefaultValue(Constraint constraint);
	/**获取创建检查约束的sql语句*/
	protected String createCheck(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" add constraint ").append(constraint.getName()).append(" ");
		tmpSql.append(constraint.getConstraintType().getSqlStatement()).append(" (").append(constraint.getCheck()).append(")");
		return tmpSql.toString();
	}
	/**获取创建外键约束的sql语句*/
	protected String createForeignKey(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(120);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" add constraint ").append(constraint.getName()).append(" ");
		tmpSql.append(constraint.getConstraintType().getSqlStatement()).append(" (").append(constraint.getConstraintColumnNames()).append(") ");
		tmpSql.append("references ").append(constraint.getFkTableName()).append("(").append(constraint.getFkColumnName()).append(")");
		return tmpSql.toString();
	}

	/**
	 * 获取drop constraint的sql语句
	 * @param constraint
	 * @return
	 */
	public String dropConstraint(Constraint constraint) {
		switch(constraint.getConstraintType()) {
			case PRIMARY_KEY:
				return dropPrimaryKey(constraint);
			case UNIQUE:
				return dropUnique(constraint);
			case DEFAULT_VALUE:
				return dropDefaultValue(constraint);
			case CHECK:
				return dropCheck(constraint);
			case FOREIGN_KEY:
				return dropForeignKey(constraint);
		}
		throw new IllegalArgumentException("没有处理到的约束: " + constraint.getConstraintType());
	}
	/**通用的 获取drop constraint的sql语句*/
	private String commonDropConstraint(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" drop constraint ").append(constraint.getName());
		return tmpSql.toString();
	}
	/**获取删除主键约束的sql语句*/
	protected String dropPrimaryKey(Constraint constraint) {
		return commonDropConstraint(constraint);
	}
	/**获取删除唯一约束的sql语句*/
	protected String dropUnique(Constraint constraint) {
		return commonDropConstraint(constraint);
	}
	/**获取删除默认值约束的sql语句*/
	protected String dropDefaultValue(Constraint constraint) {
		return commonDropConstraint(constraint);
	}
	/**获取删除检查约束的sql语句*/
	protected String dropCheck(Constraint constraint) {
		return commonDropConstraint(constraint);
	}
	/**获取删除外键约束的sql语句*/
	protected String dropForeignKey(Constraint constraint) {
		return commonDropConstraint(constraint);
	}
	
	
	// --------------------------------------------------------------------------------------------
	// sql拼装
	// --------------------------------------------------------------------------------------------
	/**
	 * 是否需要提取order by子句, 目前仅sqlserver需要
	 * @return
	 */
	public boolean needExtractOrderByClause() {
		return false;
	}
	
	/**
	 * 获取分页查询的sql语句
	 * @param pageNum 
	 * @param pageSize 
	 * @param statement
	 * @return
	 */
	public abstract String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement);
	
	/**
	 * 获取递归查询的sql语句
	 * @param statement
	 * @return
	 */
	public String getRecursiveSql(RecursiveSqlStatement statement) {
		StringBuilder recursiveQuerySql = statement.getRecursiveQuerySqlCache();
		if(recursiveQuerySql == null) {
			recursiveQuerySql = new StringBuilder(86 + statement.length());
			
			if(statement.getWithClause() != null)
				recursiveQuerySql.append(statement.getWithClause()).append(' ');
			recursiveQuerySql.append("SELECT JDB_ORM_RECURSIVE_QUERY_.* FROM (");
			recursiveQuerySql.append(statement.getSql());
			recursiveQuerySql.append(") JDB_ORM_RECURSIVE_QUERY_ WHERE ");
			
			statement.setRecursiveQuerySqlCache(recursiveQuerySql);
		}
		appendConditionSql2RecursiveSql(recursiveQuerySql, statement);	
		
		if(statement.getOrderByClause() != null)
			recursiveQuerySql.append(' ').append(statement.getOrderByClause());
		if(logger.isDebugEnabled())
			logger.debug("{} 进行递归查询的sql语句为: {}", getClass().getName(), recursiveQuerySql);
		return recursiveQuerySql.toString();
	}
	
	/**
	 * 给递归查询的sql语句追加条件
	 * @param recursiveQuerySql
	 * @param statement
	 */
	public void appendConditionSql2RecursiveSql(StringBuilder recursiveQuerySql, RecursiveSqlStatement statement) { 
		recursiveQuerySql.append('(');
		int parentValueListSize = statement.parentValueListSize();
		if(statement.parentValueExistNull()) {
			recursiveQuerySql.append(statement.getParentPkColumnName()).append(" IS NULL");
			
			if(parentValueListSize > 0) {
				recursiveQuerySql.append(" OR ");
			}
		}
		
		if(parentValueListSize > 0) {
			recursiveQuerySql.append(statement.getParentPkColumnName());
			if(parentValueListSize == 1) {
				recursiveQuerySql.append("=?");
			}else {
				recursiveQuerySql.append(" IN (");
				for(int i=0;i<parentValueListSize;i++) {
					recursiveQuerySql.append('?');
					if(i < parentValueListSize-1)
						recursiveQuerySql.append(',');
				}
				recursiveQuerySql.append(')');
			}
		}
		recursiveQuerySql.append(')');
	}
}
