package com.douglei.orm.dialect.impl.mysql.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNStringDataType;

/**
 * 
 * @author DougLei
 */
public class NStringDataType extends AbstractNStringDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "VARCHAR";
	}
}
