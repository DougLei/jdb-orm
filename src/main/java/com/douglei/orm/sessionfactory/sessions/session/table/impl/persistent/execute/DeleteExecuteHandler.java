package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execute;

import java.util.ArrayList;
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
	
	// 当存在primaryKey时, set对应的where sql语句
	private void setWhereSqlStatementWhenExistsPrimaryKeys(StringBuilder deleteSql) {
		Map<String, ColumnMetadata> primaryKeyColumns = tableMetadata.getPrimaryKeyColumns_();
		parameters = new ArrayList<Object>(primaryKeyColumns.size());
		
		ColumnMetadata column = null;
		for (String pkCode : primaryKeyColumns.keySet()) {
			column = primaryKeyColumns.get(pkCode);
			
			deleteSql.append(column.getName()).append("=?");
			parameters.add(new InputSqlParameter(objectMap.get(pkCode), column.getDBDataType()));
			
			deleteSql.append(" and ");
		}
		deleteSql.setLength(deleteSql.length()-5);
	}
	
	// 当不存在primaryKey时, set对应的where sql语句
	private void setWhereSqlStatementWhenUnExistsPrimaryKeys(StringBuilder deleteSql) {
		parameters = new ArrayList<Object>(objectMap.size());
		
		Object value = null;
		ColumnMetadata column = null;
		for (String code : objectMap.keySet()) {
			column = tableMetadata.getColumns_().get(code);
			value = objectMap.get(code);
			
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
