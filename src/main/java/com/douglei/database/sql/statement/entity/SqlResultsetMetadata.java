package com.douglei.database.sql.statement.entity;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.sql.statement.LocalDialect;

/**
 * sql结果集元数据对象
 * @author DougLei
 */
public class SqlResultsetMetadata {
	// 列名
	private String columnName;
	// 要进行数据处理的DataTypeHandler
	private DataTypeHandler dataTypeHandler;
	
	public SqlResultsetMetadata(String columnName, int columnType) {
		this.columnName = columnName.toUpperCase();
		this.dataTypeHandler = LocalDialect.getDialect().getDataTypeHandlerByDatabaseColumnType(columnType);
	}

	public String getColumnName() {
		return columnName;
	}
	public DataTypeHandler getDataTypeHandler() {
		return dataTypeHandler;
	}

	@Override
	public String toString() {
		return "SqlResultsetMetadata [columnName=" + columnName + ", dataTypeHandler=" + dataTypeHandler + "]";
	}
}
