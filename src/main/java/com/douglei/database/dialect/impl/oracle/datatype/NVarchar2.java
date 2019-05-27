package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NVarchar2 extends DBDataType{
	private static final short SQL_TYPE = -9;
	private static final NVarchar2 instance = new NVarchar2();
	public static final NVarchar2 singleInstance() {
		return instance;
	}
	
	private NVarchar2() {
		super(SQL_TYPE);
	}
}
