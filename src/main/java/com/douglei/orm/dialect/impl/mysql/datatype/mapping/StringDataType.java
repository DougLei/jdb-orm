package com.douglei.orm.dialect.impl.mysql.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractStringDataType;

/**
 * 
 * @author DougLei
 */
public class StringDataType extends AbstractStringDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "VARCHAR";
	}
}
