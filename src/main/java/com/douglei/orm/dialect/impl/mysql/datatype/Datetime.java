package com.douglei.orm.dialect.impl.mysql.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Datetime extends DBDataType{
	private static final long serialVersionUID = -8359028283259409425L;
	
	private static final Datetime instance = new Datetime();
	public static final Datetime singleInstance() {
		return instance;
	}
	
	private Datetime() {
		super((short)93);
	}
}
