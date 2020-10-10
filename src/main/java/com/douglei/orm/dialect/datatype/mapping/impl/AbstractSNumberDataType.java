package com.douglei.orm.dialect.datatype.mapping.impl;

import com.douglei.orm.dialect.datatype.mapping.MappingDataType;

/**
 * Secure Number
 * @author DougLei
 */
public abstract class AbstractSNumberDataType extends MappingDataType {

	@Override
	public final String getName() {
		return "snumber";
	}
}
