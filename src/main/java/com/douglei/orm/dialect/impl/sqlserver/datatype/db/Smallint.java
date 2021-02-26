package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

/**
 * 
 * @author DougLei
 */
public class Smallint extends com.douglei.orm.dialect.impl.mysql.datatype.db.Smallint{
	private static final Smallint singleton = new Smallint();
	public static Smallint getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Smallint() {}
}
