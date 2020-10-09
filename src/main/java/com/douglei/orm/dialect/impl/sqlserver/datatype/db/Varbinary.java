package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractBlob;

/**
 * 
 * @author DougLei
 */
public class Varbinary extends AbstractBlob{
	private static final Varbinary singleton = new Varbinary();
	public static Varbinary getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Varbinary() {
		super(-3);
	}
	
	@Override
	public String getSqlStatement(int length, int precision) {
		return name + "(MAX)";
	}
}
