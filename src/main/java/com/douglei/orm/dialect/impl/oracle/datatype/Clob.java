package com.douglei.orm.dialect.impl.oracle.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Clob extends DBDataType{
	private static final long serialVersionUID = 2656226552193009937L;
	private static final Clob instance = new Clob();
	public static final Clob singleInstance() {
		return instance;
	}
	
	private Clob() {
		super((short)2005);
	}
}
