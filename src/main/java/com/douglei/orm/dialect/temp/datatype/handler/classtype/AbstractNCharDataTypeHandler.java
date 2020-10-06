package com.douglei.orm.dialect.temp.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DataType2;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractNCharDataTypeHandler extends AbstractStringDataTypeHandler{
	private static final long serialVersionUID = 4141989128449412025L;

	@Override
	public String getCode() {
		return DataType2.NCHAR.getName();
	}
}
