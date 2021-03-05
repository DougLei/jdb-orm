package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNumberDataType;

/**
 * 
 * @author DougLei
 */
public class NumberDataType extends AbstractNumberDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "NUMBER";
	}
}
