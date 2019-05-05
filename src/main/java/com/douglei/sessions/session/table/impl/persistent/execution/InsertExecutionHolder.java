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
public class InsertExecutionHolder extends TableExecutionHolder{
	
	public InsertExecutionHolder(TableMetadata tableMetadata, Map<String, Object> propertyMap) {
		super(tableMetadata, propertyMap);
	}
	
	@Override
	protected void initialInstance() {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("insert into ").append(tableMetadata.getName()).append("(");
		
		StringBuilder values = new StringBuilder();
		values.append(" values(");
		
		int size = propertyMap.size();
		parameters = new ArrayList<Parameter>(size);// 使用TableExecutionHolder.parameters属性
		
		int index = 1;
		Object value = null;
		ColumnMetadata columnMetadata = null;
		Set<String> codes = propertyMap.keySet();
		for (String code : codes) {
			value = propertyMap.get(code);
			if(value != null) {// 只保存不为空的值
				columnMetadata = tableMetadata.getColumnMetadata(code);
				
				insertSql.append(columnMetadata.getName());
				values.append("?");
				parameters.add(new Parameter(value, columnMetadata.getDataType()));
				
				if(index < size) {
					insertSql.append(",");
					values.append(",");
				}
			}
			index++;
		}
		
		insertSql.append(")").append(values).append(")");
		
		this.sql = insertSql.toString();
	}
}
