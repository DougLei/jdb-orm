package com.douglei.database.sql.statement;

import com.douglei.database.datatype.DataTypeHandler;

/**
 * 参数对象
 * @author DougLei
 */
public class Parameter {
	private Object value;
	private DataTypeHandler dataTypeHandler;
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public DataTypeHandler getDataTypeHandler() {
		return dataTypeHandler;
	}
	public void setDataTypeHandler(DataTypeHandler dataTypeHandler) {
		this.dataTypeHandler = dataTypeHandler;
	}
}
