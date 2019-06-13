package com.douglei.orm.sessions.session.table.impl.persistent.execution;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.sql.statement.entity.InputSqlParameter;

/**
 * 
 * @author DougLei
 */
public class DeleteExecutionHolder extends TableExecutionHolder{
	
	public DeleteExecutionHolder(TableMetadata tableMetadata, Map<String, Object> propertyMap) {
		super(tableMetadata, propertyMap);
	}

	@Override
	protected void initializeInstance() {
		StringBuilder deleteSql = new StringBuilder();
		deleteSql.append("delete ").append(tableMetadata.getName()).append(" where ");
		
		// 处理where值
		Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumnMetadataCodes();
		int size = primaryKeyColumnMetadataCodes.size();
		
		parameters = new ArrayList<Object>(size);// 使用TableExecutionHolder.parameters属性
		
		ColumnMetadata primaryKeyColumnMetadata = null;
		int index = 1;
		for (String pkCode : primaryKeyColumnMetadataCodes) {
			primaryKeyColumnMetadata = tableMetadata.getPrimaryKeyColumnMetadata(pkCode);
			
			deleteSql.append(primaryKeyColumnMetadata.getName()).append("=?");
			parameters.add(new InputSqlParameter(propertyMap.get(pkCode), primaryKeyColumnMetadata.getDataTypeHandler()));
			
			if(index < size) {
				deleteSql.append(" and ");
			}
			index++;
		}
		
		this.sql = deleteSql.toString();
	}
}