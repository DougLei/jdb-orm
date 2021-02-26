package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractDecimal;

/**
 * 
 * @author DougLei
 */
public class Decimal extends AbstractDecimal{
	private static final Decimal singleton = new Decimal();
	public static Decimal getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Decimal() {
		super("DECIMAL", 3);
	}
}
