package com.douglei.database.dialect.datatype.handler.classtype;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractNCharDataTypeHandler extends AbstractStringDataTypeHandler{
	
	@Override
	public String getCode() {
		return "nchar";
	}
}
