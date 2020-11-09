package com.douglei.orm.dialect.impl.oracle.datatype.db;

/**
 * 
 * @author DougLei
 */
public class Date extends com.douglei.orm.dialect.impl.mysql.datatype.db.Datetime {
	private static final long serialVersionUID = -7678619660684840225L;
	private static final Date singleton = new Date();
	public static Date getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Date() {}
}
