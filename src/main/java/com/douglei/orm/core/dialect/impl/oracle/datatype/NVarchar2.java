package com.douglei.orm.core.dialect.impl.oracle.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NVarchar2 extends DBDataType{
	private static final long serialVersionUID = -4455827669967982037L;
	private static final NVarchar2 instance = new NVarchar2();
	public static final NVarchar2 singleInstance() {
		return instance;
	}
	
	private NVarchar2() {
		super((short)-9, (short)2000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
