package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNStringDataType;

/**
 * 
 * @author DougLei
 */
public class NStringDataType extends AbstractNStringDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "NVARCHAR2";
	}
}
