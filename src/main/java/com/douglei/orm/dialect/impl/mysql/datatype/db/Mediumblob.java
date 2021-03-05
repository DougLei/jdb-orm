package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractBlob;

/**
 * 
 * @author DougLei
 */
public class Mediumblob extends AbstractBlob{
	
	public Mediumblob() {
		super("MEDIUMBLOB", -4);
	}
}
