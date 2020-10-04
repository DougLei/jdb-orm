package com.douglei.orm.dialect.impl.sqlserver.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Text extends DBDataType{
	private static final long serialVersionUID = 2492354855149646883L;
	private static final Text instance = new Text();
	public static final Text singleInstance() {
		return instance;
	}
	
	private Text() {
		super((short)-1);
	}
}
