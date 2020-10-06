package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Number extends DBDataType{
	
	public Number() {
		super(2, 38, 38);
	}
}
