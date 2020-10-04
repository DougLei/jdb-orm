package com.douglei.orm.dialect.impl.oracle.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Blob extends DBDataType{
	private static final long serialVersionUID = 5738963494543586697L;
	private static final Blob instance = new Blob();
	public static final Blob singleInstance() {
		return instance;
	}
	
	private Blob() {
		super((short)2004);
	}
}
