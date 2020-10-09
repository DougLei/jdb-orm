package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

/**
 * 
 * @author DougLei
 */
public class Int extends com.douglei.orm.dialect.impl.mysql.datatype.db.Int{
	private static final long serialVersionUID = -249269019506098763L;
	private static final Int singleton = new Int();
	public static Int getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
}
