package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execute;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

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
		
		// 处理update set
		boolean isFirst = true;
		Object value = null;
		ColumnMetadata columnMetadata = null;
		for (String code : objectMap.keySet()) {
			if(!tableMetadata.getPrimaryKeyColumns_().containsKey(code)) {
				value = objectMap.get(code);
				if(updateNullValue || value != null) {
					if(isFirst) {
						isFirst = false;
					}else {
						updateSql.append(", ");
					}
					
					columnMetadata = tableMetadata.getColumns_().get(code);
					updateSql.append(columnMetadata.getName()).append("=?");
					parameters.add(new InputSqlParameter(value, columnMetadata.getDBDataType()));
				}
			}
		}
		
		setWhereSqlStatement(updateSql, columnMetadata);
		this.sql = updateSql.toString();
	}
	
	// set对应的where sql语句
	private void setWhereSqlStatement(StringBuilder updateSql, ColumnMetadata columnMetadata) {
		updateSql.append(" where ");
		Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumns_().keySet();
		byte size = (byte) primaryKeyColumnMetadataCodes.size();
		byte index = 1;
		for (String pkCode : primaryKeyColumnMetadataCodes) {
			columnMetadata = tableMetadata.getPrimaryKeyColumns_().get(pkCode);
			
			updateSql.append(columnMetadata.getName()).append("=?");
			parameters.add(new InputSqlParameter(objectMap.get(pkCode), columnMetadata.getDBDataType()));
			
			if(index < size) {
				updateSql.append(" and ");
			}
			index++;
		}
	}
}
