package com.douglei.orm.core.dialect.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DataType;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractNCharDataTypeHandler extends AbstractStringDataTypeHandler{
	private static final long serialVersionUID = 4141989128449412025L;

	@Override
	public String getCode() {
		return DataType.NCHAR.getName();
	}
}
