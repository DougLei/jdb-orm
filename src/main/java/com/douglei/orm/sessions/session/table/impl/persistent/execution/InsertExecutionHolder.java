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
public class InsertExecutionHolder extends TableExecutionHolder{
	
	public InsertExecutionHolder(TableMetadata tableMetadata, Map<String, Object> propertyMap) {
		super(tableMetadata, propertyMap);
	}
	
	@Override
	protected void initializeInstance() {
		StringBuilder insertSql = new StringBuilder(256);
		insertSql.append("insert into ").append(tableMetadata.getName()).append("(");
		
		StringBuilder values = new StringBuilder();
		values.append(" values(");
		
		int size = propertyMap.size();
		parameters = new ArrayList<Object>(size);// 使用TableExecutionHolder.parameters属性
		
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
				parameters.add(new InputSqlParameter(value, columnMetadata.getDataTypeHandler()));
				
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
