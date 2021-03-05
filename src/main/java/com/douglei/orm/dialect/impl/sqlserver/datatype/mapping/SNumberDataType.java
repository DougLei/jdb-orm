package com.douglei.orm.dialect.impl.sqlserver.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractSNumberDataType;

/**
 * 
 * @author DougLei
 */
public class SNumberDataType extends AbstractSNumberDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		if(precision > 0)
			return "DECIMAL";
		if(length > 0 && length < 5)
			return "SMALLINT";
		if(length > 9)
			return "BIGINT";
		return "INT";
	}
}
