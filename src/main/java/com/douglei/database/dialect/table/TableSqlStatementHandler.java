package com.douglei.database.dialect.table;

import java.util.List;

import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.column.extend.ColumnConstraint;
import com.douglei.database.metadata.table.column.extend.ColumnIndex;

/**
 * 表sql语句处理器
 * @author DougLei
 */
public abstract class TableSqlStatementHandler {
	
	/**
	 * 表是否存在
	 * @param tableName
	 * @return 返回1表示存在, 0表示不存在
	 */
	protected abstract short tableExists(String tableName);
	
	/**
	 * 获取create table的sql语句
	 * @param tableName
	 * @param columns
	 * @return
	 */
	protected abstract String tableCreateSqlStatement(String tableName, List<ColumnMetadata> columns);
	
	/**
	 * 获取drop table的sql语句
	 * @param tableName
	 * @return
	 */
	protected String tableDropSqlStatement(String tableName) {
		return "drop table " + tableName;
	}
	
	/**
	 * 获取create column的sql语句
	 * @param tableName
	 * @param columns
	 * @return
	 */
	protected abstract String[] columnCreateSqlStatement(String tableName, List<ColumnMetadata> columns);
	
	/**
	 * 获取drop column的sql语句
	 * @param tableName
	 * @param columns
	 * @return
	 */
	protected abstract String[] columnDropSqlStatement(String tableName, List<ColumnMetadata> columns);
	
	/**
	 * 获取rename column的sql语句
	 * @param tableName
	 * @param columns
	 * @return
	 */
	protected abstract String[] columnRenameSqlStatement(String tableName, List<ColumnMetadata> columns);
	
	/**
	 * 获取create constraint的sql语句
	 * @param tableName
	 * @param constraints
	 * @return
	 */
	protected abstract String[] constraintCreateSqlStatement(String tableName, List<ColumnConstraint> constraints);
	
	/**
	 * 获取drop constraint的sql语句
	 * @param tableName
	 * @param constraints
	 * @return
	 */
	protected String[] constraintDropSqlStatement(String tableName, List<ColumnConstraint> constraints) {
		if(constraints != null && constraints.size() > 0) {
			int size = constraints.size();
			String[] dropSqlStatement = new String[size];
			
			StringBuilder tmpSql = new StringBuilder(70);
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
	protected abstract String[] indexCreateSqlStatement(String tableName, List<ColumnIndex> indexes);
	
	/**
	 * 获取drop index的sql语句
	 * @param tableName
	 * @param indexes
	 * @return
	 */
	protected abstract String[] indexDropSqlStatement(String tableName, List<ColumnIndex> indexes);
}
