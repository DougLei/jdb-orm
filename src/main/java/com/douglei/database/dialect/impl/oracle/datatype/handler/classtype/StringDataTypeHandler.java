package com.douglei.database.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.DBDataType;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.Varchar2;

/**
 * 
 * @author DougLei
 */
public class StringDataTypeHandler extends AbstractStringDataTypeHandler{
	private StringDataTypeHandler() {}
	private static final StringDataTypeHandler instance = new StringDataTypeHandler();
	public static final StringDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected DBDataType defaultDBDataType() {
		return Varchar2.singleInstance();
	}
}
