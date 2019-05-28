package com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.DBDataType;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractCharDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.Char;

/**
 * 
 * @author DougLei
 */
public class CharDataTypeHandler extends AbstractCharDataTypeHandler{
	private CharDataTypeHandler() {}
	private static final CharDataTypeHandler instance = new CharDataTypeHandler();
	public static final CharDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType defaultDBDataType() {
		return Char.singleInstance();
	}
}
