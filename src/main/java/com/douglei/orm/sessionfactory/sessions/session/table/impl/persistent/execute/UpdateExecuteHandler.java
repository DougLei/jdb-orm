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
public class UpdateExecuteHandler extends TableExecuteHandler{
	private boolean updateNullValue;
	
	public UpdateExecuteHandler(TableMetadata tableMetadata, Map<String, Object> objectMap, boolean updateNullValue) {
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
		
		// 处理update set
		Object value = null;
		ColumnMetadata column = null;
		for (String code : objectMap.keySet()) {
			if(primaryKeyColumns.containsKey(code))
				continue;
			
			value = objectMap.get(code);
			if(updateNullValue || value != null) {
				column = tableMetadata.getColumns_().get(code);
				updateSql.append(column.getName()).append("=?,");
				parameters.add(new InputSqlParameter(value, column.getDBDataType()));
			}
		}
		updateSql.setLength(updateSql.length()-1);
		
		// set对应的where sql语句
		updateSql.append(" where ");
		for (String pkCode : primaryKeyColumns.keySet()) {
			value = objectMap.get(pkCode);
			column = primaryKeyColumns.get(pkCode);
			
			updateSql.append(column.getName()).append("=?");
			parameters.add(new InputSqlParameter(value, column.getDBDataType()));
			
			updateSql.append(" and ");
		}
		updateSql.setLength(updateSql.length()-5);
		
		this.sql = updateSql.toString();
	}
}
