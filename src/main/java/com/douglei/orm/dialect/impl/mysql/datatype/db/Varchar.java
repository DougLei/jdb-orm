package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;

/**
 * 
 * @author DougLei
 */
public class Varchar extends AbstractCharacter{
	
	public Varchar() {
		super("VARCHAR", 12, 1024);
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {String.class};
	}
}
