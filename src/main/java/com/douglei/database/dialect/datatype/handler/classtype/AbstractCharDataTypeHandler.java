package com.douglei.database.dialect.datatype.handler.classtype;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractCharDataTypeHandler extends AbstractStringDataTypeHandler{
	
	@Override
	public String getCode() {
		return "char";
	}
}
