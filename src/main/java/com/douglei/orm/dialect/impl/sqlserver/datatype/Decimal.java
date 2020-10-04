package com.douglei.orm.dialect.impl.sqlserver.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Decimal extends DBDataType{
	private static final long serialVersionUID = -8400466948422638618L;
	private static final Decimal instance = new Decimal();
	public static final Decimal singleInstance() {
		return instance;
	}
	
	private Decimal() {
		super((short)3, (short)38, (short)38);
	}
}
