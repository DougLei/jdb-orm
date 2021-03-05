package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql;

import java.util.ArrayList;
import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sql.statement.entity.InputSqlParameter;

/**
 * 
 * @author DougLei
 */
public class ExecutableDeleteSql extends ExecutableTableSql{
	
	public ExecutableDeleteSql(TableMetadata tableMetadata, Map<String, Object> objectMap) {
		super(tableMetadata, objectMap);
		installSQL();
	}

	/**
	 * 组装DeleteSQL
	 */
	private void installSQL() {
		StringBuilder deleteSql = new StringBuilder(300);
		deleteSql.append("delete ").append(tableMetadata.getName()).append(" where ");
		
		if(tableMetadata.getPrimaryKeyConstraint() == null) {
			setWhereSqlStatementWhenUnExistsPrimaryKeys(deleteSql);
		}else {
			setWhereSqlStatementWhenExistsPrimaryKeys(deleteSql);
		}
		super.sql = deleteSql.toString();
	}
	
	// 当存在primaryKey时, 拼装where sql语句
	private void setWhereSqlStatementWhenExistsPrimaryKeys(StringBuilder deleteSql) {
		super.parameters = new ArrayList<Object>(tableMetadata.getPrimaryKeyConstraint().getColumnNameList().size());
		
		ColumnMetadata column = null;
		for (String columnName : tableMetadata.getPrimaryKeyConstraint().getColumnNameList()) {
			deleteSql.append(columnName).append("=?");
			
			column = tableMetadata.getColumnMap4Name().get(columnName);
			parameters.add(new InputSqlParameter(objectMap.get(column.getCode()), column.getDBDataType()));
			
			deleteSql.append(" and ");
		}
		deleteSql.setLength(deleteSql.length()-5);
	}
	
	// 当不存在primaryKey时, 拼装where sql语句; 将所有列值组装成条件
	private void setWhereSqlStatementWhenUnExistsPrimaryKeys(StringBuilder deleteSql) {
		super.parameters = new ArrayList<Object>(tableMetadata.getColumns().size());
		
		Object value = null;
		for (ColumnMetadata column : tableMetadata.getColumns()) {
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
