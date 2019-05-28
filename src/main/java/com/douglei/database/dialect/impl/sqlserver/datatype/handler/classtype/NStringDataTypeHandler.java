package com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.DBDataType;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractNStringDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.NVarchar;

/**
 * 
 * @author DougLei
 */
public class NStringDataTypeHandler extends AbstractNStringDataTypeHandler{
	private NStringDataTypeHandler() {}
	private static final NStringDataTypeHandler instance = new NStringDataTypeHandler();
	public static final NStringDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType defaultDBDataType() {
		return NVarchar.singleInstance();
	}
}
