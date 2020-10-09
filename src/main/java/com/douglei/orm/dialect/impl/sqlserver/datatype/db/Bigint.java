package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

/**
 * 
 * @author DougLei
 */
public class Bigint extends com.douglei.orm.dialect.impl.mysql.datatype.db.Bigint{
	private static final long serialVersionUID = 5956381217222676845L;
	private static final Bigint singleton = new Bigint();
	public static Bigint getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
}
