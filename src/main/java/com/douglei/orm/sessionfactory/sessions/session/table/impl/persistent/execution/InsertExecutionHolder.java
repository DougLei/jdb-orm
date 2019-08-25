package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execution;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
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
		StringBuilder insertSql = new StringBuilder(300);
		insertSql.append("insert into ").append(tableMetadata.getName()).append("(");
		
		StringBuilder values = new StringBuilder();
		values.append(" values(");
		
		tableMetadata.setPrimaryKeyValue2EntityMap(propertyMap);
		parameters = new ArrayList<Object>(propertyMap.size());// 使用TableExecutionHolder.parameters属性
		
		boolean isFirst = true;
		Object value = null;
		ColumnMetadata column = null;
		Set<String> codes = propertyMap.keySet();
		for (String code : codes) {
			value = propertyMap.get(code);
			if(value != null) {// 只保存不为空的值
				if(isFirst) {
					isFirst = false;
				}else {
					insertSql.append(",");
					values.append(",");
				}
				column = tableMetadata.getColumnByCode(code);

				insertSql.append(column.getName());
				if(value instanceof PrimaryKeySequence) {
					values.append(((PrimaryKeySequence)value).getNextvalSql());
				}else {
					values.append("?");
					parameters.add(new InputSqlParameter(value, column.getDataTypeHandler()));
				}
			}
		}
		insertSql.append(")").append(values).append(")");
		this.sql = insertSql.toString();
	}
}
