package com.douglei.orm.dialect.impl.sqlserver.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractDatetimeDataType;

/**
 * 
 * @author DougLei
 */
public class DatetimeDataType extends AbstractDatetimeDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "DATETIME";
	}
}
