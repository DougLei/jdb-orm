package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sql.statement.entity.InputSqlParameter;

/**
 * 
 * @author DougLei
 */
public class ExecutableUpdateSql extends ExecutableTableSql{
	private boolean updateNullValue;
	
	public ExecutableUpdateSql(TableMetadata tableMetadata, Map<String, Object> objectMap, boolean updateNullValue) {
		setBaseInfo(tableMetadata, objectMap);
		this.updateNullValue = updateNullValue;
		initial();
	}

	@Override
	protected void initial() {
		StringBuilder updateSql = new StringBuilder(300);
		updateSql.append("update ").append(tableMetadata.getName()).append(" set ");
		
		parameters = new ArrayList<Object>(objectMap.size());
		Map<String, ColumnMetadata> primaryKeyColumns = tableMetadata.getPrimaryKeyColumns_();
		
		// 拼装update set sql语句
		ColumnMetadata column = null;
		for (Entry<String, Object> entry : objectMap.entrySet()) {
			if(primaryKeyColumns.containsKey(entry.getKey()))
				continue;
			
			if(updateNullValue || entry.getValue() != null) {
				column = tableMetadata.getColumns_().get(entry.getKey());
				updateSql.append(column.getName()).append("=?,");
				parameters.add(new InputSqlParameter(entry.getValue(), column.getDBDataType()));
			}
		}
		updateSql.setLength(updateSql.length()-1);
		
		// 拼装where sql语句
		updateSql.append(" where ");
		Object value = null;
		for (ColumnMetadata pkColumn : primaryKeyColumns.values()) {
			value = objectMap.get(pkColumn.getCode());
			
			updateSql.append(pkColumn.getName()).append("=?");
			parameters.add(new InputSqlParameter(value, pkColumn.getDBDataType()));
			
			updateSql.append(" and ");
		}
		updateSql.setLength(updateSql.length()-5);
		
		this.sql = updateSql.toString();
	}
}
