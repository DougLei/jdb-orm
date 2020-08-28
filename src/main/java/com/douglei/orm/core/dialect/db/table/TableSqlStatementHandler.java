package com.douglei.orm.core.dialect.db.table;

import java.util.Collection;

import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.Constraint;
import com.douglei.orm.core.metadata.table.TableMetadata;

/**
 * 表sql语句处理器
 * @author DougLei
 */
public abstract class TableSqlStatementHandler {
	
	// --------------------------------------------------------------------------------------------
	// table
	// --------------------------------------------------------------------------------------------
	/**
	 * <pre>
	 * 	获取查询表是否存在的sql语句
	 * 	返回的sql语句需要使用PreparedStatement查询, 下标为1的传入表名, 且表名必须全大写
	 * </pre>
	 * @return 
	 */
	public abstract String queryTableExistsSql();
	
	/**
	 * 获取create table的sql语句
	 * @param table
	 * @return
	 */
	public String tableCreateSqlStatement(TableMetadata table) {
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
	public String tableDropSqlStatement(String tableName) {
		return "drop table " + tableName;
	}
	
	/**
	 * 获取rename 表名的sql语句
	 * @param originTableName
	 * @param targetTableName
	 * @return
	 */
	public String tableRenameSqlStatement(String originTableName, String targetTableName) {
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
	public String columnCreateSqlStatement(String tableName, ColumnMetadata column) {
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
	public String columnDropSqlStatement(String tableName, String columnName) {
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
	public String columnRenameSqlStatement(String tableName, String originColumnName, String targetColumnName) {
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
	public String columnModifySqlStatement(String tableName, ColumnMetadata column) {
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
	public String constraintCreateSqlStatement(Constraint constraint) {
		switch(constraint.getConstraintType()) {
			case PRIMARY_KEY:
			case UNIQUE:
				return pk_uq_constraintCreateSqlStatement(constraint);
			case DEFAULT_VALUE:
				return defaultValueConstraintCreateSqlStatement(constraint);
			case CHECK:
				return checkConstraintCreateSqlStatement(constraint);
			case FOREIGN_KEY:
				return foreignKeyConstraintCreateSqlStatement(constraint);
		}
		throw new IllegalArgumentException("没有处理:" + constraint.getConstraintType());
	}
	/**获取创建主键约束、唯一约束的sql语句*/
	protected String pk_uq_constraintCreateSqlStatement(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" add constraint ").append(constraint.getName()).append(" ");
		tmpSql.append(constraint.getConstraintType().getSqlStatement()).append(" (").append(constraint.getConstraintColumnNames()).append(")");
		return tmpSql.toString();
	}
	/**获取创建默认值约束的sql语句*/
	protected abstract String defaultValueConstraintCreateSqlStatement(Constraint constraint);
	/**获取创建检查约束的sql语句*/
	protected String checkConstraintCreateSqlStatement(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" add constraint ").append(constraint.getName()).append(" ");
		tmpSql.append(constraint.getConstraintType().getSqlStatement()).append(" (").append(constraint.getCheck()).append(")");
		return tmpSql.toString();
	}
	/**获取创建外键约束的sql语句*/
	protected String foreignKeyConstraintCreateSqlStatement(Constraint constraint) {
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
	public String constraintDropSqlStatement(Constraint constraint) {
		switch(constraint.getConstraintType()) {
			case PRIMARY_KEY:
				return primaryKeyConstraintDropSqlStatement(constraint);
			case UNIQUE:
				return uniqueConstraintDropSqlStatement(constraint);
			case DEFAULT_VALUE:
				return defaultValueConstraintDropSqlStatement(constraint);
			case CHECK:
				return checkConstraintDropSqlStatement(constraint);
			case FOREIGN_KEY:
				return foreignKeyConstraintDropSqlStatement(constraint);
		}
		throw new IllegalArgumentException("没有处理:" + constraint.getConstraintType());
	}
	/**通用的 获取drop constraint的sql语句*/
	private String commonConstraintDropSqlStatement(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" drop constraint ").append(constraint.getName());
		return tmpSql.toString();
	}
	/**获取删除主键约束的sql语句*/
	protected String primaryKeyConstraintDropSqlStatement(Constraint constraint) {
		return commonConstraintDropSqlStatement(constraint);
	}
	/**获取删除唯一约束的sql语句*/
	protected String uniqueConstraintDropSqlStatement(Constraint constraint) {
		return commonConstraintDropSqlStatement(constraint);
	}
	/**获取删除默认值约束的sql语句*/
	protected String defaultValueConstraintDropSqlStatement(Constraint constraint) {
		return commonConstraintDropSqlStatement(constraint);
	}
	/**获取删除检查约束的sql语句*/
	protected String checkConstraintDropSqlStatement(Constraint constraint) {
		return commonConstraintDropSqlStatement(constraint);
	}
	/**获取删除外键约束的sql语句*/
	protected String foreignKeyConstraintDropSqlStatement(Constraint constraint) {
		return commonConstraintDropSqlStatement(constraint);
	}
}
