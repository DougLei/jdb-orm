package com.douglei.database.dialect.datatype.handler.classtype;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractNStringDataTypeHandler extends AbstractStringDataTypeHandler{
	
	@Override
	public String getCode() {
		return "nstring";
	}
}
