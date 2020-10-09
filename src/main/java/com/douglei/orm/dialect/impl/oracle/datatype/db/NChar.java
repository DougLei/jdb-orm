package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;

/**
 * 
 * @author DougLei
 */
public class NChar extends AbstractCharacter{
	private static final long serialVersionUID = 7039661715190880649L;
	private static final NChar singleton = new NChar();
	public static NChar getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private NChar() {
		super(-15, 1000);
	}
}
