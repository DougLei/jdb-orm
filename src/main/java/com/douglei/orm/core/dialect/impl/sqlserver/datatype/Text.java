package com.douglei.orm.core.dialect.impl.sqlserver.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Text extends DBDataType{
	private static final Text instance = new Text();
	public static final Text singleInstance() {
		return instance;
	}
	
	private Text() {
		super((short)-1);
	}
}