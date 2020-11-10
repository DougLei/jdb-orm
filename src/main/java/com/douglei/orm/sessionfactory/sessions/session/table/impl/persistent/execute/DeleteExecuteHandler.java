package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sql.statement.entity.InputSqlParameter;

/**
 * 
 * @author DougLei
 */
public class DeleteExecuteHandler extends TableExecuteHandler{
	
	public DeleteExecuteHandler(TableMetadata tableMetadata, Map<String, Object> objectMap) {
		super(tableMetadata, objectMap);
	}

	@Override
	protected void initial() {
		StringBuilder deleteSql = new StringBuilder(300);
		deleteSql.append("delete ").append(tableMetadata.getName()).append(" where ");
		
		if(tableMetadata.getPrimaryKeyColumns_() == null) {
			setWhereSqlStatementWhenUnExistsPrimaryKeys(deleteSql);
		}else {
			setWhereSqlStatementWhenExistsPrimaryKeys(deleteSql);
		}
		this.sql = deleteSql.toString();
	}
	
	// 当存在primaryKey时, 拼装where sql语句
	private void setWhereSqlStatementWhenExistsPrimaryKeys(StringBuilder deleteSql) {
		Collection<ColumnMetadata> primaryKeyColumns = tableMetadata.getPrimaryKeyColumns_().values();
		parameters = new ArrayList<Object>(primaryKeyColumns.size());
		
		for (ColumnMetadata pkColumn : primaryKeyColumns) {
			deleteSql.append(pkColumn.getName()).append("=?");
			parameters.add(new InputSqlParameter(objectMap.get(pkColumn.getCode()), pkColumn.getDBDataType()));
			
			deleteSql.append(" and ");
		}
		deleteSql.setLength(deleteSql.length()-5);
	}
	
	// 当不存在primaryKey时, 拼装where sql语句; 将所有列值组装成条件
	private void setWhereSqlStatementWhenUnExistsPrimaryKeys(StringBuilder deleteSql) {
		Collection<ColumnMetadata> columns = tableMetadata.getColumns_().values();
		parameters = new ArrayList<Object>(columns.size());
		
		Object value = null;
		for (ColumnMetadata column : columns) {
			value = objectMap.get(column.getCode());
			
			if(value == null) {
				deleteSql.append(column.getName()).append(" is null");
			}else {
				deleteSql.append(column.getName()).append("=?");
				parameters.add(new InputSqlParameter(value, column.getDBDataType()));
			}
			deleteSql.append(" and ");
		}
		deleteSql.setLength(deleteSql.length()-5);
	}
}
