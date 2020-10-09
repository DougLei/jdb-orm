package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractBlob;

/**
 * 
 * @author DougLei
 */
public class Varbinarymax extends AbstractBlob{
	private static final long serialVersionUID = -3877395094547043545L;
	private static final Varbinarymax singleton = new Varbinarymax();
	public static Varbinarymax getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Varbinarymax() {
		super(-3);
		super.name = "VARBINARY(MAX)";
	}
}
