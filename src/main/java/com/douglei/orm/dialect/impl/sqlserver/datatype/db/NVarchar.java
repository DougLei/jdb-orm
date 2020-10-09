package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NVarchar extends DBDataType{
	private static final NVarchar singleton = new NVarchar();
	public static NVarchar getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private NVarchar() {
		super(-9, 4000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
