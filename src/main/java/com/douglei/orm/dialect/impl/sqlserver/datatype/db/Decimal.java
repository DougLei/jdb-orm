package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractDecimal;

/**
 * 
 * @author DougLei
 */
public class Decimal extends AbstractDecimal{
	private static final long serialVersionUID = 2176905356327022658L;
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
