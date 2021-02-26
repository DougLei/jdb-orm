package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractClob;

/**
 * 
 * @author DougLei
 */
public class Clob extends AbstractClob{
	private static final Clob singleton = new Clob();
	public static Clob getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Clob() {
		super("CLOB", 2005);
	}
}
