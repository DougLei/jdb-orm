package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

/**
 * 
 * @author DougLei
 */
public class Date extends com.douglei.orm.dialect.impl.mysql.datatype.db.Date {
	private static final long serialVersionUID = 2326328774369872361L;
	private static final Date singleton = new Date();
	public static Date getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
}