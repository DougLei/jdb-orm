package com.douglei.database.dialect.impl.mysql.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Text extends DBDataType{
	private static final short SQL_TYPE = -1;
	private static final Text instance = new Text();
	public static final Text singleInstance() {
		return instance;
	}
	
	private Text() {
		super(SQL_TYPE);
	}
}
