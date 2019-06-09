package com.douglei.orm.core.dialect.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DataType;

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
