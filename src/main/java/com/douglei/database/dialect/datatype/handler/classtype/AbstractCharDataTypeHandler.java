package com.douglei.database.dialect.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.DataType;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractCharDataTypeHandler extends AbstractStringDataTypeHandler{
	
	@Override
	public String getCode() {
		return DataType.CHAR.getName();
	}
}
