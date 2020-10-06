package com.douglei.orm.dialect.datatype.mapping.impl;

import com.douglei.orm.dialect.datatype.mapping.MappingDataType;

/**
 * 
 * @author DougLei
 */
public abstract class StringDataType extends MappingDataType {

	@Override
	public final String getName() {
		return "string";
	}
}
