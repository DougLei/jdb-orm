package com.douglei.core.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.core.dialect.datatype.DBDataType;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;
import com.douglei.core.dialect.impl.sqlserver.datatype.Varcharmax;

/**
 * 
 * @author DougLei
 */
public class ClobDataTypeHandler extends AbstractStringDataTypeHandler{
	private ClobDataTypeHandler() {}
	private static final ClobDataTypeHandler instance = new ClobDataTypeHandler();
	public static final ClobDataTypeHandler singleInstance() {
		return instance;
	}

	@Override
	public DBDataType defaultDBDataType() {
		return Varcharmax.singleInstance();
	}
}
