package com.douglei.sessions.session.table.impl.persistent.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.database.sql.statement.impl.Parameter;

/**
 * 
 * @author DougLei
 */
public class DeleteExecutionHolder extends TableExecutionHolder{
	
	public DeleteExecutionHolder(TableMetadata tableMetadata, Map<String, Object> propertyMap) {
		super(tableMetadata, propertyMap);
	}

	@Override
	protected void initialInstance() {
		StringBuilder deleteSql = new StringBuilder();
		deleteSql.append("delete ").append(tableMetadata.getName()).append(" where ");
		
		List<ColumnMetadata> primaryKeyColumns = tableMetadata.getPrimaryKeyColumns();
		int size = primaryKeyColumns.size();
		
		parameters = new ArrayList<Parameter>(size);// 使用TableExecutionHolder.parameters属性
		
		int index = 1;
		for (ColumnMetadata pkColumn : primaryKeyColumns) {
			deleteSql.append(pkColumn.getName()).append("=?");
			parameters.add(new Parameter(propertyMap.get(pkColumn.getCode()), pkColumn.getDataType()));
			
			if(index < size) {
				deleteSql.append(" and ");
			}
			index++;
		}
		
		this.sql = deleteSql.toString();
	}
}
