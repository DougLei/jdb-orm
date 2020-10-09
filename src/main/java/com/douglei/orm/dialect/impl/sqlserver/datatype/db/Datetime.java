package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

/**
 * 
 * @author DougLei
 */
public class Datetime extends com.douglei.orm.dialect.impl.mysql.datatype.db.Datetime {
	private static final long serialVersionUID = 1957015124804504084L;
	private static final Datetime singleton = new Datetime();
	public static Datetime getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
}
