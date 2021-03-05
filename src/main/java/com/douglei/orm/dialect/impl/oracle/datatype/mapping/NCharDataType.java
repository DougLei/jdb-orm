package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNCharDataType;

/**
 * 
 * @author DougLei
 */
public class NCharDataType extends AbstractNCharDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "NCHAR";
	}
}
