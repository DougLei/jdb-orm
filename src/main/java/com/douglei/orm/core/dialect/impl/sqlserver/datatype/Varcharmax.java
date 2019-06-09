package com.douglei.orm.core.dialect.impl.sqlserver.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varcharmax extends DBDataType{
	private static final Varcharmax instance = new Varcharmax();
	public static final Varcharmax singleInstance() {
		return instance;
	}
	
	private Varcharmax() {
		super((short)12);
	}
	
	@Override
	public String getTypeName() {
		if(unProcessTypeName) {
			typeName = "VARCHAR";
			unProcessTypeName = false;
		}
		return typeName;
	}
	private boolean unProcessTypeName=true;
	
	@Override
	public String getDBType4SqlStatement(short length, short precision) {
		return getTypeName() + "(max)";
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
