package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractDecimal;

/**
 * 
 * @author DougLei
 */
public class Decimal extends AbstractDecimal{
	private static final long serialVersionUID = 7840095229072566923L;
	private static final Decimal singleton = new Decimal();
	public static Decimal getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Decimal() {
		super(3, 38, 38);
	}
}
