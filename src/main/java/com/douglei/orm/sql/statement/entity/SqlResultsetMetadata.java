package com.douglei.orm.sql.statement.entity;

import com.douglei.orm.EnvironmentContext;
import com.douglei.orm.dialect.temp.datatype.handler.DataTypeHandler;

/**
 * sql结果集元数据对象
 * @author DougLei
 */
public class SqlResultsetMetadata {
	// 列名
	private String columnName;
	// 要进行数据处理的DataTypeHandler
	private DataTypeHandler dataTypeHandler;
	
	public SqlResultsetMetadata(String columnName, int columnType, String columnTypeName) {
		this.columnName = columnName;
		this.dataTypeHandler = EnvironmentContext.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByDatabaseColumnType(columnType, columnName, columnTypeName);
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
