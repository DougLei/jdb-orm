package com.douglei.database.datatype.impl;

import com.douglei.database.datatype.DataTypeHandler;

/**
 * string类型
 * @author DougLei
 */
public class StringDataTypeHandler implements DataTypeHandler {

	@Override
	public String getDataType() {
		return "string";
	}
}
