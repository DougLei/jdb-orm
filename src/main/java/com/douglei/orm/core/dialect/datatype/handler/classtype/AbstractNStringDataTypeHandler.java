package com.douglei.orm.core.dialect.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DataType;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractNStringDataTypeHandler extends AbstractStringDataTypeHandler{
	
	@Override
	public String getCode() {
		return DataType.NSTRING.getName();
	}
}
