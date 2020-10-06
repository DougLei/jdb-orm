package com.douglei.orm.dialect.temp.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DataType2;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractCharDataTypeHandler extends AbstractStringDataTypeHandler{
	private static final long serialVersionUID = 6974292051348548908L;

	@Override
	public String getCode() {
		return DataType2.CHAR.getName();
	}
}
