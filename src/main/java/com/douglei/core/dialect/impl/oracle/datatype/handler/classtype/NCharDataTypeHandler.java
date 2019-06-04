package com.douglei.core.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.core.dialect.datatype.DBDataType;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractNCharDataTypeHandler;
import com.douglei.core.dialect.impl.oracle.datatype.NChar;

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