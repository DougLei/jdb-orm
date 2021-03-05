package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractClobDataType;

/**
 * 
 * @author DougLei
 */
public class ClobDataType extends AbstractClobDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "CLOB";
	}
}
