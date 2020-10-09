package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;

/**
 * 
 * @author DougLei
 */
public class NVarchar2 extends AbstractCharacter{
	private static final long serialVersionUID = 2583499014173244514L;
	private static final NVarchar2 singleton = new NVarchar2();
	public static NVarchar2 getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private NVarchar2() {
		super(-9, 2000);
	}
}
