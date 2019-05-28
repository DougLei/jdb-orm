package com.douglei.database.dialect.impl.sqlserver.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

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
}
