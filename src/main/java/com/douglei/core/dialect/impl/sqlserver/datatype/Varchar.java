package com.douglei.core.dialect.impl.sqlserver.datatype;

import com.douglei.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varchar extends DBDataType{
	private static final Varchar instance = new Varchar();
	public static final Varchar singleInstance() {
		return instance;
	}
	
	private Varchar() {
		super((short)12, (short)8000);
	}

	@Override
	public short fixInputLength(short inputLength) {
		if(inputLength > super.length) {
			return Short.MAX_VALUE;// Short.MAX_VALUE时, 使用varchar(max)
		}
		return super.fixInputLength(inputLength);
	}
	
	@Override
	public String getDBType4SqlStatement(short length, short precision) {
		if(length == Short.MAX_VALUE) {
			return getTypeName() + "(max)";
		}
		return super.getDBType4SqlStatement(length, precision);
	}
}
