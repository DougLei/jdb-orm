package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NVarchar2 extends DBDataType{
	private static final NVarchar2 singleton = new NVarchar2();
	public static NVarchar2 getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private NVarchar2() {
		super(-9, 2000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
