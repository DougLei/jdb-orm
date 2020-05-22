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
public class DeleteExecuteHandler extends TableExecuteHandler{
	
	public DeleteExecuteHandler(TableMetadata tableMetadata, Map<String, Object> propertyMap) {
		super(tableMetadata, propertyMap);
	}

	@Override
	protected void initial() {
		StringBuilder deleteSql = new StringBuilder(200);
		deleteSql.append("delete ").append(tableMetadata.getName()).append(" where ");
		
		if(tableMetadata.existsPrimaryKey()) {
			setSqlWhenExistsPrimaryKey(deleteSql);
		}else {
			setSqlWhenUnExistsPrimaryKey(deleteSql);
		}
		this.sql = deleteSql.toString();
	}
	
	// 当存在primaryKey时, set对应的sql语句
	private void setSqlWhenExistsPrimaryKey(StringBuilder deleteSql) {
		Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumnCodes();
		byte size = (byte)primaryKeyColumnMetadataCodes.size();
		
		parameters = new ArrayList<Object>(size);
		
		ColumnMetadata primaryKeyColumnMetadata = null;
		byte index = 1;
		for (String pkCode : primaryKeyColumnMetadataCodes) {
			primaryKeyColumnMetadata = tableMetadata.getPrimaryKeyColumnByCode(pkCode);
			
			deleteSql.append(primaryKeyColumnMetadata.getName()).append("=?");
			parameters.add(new InputSqlParameter(propertyMap.get(pkCode), primaryKeyColumnMetadata.getDataTypeHandler()));
			
			if(index < size) {
				deleteSql.append(" and ");
			}
			index++;
		}
	}
	
	// 当不存在primaryKey时, set对应的sql语句
	private void setSqlWhenUnExistsPrimaryKey(StringBuilder deleteSql) {
		short size = (short) propertyMap.size();
		parameters = new ArrayList<Object>(size);
		
		int index = 1;
		Object value = null;
		ColumnMetadata columnMetadata = null;
		for (String code : propertyMap.keySet()) {
			columnMetadata = tableMetadata.getColumnByCode(code);
			value = propertyMap.get(code);
			
			if(value == null) {
				deleteSql.append(columnMetadata.getName()).append(" is null");
			}else {
				deleteSql.append(columnMetadata.getName()).append("=?");
				parameters.add(new InputSqlParameter(value, columnMetadata.getDataTypeHandler()));
			}
			
			if(index < size) {
				deleteSql.append(" and ");
			}
			index++;
		}
	}
}
