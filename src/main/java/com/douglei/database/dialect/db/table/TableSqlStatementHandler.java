package com.douglei.database.dialect.db.table;

import java.util.List;

import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.column.extend.ColumnConstraint;
import com.douglei.database.metadata.table.column.extend.ColumnIndex;
import com.douglei.database.metadata.table.column.extend.ColumnProperty;

/**
 * 表sql语句处理器
 * @author DougLei
 */
public abstract class TableSqlStatementHandler {
	
	/**
	 * <pre>
	 * 	获取查询表是否存在的sql语句
	 * 	返回的sql语句需要使用PreparedStatement查询, 下标为1的传入表名, 且表名必须全大写
	 * </pre>
	 * @return 
	 */
	public abstract String tableExistsQueryPreparedSqlStatement();
	
	/**
	 * 获取create table的sql语句
	 * @param tableName
	 * @param columns
	 * @return
	 */
	public String tableCreateSqlStatement(String tableName, List<ColumnMetadata> columns) {
		StringBuilder sql = new StringBuilder(1000);
		sql.append("create table ").append(tableName);
		sql.append("(");
		
		int lastIndex = columns.size()-1;
		ColumnMetadata column = null;
		ColumnProperty cp = null;
		for (int i=0;i<columns.size();i++) {
			column = columns.get(i);
			cp = column.getColumnProperty();
			
			sql.append(cp.getName()).append(" ");
			sql.append(column.getDataType().defaultDBDataType().getType4SqlStatement(cp.getLength(), cp.getPrecision())).append(" ");
			if(!cp.isNullabled()) {
				sql.append("not null");
			}
			if(i<lastIndex) {
				sql.append(",");
			}
		}
		sql.append(")");
		return sql.toString();
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
	 * 获取create column的sql语句
	 * @param tableName
	 * @param columns
	 * @return
	 */
	public String[] columnCreateSqlStatement(String tableName, List<ColumnMetadata> columns) {
		if(columns != null && columns.size() > 0) {
			int size = columns.size();
			String[] createSqlStatement = new String[size];
			
			ColumnMetadata column = null;
			ColumnProperty cp = null;
			StringBuilder tmpSql = new StringBuilder(100);
			for(int i=0;i<size;i++) {
				column = columns.get(i);
				cp = column.getColumnProperty();
				
				tmpSql.append("alter table ").append(tableName).append(" add column ").append(column.getColumnProperty().getName()).append(" ");
				tmpSql.append(column.getDataType().defaultDBDataType().getType4SqlStatement(cp.getLength(), cp.getPrecision())).append(" ");
				if(!cp.isNullabled()) {
					tmpSql.append("not null");
				}
				
				createSqlStatement[i] = tmpSql.toString();
				tmpSql.setLength(0);
			}
			return createSqlStatement;
		}
		return null;
	}
	
	/**
	 * 获取drop column的sql语句
	 * @param tableName
	 * @param columns
	 * @return
	 */
	public String[] columnDropSqlStatement(String tableName, List<ColumnMetadata> columns) {
		if(columns != null && columns.size() > 0) {
			int size = columns.size();
			String[] dropSqlStatement = new String[size];
			
			StringBuilder tmpSql = new StringBuilder(100);
			for(int i=0;i<size;i++) {
				tmpSql.append("alter table ").append(tableName).append(" drop column ").append(columns.get(i).getColumnProperty().getName());
				dropSqlStatement[i] = tmpSql.toString();
				tmpSql.setLength(0);
			}
			return dropSqlStatement;
		}
		return null;
	}
	
	/**
	 * 获取create constraint的sql语句
	 * @param tableName
	 * @param constraints
	 * @return
	 */
	public String[] constraintCreateSqlStatement(String tableName, List<ColumnConstraint> constraints) {
		if(constraints != null && constraints.size() > 0) {
			int size = constraints.size();
			String[] createSqlStatement = new String[size];
			
			StringBuilder tmpSql = new StringBuilder(100);
			for(int i=0;i<size;i++) {
				// TODO
				createSqlStatement[i] = tmpSql.toString();
				tmpSql.setLength(0);
			}
			return createSqlStatement;
		}
		return null;
	}
	
	/**
	 * 获取drop constraint的sql语句
	 * @param tableName
	 * @param constraints
	 * @return
	 */
	public String[] constraintDropSqlStatement(String tableName, List<ColumnConstraint> constraints) {
		if(constraints != null && constraints.size() > 0) {
			int size = constraints.size();
			String[] dropSqlStatement = new String[size];
			
			StringBuilder tmpSql = new StringBuilder(100);
			for(int i=0;i<size;i++) {
				tmpSql.append("alter table ").append(tableName).append(" drop constraint ").append(constraints.get(i).getName());
				dropSqlStatement[i] = tmpSql.toString();
				tmpSql.setLength(0);
			}
			return dropSqlStatement;
		}
		return null;
	}
	
	/**
	 * 获取create index的sql语句
	 * @param tableName
	 * @param indexes
	 * @return
	 */
	public String[] indexCreateSqlStatement(String tableName, List<ColumnIndex> indexes) {
		if(indexes != null && indexes.size() > 0) {
			int size = indexes.size();
			String[] createSqlStatement = new String[size];
			
			StringBuilder tmpSql = new StringBuilder(100);
			for(int i=0;i<size;i++) {
				// TODO
				createSqlStatement[i] = tmpSql.toString();
				tmpSql.setLength(0);
			}
			return createSqlStatement;
		}
		return null;
	}
	
	/**
	 * 获取drop index的sql语句
	 * @param tableName
	 * @param indexes
	 * @return
	 */
	public String[] indexDropSqlStatement(String tableName, List<ColumnIndex> indexes) {
		if(indexes != null && indexes.size() > 0) {
			int size = indexes.size();
			String[] dropSqlStatement = new String[size];
			
			StringBuilder tmpSql = new StringBuilder(100);
			for(int i=0;i<size;i++) {
				// TODO
				dropSqlStatement[i] = tmpSql.toString();
				tmpSql.setLength(0);
			}
			return dropSqlStatement;
		}
		return null;
	}
}
