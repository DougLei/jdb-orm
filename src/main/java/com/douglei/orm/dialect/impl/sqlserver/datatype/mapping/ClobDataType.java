package com.douglei.orm.dialect.impl.sqlserver.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractClobDataType;

/**
 * 
 * @author DougLei
 */
public class ClobDataType extends AbstractClobDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "VARCHAR(MAX)";
	}
}
