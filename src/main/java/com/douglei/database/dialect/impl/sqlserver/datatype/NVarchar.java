package com.douglei.database.dialect.impl.sqlserver.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NVarchar extends DBDataType{
	private static final NVarchar instance = new NVarchar();
	public static final NVarchar singleInstance() {
		return instance;
	}
	
	private NVarchar() {
		super((short)-9, (short)4000);
	}
}
