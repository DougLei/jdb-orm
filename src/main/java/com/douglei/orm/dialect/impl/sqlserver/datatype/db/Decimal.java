package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractDecimal;

/**
 * 
 * @author DougLei
 */
public class Decimal extends AbstractDecimal{
	
	public Decimal() {
		super("DECIMAL", 3);
	}
}
