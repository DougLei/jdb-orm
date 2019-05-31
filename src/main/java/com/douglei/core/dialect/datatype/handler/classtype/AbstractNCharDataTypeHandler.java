package com.douglei.core.dialect.datatype.handler.classtype;

import com.douglei.core.dialect.datatype.DataType;

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
