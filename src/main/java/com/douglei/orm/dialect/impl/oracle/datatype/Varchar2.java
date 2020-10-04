package com.douglei.orm.dialect.impl.oracle.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varchar2 extends DBDataType{
	private static final long serialVersionUID = 5552588661950902640L;
	private static final Varchar2 instance = new Varchar2();
	public static final Varchar2 singleInstance() {
		return instance;
	}
	
	private Varchar2() {
		super((short)12, (short)4000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
