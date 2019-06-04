package com.douglei.core.dialect.impl.mysql.datatype;

import com.douglei.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Decimal extends DBDataType{
	private static final Decimal instance = new Decimal();
	public static final Decimal singleInstance() {
		return instance;
	}
	
	private Decimal() {
		super((short)3, (short)38, (short)30);
	}
}