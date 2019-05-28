package com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.DBDataType;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractNCharDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.NChar;

/**
 * 
 * @author DougLei
 */
public class NCharDataTypeHandler extends AbstractNCharDataTypeHandler{
	private NCharDataTypeHandler() {}
	private static final NCharDataTypeHandler instance = new NCharDataTypeHandler();
	public static final NCharDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType defaultDBDataType() {
		return NChar.singleInstance();
	}
}
