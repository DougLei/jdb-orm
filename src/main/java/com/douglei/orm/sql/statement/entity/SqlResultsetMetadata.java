package com.douglei.orm.sql.statement.entity;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * sql结果集元数据对象
 * @author DougLei
 */
public class SqlResultsetMetadata {
	// 列名
	private String columnName;
	// 要进行数据处理的dataType
	private DBDataType dbDataType;
	
	public SqlResultsetMetadata(String columnName, int columnType, String columnTypeName) {
		this.columnName = columnName;
		this.dbDataType = EnvironmentContext.getEnvironment().getDialect().getDataTypeContainer().getDBDataTypeByColumnType(columnType);
	}

	public String getColumnName() {
		return columnName;
	}
	public DBDataType getDBDataType() {
		return dbDataType;
	}

	@Override
	public String toString() {
		return "SqlResultsetMetadata [columnName=" + columnName + ", dbDataType=" + dbDataType + "]";
	}
}
