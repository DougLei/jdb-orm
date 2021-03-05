package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;

/**
 * 
 * @author DougLei
 */
public class NChar extends AbstractCharacter{
	
	public NChar() {
		super("NCHAR", -15, 1000);
	}
}
