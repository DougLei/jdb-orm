package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Number extends DBDataType{
	private static final Number instance = new Number();
	public static final Number singleInstance() {
		return instance;
	}
	
	private Number() {
		super((short)2, (short)38, (short)38);
	}
	
	@Override
	public boolean supportPrecision() {
		return true;
	}
}
