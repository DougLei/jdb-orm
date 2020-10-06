package com.douglei.orm.dialect.temp.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DataType2;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractNStringDataTypeHandler extends AbstractStringDataTypeHandler{
	private static final long serialVersionUID = -1718226656484396409L;

	@Override
	public String getCode() {
		return DataType2.NSTRING.getName();
	}
}
