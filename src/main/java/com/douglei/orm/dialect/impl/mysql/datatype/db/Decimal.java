package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Decimal extends DBDataType{
	
	public Decimal() {
		super(3, 38, 30);
	}
}
