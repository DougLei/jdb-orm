package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractClob;

/**
 * 
 * @author DougLei
 */
public class Mediumtext extends AbstractClob {
	
	public Mediumtext() {
		super("MEDIUMTEXT", -1);
	}
}
