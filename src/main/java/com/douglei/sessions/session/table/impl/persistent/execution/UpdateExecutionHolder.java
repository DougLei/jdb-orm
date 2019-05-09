package com.douglei.sessions.session.table.impl.persistent.execution;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.database.sql.statement.impl.Parameter;

/**
 * 
 * @author DougLei
 */
public class UpdateExecutionHolder extends TableExecutionHolder{
	
	public UpdateExecutionHolder(TableMetadata tableMetadata, Map<String, Object> propertyMap) {
		super(tableMetadata, propertyMap);
	}

	@Override
	protected void initialInstance() {
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("update ").append(tableMetadata.getName()).append(" set ");
		
		int size = propertyMap.size();
		parameters = new ArrayList<Object>(size);// 使用TableExecutionHolder.parameters属性
		
		// 处理update set值
		int index = 1;
		Set<String> codes = propertyMap.keySet();
		Object value = null;
		ColumnMetadata columnMetadata = null;
		for (String code : codes) {
			if(!tableMetadata.isPrimaryKeyColumn(code)) {
				value = propertyMap.get(code);
				if(value != null) {// 只修改不为空的值
					columnMetadata = tableMetadata.getColumnMetadata(code);
					
					updateSql.append(columnMetadata.getName()).append("=?");
					parameters.add(new Parameter(value, columnMetadata.getDataType()));
					
					if(index < size) {
						updateSql.append(",");
					}
				}
			}
			index++;
		}
		updateSql.append(" where ");
		
		// 处理where值
		Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumnMetadataCodes();
		size = primaryKeyColumnMetadataCodes.size();
		index = 1;
		for (String pkCode : primaryKeyColumnMetadataCodes) {
			columnMetadata = tableMetadata.getPrimaryKeyColumnMetadata(pkCode);
			
			updateSql.append(columnMetadata.getName()).append("=?");
			parameters.add(new Parameter(propertyMap.get(pkCode), columnMetadata.getDataType()));
			
			if(index < size) {
				updateSql.append(" and ");
			}
			index++;
		}
		
		this.sql = updateSql.toString();
	}
}
