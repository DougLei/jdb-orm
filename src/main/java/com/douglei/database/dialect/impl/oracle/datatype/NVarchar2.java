package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NVarchar2 extends DBDataType{
	private static final NVarchar2 instance = new NVarchar2();
	public static final NVarchar2 singleInstance() {
		return instance;
	}
	
	private NVarchar2() {
		super((short)-9, (short)2000);
	}
}
