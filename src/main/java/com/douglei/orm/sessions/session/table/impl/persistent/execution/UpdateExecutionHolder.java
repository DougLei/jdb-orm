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
public class UpdateExecutionHolder extends TableExecutionHolder{
	
	public UpdateExecutionHolder(TableMetadata tableMetadata, Map<String, Object> propertyMap) {
		super(tableMetadata, propertyMap);
	}

	@Override
	protected void initializeInstance() {
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("update ").append(tableMetadata.getName()).append(" set ");
		
		Set<String> codes = propertyMap.keySet();
		parameters = new ArrayList<Object>(propertyMap.size());// 使用TableExecutionHolder.parameters属性
		
		// 处理update set
		boolean isFirst = true;
		Object value = null;
		ColumnMetadata columnMetadata = null;
		for (String code : codes) {
			if(!tableMetadata.isPrimaryKeyColumnMetadata(code)) {
				value = propertyMap.get(code);
				if(value != null) {// 只修改不为空的值
					if(isFirst) {
						isFirst = false;
					}else {
						updateSql.append(", ");
					}
					
					columnMetadata = tableMetadata.getColumnMetadata(code);
					
					updateSql.append(columnMetadata.getName()).append("=?");
					parameters.add(new InputSqlParameter(value, columnMetadata.getDataTypeHandler()));
				}
			}
		}
		
		setWhereSqlWhenExistsPrimaryKey(updateSql, columnMetadata);
		this.sql = updateSql.toString();
	}
	
	// 当存在primaryKey时, set对应的where sql语句
	private void setWhereSqlWhenExistsPrimaryKey(StringBuilder updateSql, ColumnMetadata columnMetadata) {
		updateSql.append(" where ");
		Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumnMetadataCodes();
		int size = primaryKeyColumnMetadataCodes.size();
		byte index = 1;
		for (String pkCode : primaryKeyColumnMetadataCodes) {
			columnMetadata = tableMetadata.getPrimaryKeyColumnMetadata(pkCode);
			
			updateSql.append(columnMetadata.getName()).append("=?");
			parameters.add(new InputSqlParameter(propertyMap.get(pkCode), columnMetadata.getDataTypeHandler()));
			
			if(index < size) {
				updateSql.append(" and ");
			}
			index++;
		}
	}
}
