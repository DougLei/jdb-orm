package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractBlob;

/**
 * 
 * @author DougLei
 */
public class Varbinarymax extends AbstractBlob{
	
	public Varbinarymax() {
		super("VARBINARY(MAX)", -3);
	}
}
