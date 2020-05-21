package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execute;

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
public class UpdateExecuteHandler extends TableExecuteHandler{
	private boolean updateNullValue;
	
	public UpdateExecuteHandler(TableMetadata tableMetadata, Map<String, Object> propertyMap, boolean updateNullValue) {
		super(tableMetadata, propertyMap);
		this.updateNullValue = updateNullValue;
	}

	@Override
	protected void initializeInstance() {
		StringBuilder updateSql = new StringBuilder(300);
		updateSql.append("update ").append(tableMetadata.getName()).append(" set ");
		
		parameters = new ArrayList<Object>(propertyMap.size());// 使用TableExecutionHolder.parameters属性
		
		// 处理update set
		boolean isFirst = true;
		Object value = null;
		ColumnMetadata columnMetadata = null;
		for (String code : propertyMap.keySet()) {
			if(!tableMetadata.isPrimaryKeyColumnByCode(code)) {
				value = propertyMap.get(code);
				if(updateNullValue || value != null) {
					if(isFirst) {
						isFirst = false;
					}else {
						updateSql.append(", ");
					}
					
					columnMetadata = tableMetadata.getColumnByCode(code);
					updateSql.append(columnMetadata.getName()).append('=');
					
					if(value == null) {
						updateSql.append("null");
					}else {
						updateSql.append('?');
						parameters.add(new InputSqlParameter(value, columnMetadata.getDataTypeHandler()));
					}
				}
			}
		}
		
		setWhereSqlWhenExistsPrimaryKey(updateSql, columnMetadata);
		this.sql = updateSql.toString();
	}
	
	// 当存在primaryKey时, set对应的where sql语句
	private void setWhereSqlWhenExistsPrimaryKey(StringBuilder updateSql, ColumnMetadata columnMetadata) {
		updateSql.append(" where ");
		Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumnCodes();
		byte size = (byte) primaryKeyColumnMetadataCodes.size();
		byte index = 1;
		for (String pkCode : primaryKeyColumnMetadataCodes) {
			columnMetadata = tableMetadata.getPrimaryKeyColumnByCode(pkCode);
			
			updateSql.append(columnMetadata.getName()).append("=?");
			parameters.add(new InputSqlParameter(propertyMap.get(pkCode), columnMetadata.getDataTypeHandler()));
			
			if(index < size) {
				updateSql.append(" and ");
			}
			index++;
		}
	}
}
