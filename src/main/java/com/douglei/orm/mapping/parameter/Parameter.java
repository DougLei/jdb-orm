package com.douglei.orm.mapping.parameter;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 参数, 针对table和sql, 其中table的参数即列
 * @author DougLei
 */
public class Parameter {
	private String name; // 参数名
	private DBDataType dbDataType; // 参数类型

	public Parameter(String name, DBDataType dbDataType) {
		this.name = name;
		this.dbDataType = dbDataType;
	}
	
	public String getName() {
		return name;
	}
	public DBDataType getDbDataType() {
		return dbDataType;
	}
}
