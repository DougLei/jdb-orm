package com.douglei.orm.dialect.impl.mysql.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNumberDataType;

/**
 * 
 * @author DougLei
 */
public class NumberDataType extends AbstractNumberDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		if(precision > 0)
			return "DECIMAL";
		if(length > 0 && length < 6)
			return "SMALLINT";
		if(length > 10)
			return "BIGINT";
		return "INT";
	}
}
