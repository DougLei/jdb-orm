package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractSNumberDataType;

/**
 * 
 * @author DougLei
 */
public class SNumberDataType extends AbstractSNumberDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "NUMBER";
	}
}
