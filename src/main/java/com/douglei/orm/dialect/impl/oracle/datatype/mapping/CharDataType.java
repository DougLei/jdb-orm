package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractCharDataType;

/**
 * 
 * @author DougLei
 */
public class CharDataType extends AbstractCharDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "CHAR";
	}
}
