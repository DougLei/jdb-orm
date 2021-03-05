package com.douglei.orm.dialect.sqlhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.OrmException;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.ConstraintMetadata;
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
	public abstract String queryProcedureExists();
	
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
		
		for (ColumnMetadata column : table.getColumns()) {
			sql.append(column.getName()).append(" ");
			sql.append(column.getDBDataType().getSqlStatement(column.getLength(), column.getPrecision())).append(" ");
			if(!column.isNullable()) 
				sql.append("not null");
			sql.append(',');
		}
		sql.setLength(sql.length()-1);
		sql.append(")");
		return sql.toString();
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
	 * 获取修改表名的sql语句
	 * @param oldTableName
	 * @param newTableName
	 * @return
	 */
	public String renameTable(String oldTableName, String newTableName) {
		StringBuilder tmpSql = new StringBuilder(80);
		tmpSql.append("alter table ").append(oldTableName).append(" rename to ").append(newTableName);
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// column
	// --------------------------------------------------------------------------------------------
	/**
	 * 获取创建列的sql语句
	 * @param tableName
	 * @param column
	 * @return
	 */
	public String createColumn(String tableName, ColumnMetadata column) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(tableName).append(" add ").append(column.getName()).append(" ");
		tmpSql.append(column.getDBDataType().getSqlStatement(column.getLength(), column.getPrecision())).append(" ");
		if(!column.isNullable()) 
			tmpSql.append("not null");
		return tmpSql.toString();
	}
	
	/**
	 * 获取删除列的sql语句
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
	 * 获取修改列名的sql语句
	 * @param tableName
	 * @param oldColumnName
	 * @param newColumnName
	 * @return
	 */
	public String renameColumn(String tableName, String oldColumnName, String newColumnName) {
		StringBuilder tmpSql = new StringBuilder(80);
		tmpSql.append("alter table ").append(tableName).append(" rename column ").append(oldColumnName).append(" to ").append(newColumnName);
		return tmpSql.toString();
	}
	
	/**
	 * 获取修改列的sql语句
	 * @param tableName
	 * @param column
	 * @return
	 */
	public String updateColumn(String tableName, ColumnMetadata column) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(tableName).append(" modify ").append(column.getName()).append(" ");
		tmpSql.append(column.getDBDataType().getSqlStatement(column.getLength(), column.getPrecision())).append(" ");
		if(!column.isNullable()) 
			tmpSql.append("not null");
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	/**
	 * 获取创建约束的sql语句
	 * @param tableName
	 * @param constraint
	 * @return
	 */
	public String createConstraint(String tableName, ConstraintMetadata constraint) {
		switch(constraint.getType()) {
			case AUTO_INCREMENT_PRIMARY_KEY:
			case PRIMARY_KEY:
			case UNIQUE:
				return createPK_UQ(tableName, constraint);
			case DEFAULT_VALUE:
				return createDefaultValue(tableName, constraint);
			case CHECK:
				return createCheck(tableName, constraint);
			case FOREIGN_KEY:
				return createForeignKey(tableName, constraint);
		}
		throw new OrmException("(create)不支持的约束类型: " + constraint.getType());
	}
	/**获取创建主键约束、唯一约束的sql语句*/
	protected String createPK_UQ(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(120);
		sql.append("alter table ").append(tableName).append(" add constraint ").append(constraint.getName()).append(" ");
		sql.append(constraint.getType().getSqlKey()).append(" (");
		constraint.getColumnNameList().forEach(columnName -> sql.append(columnName).append(','));
		sql.setLength(sql.length()-1);
		sql.append(')');
		return sql.toString();
	}
	/**获取创建默认值约束的sql语句*/
	protected abstract String createDefaultValue(String tableName, ConstraintMetadata constraint);
	/**获取创建检查约束的sql语句*/
	protected String createCheck(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(120);
		sql.append("alter table ").append(tableName).append(" add constraint ").append(constraint.getName()).append(" ");
		sql.append(constraint.getType().getSqlKey()).append(" (").append(constraint.getCheck()).append(")");
		return sql.toString();
	}
	/**获取创建外键约束的sql语句*/
	protected String createForeignKey(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(120);
		sql.append("alter table ").append(tableName).append(" add constraint ").append(constraint.getName()).append(" ");
		sql.append(constraint.getType().getSqlKey()).append(" (").append(constraint.getColumnNameList().get(0)).append(") ");
		sql.append("references ").append(constraint.getTable()).append("(").append(constraint.getColumn()).append(")");
		return sql.toString();
	}

	/**
	 * 获取删除约束的sql语句
	 * @param tableName
	 * @param constraint
	 * @return
	 */
	public String dropConstraint(String tableName, ConstraintMetadata constraint) {
		switch(constraint.getType()) {
			case AUTO_INCREMENT_PRIMARY_KEY:
			case PRIMARY_KEY:
				return dropPrimaryKey(tableName, constraint);
			case UNIQUE:
				return dropUnique(tableName, constraint);
			case DEFAULT_VALUE:
				return dropDefaultValue(tableName, constraint);
			case CHECK:
				return dropCheck(tableName, constraint);
			case FOREIGN_KEY:
				return dropForeignKey(tableName, constraint);
		}
		throw new OrmException("(drop)不支持的约束类型: " + constraint.getType());
	}
	/**获取删除主键约束的sql语句*/
	protected String dropPrimaryKey(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("alter table ").append(tableName).append(" drop constraint ").append(constraint.getName());
		return sql.toString();
	}
	/**获取删除唯一约束的sql语句*/
	protected String dropUnique(String tableName, ConstraintMetadata constraint) {
		return dropPrimaryKey(tableName, constraint);
	}
	/**获取删除默认值约束的sql语句*/
	protected String dropDefaultValue(String tableName, ConstraintMetadata constraint) {
		return dropPrimaryKey(tableName, constraint);
	}
	/**获取删除检查约束的sql语句*/
	protected String dropCheck(String tableName, ConstraintMetadata constraint) {
		return dropPrimaryKey(tableName, constraint);
	}
	/**获取删除外键约束的sql语句*/
	protected String dropForeignKey(String tableName, ConstraintMetadata constraint) {
		return dropPrimaryKey(tableName, constraint);
	}
	
	// --------------------------------------------------------------------------------------------
	// 自增主键
	// --------------------------------------------------------------------------------------------
	/**
	 * 获取创建自增主键的sql语句; 返回null表示不需要创建自增主键
	 * @param table
	 * @return
	 */
	public String createAutoincrementPrimaryKey(TableMetadata table) {
		return null;
	}
	
	/**
	 * 获取删除自增主键的sql语句; 返回null表示不需要删除自增主键
	 * @param table
	 * @return
	 */
	public String dropAutoincrementPrimaryKey(TableMetadata table) {
		return null;
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
	public final String getRecursiveSql(RecursiveSqlStatement statement) {
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
	public final void appendConditionSql2RecursiveSql(StringBuilder recursiveQuerySql, RecursiveSqlStatement statement) { 
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
