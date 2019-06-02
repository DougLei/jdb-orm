package com.douglei.core.dialect.impl.sqlserver.datatype;

import com.douglei.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varbinary extends DBDataType{
	private static final Varbinary instance = new Varbinary();
	public static final Varbinary singleInstance() {
		return instance;
	}
	
	private Varbinary() {
		super((short)-3);
	}
	
	@Override
	public String getDBType4SqlStatement(short length, short precision) {
		return getTypeName() + "(max)";
	}
}
