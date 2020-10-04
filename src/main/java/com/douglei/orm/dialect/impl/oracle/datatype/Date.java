package com.douglei.orm.dialect.impl.oracle.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Date extends DBDataType{
	private static final long serialVersionUID = -5332576249437037101L;
	private static final Date instance = new Date();
	public static final Date singleInstance() {
		return instance;
	}
	
	private Date() {
		super((short)93);
	}
}
