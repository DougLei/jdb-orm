package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;

/**
 * oracle datatype handler mapping
 * @author DougLei
 */
public class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	protected String[] dataTypeHandlerBasePackages() {
		return new String[] {"com.douglei.database.dialect.impl.oracle.datatype.classtype", 
				"com.douglei.database.dialect.impl.oracle.datatype.resultset.columntype"};
	}
}
