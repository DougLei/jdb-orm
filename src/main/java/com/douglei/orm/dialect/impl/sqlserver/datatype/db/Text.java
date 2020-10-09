package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Text extends DBDataType{
	private static final Text singleton = new Text();
	public static Text getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Text() {
		super(-1);
	}
}
