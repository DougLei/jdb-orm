package com.douglei.core.dialect.datatype.handler.classtype;

import com.douglei.core.dialect.datatype.DataType;

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
