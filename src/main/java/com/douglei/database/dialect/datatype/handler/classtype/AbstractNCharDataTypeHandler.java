package com.douglei.database.dialect.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.DataType;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractNCharDataTypeHandler extends AbstractStringDataTypeHandler{
	
	@Override
	public String getCode() {
		return DataType.NCHAR.getName();
	}
}
