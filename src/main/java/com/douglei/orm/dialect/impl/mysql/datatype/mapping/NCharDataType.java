package com.douglei.orm.dialect.impl.mysql.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNCharDataType;

/**
 * 
 * @author DougLei
 */
public class NCharDataType extends AbstractNCharDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "CHAR";
	}
}
