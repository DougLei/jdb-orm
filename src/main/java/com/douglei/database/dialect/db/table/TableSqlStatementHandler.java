package com.douglei.database.dialect.db.table;

import java.util.Collection;
import java.util.List;

import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.database.metadata.table.column.extend.ColumnConstraint;
import com.douglei.database.metadata.table.column.extend.ColumnIndex;
import com.douglei.database.metadata.table.column.extend.ConstraintType;

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
	 * @param table
	 * @return
	 */
	public String tableCreateSqlStatement(TableMetadata table) {
		StringBuilder sql = new StringBuilder(1000);
		sql.append("create table ").append(table.getName());
		sql.append("(");
		
		Collection<ColumnMetadata> columns = table.getColumnMetadatas();
		int index=0, lastIndex = columns.size()-1;
		for (ColumnMetadata column : columns) {
			sql.append(column.getName()).append(" ");
			sql.append(column.getDataType().defaultDBDataType().getDBType4SqlStatement(column.getLength(), column.getPrecision())).append(" ");
			if(!column.isNullabled()) {
				sql.append("not null");
			}
			if(index<lastIndex) {
				sql.append(",");
			}
			index++;
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
	public String[] columnCreateSqlStatement(String tableName, Collection<ColumnMetadata> columns) {
		if(columns != null && columns.size() > 0) {
			int index =0, size = columns.size();
			String[] createSqlStatement = new String[size];
			
			for (ColumnMetadata column : columns) {
				createSqlStatement[index] = columnCreateSqlStatement(tableName, column);
				index++;
			}
			return createSqlStatement;
		}
		return null;
	}
	
	/**
	 * 获取create column的sql语句
	 * @param tableName
	 * @param column
	 * @return
	 */
	public String columnCreateSqlStatement(String tableName, ColumnMetadata column) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(tableName).append(" add ").append(column.getName()).append(" ");
		tmpSql.append(column.getDataType().defaultDBDataType().getDBType4SqlStatement(column.getLength(), column.getPrecision())).append(" ");
		if(!column.isNullabled()) {
			tmpSql.append("not null");
		}
		return tmpSql.toString();
	}
	
	/**
	 * 获取drop column的sql语句
	 * @param tableName
	 * @param columns
	 * @return
	 */
	public String[] columnDropSqlStatement(String tableName, Collection<ColumnMetadata> columns) {
		if(columns != null && columns.size() > 0) {
			int index=0, size = columns.size();
			String[] dropSqlStatement = new String[size];
			
			
			for (ColumnMetadata column : columns) {
				dropSqlStatement[index] = columnDropSqlStatement(tableName, column.getName());
				index++;
			}
			return dropSqlStatement;
		}
		return null;
	}
	
	/**
	 * 获取drop column的sql语句
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public String columnDropSqlStatement(String tableName, String columnName) {
		StringBuilder tmpSql = new StringBuilder(50);
		tmpSql.append("alter table ").append(tableName).append(" drop column ").append(columnName);
		return tmpSql.toString();
	}
	
	/**
	 * 获取create constraint的sql语句
	 * @param constraints
	 * @return
	 */
	public String[] constraintCreateSqlStatement(List<ColumnConstraint> constraints) {
		if(constraints != null && constraints.size() > 0) {
			int size = constraints.size();
			String[] createSqlStatement = new String[size];
			
			StringBuilder tmpSql = new StringBuilder(100);
			ColumnConstraint cc = null;
			for(int i=0;i<size;i++) {
				cc = constraints.get(i);
				if(cc.getConstraintType() == ConstraintType.DEFAULT_VALUE) {
					setDefaultValueConstraintCreateSqlStatement2StringBuilderParameter(cc, tmpSql);
				}else {
					tmpSql.append("alter table ").append(cc.getTableName()).append(" add constraint ").append(cc.getName()).append(" ");
					tmpSql.append(cc.getConstraintType().getSqlStatement()).append("(").append(cc.getColumnName()).append(")");
				}
				createSqlStatement[i] = tmpSql.toString();
				tmpSql.setLength(0);
			}
			return createSqlStatement;
		}
		return null;
	}
	/**
	 * <pre>
	 * 	设置create 默认值constraint的sql语句到StringBuilder参数中(即将结果append到参数sql中)
	 * 	各个数据库不一致, 需要各自实现
	 * </pre>
	 * @param constraint
	 * @param sql
	 */
	protected abstract void setDefaultValueConstraintCreateSqlStatement2StringBuilderParameter(ColumnConstraint constraint, StringBuilder sql);

	/**
	 * 获取drop constraint的sql语句
	 * @param constraints
	 * @return
	 */
	public String[] constraintDropSqlStatement(List<ColumnConstraint> constraints) {
		if(constraints != null && constraints.size() > 0) {
			int size = constraints.size();
			String[] dropSqlStatement = new String[size];
			
			StringBuilder tmpSql = new StringBuilder(100);
			ColumnConstraint cc = null;
			for(int i=0;i<size;i++) {
				cc = constraints.get(i);
				
				tmpSql.append("alter table ").append(cc.getTableName()).append(" drop constraint ").append(cc.getName());
				dropSqlStatement[i] = tmpSql.toString();
				tmpSql.setLength(0);
			}
			return dropSqlStatement;
		}
		return null;
	}
	
	/**
	 * 获取create index的sql语句
	 * @param indexes
	 * @return
	 */
	public String[] indexCreateSqlStatement(List<ColumnIndex> indexes) {
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
	 * @param indexes
	 * @return
	 */
	public String[] indexDropSqlStatement(List<ColumnIndex> indexes) {
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
