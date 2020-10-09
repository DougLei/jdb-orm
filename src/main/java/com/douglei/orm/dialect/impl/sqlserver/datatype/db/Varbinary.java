package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varbinary extends DBDataType{
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
		return name + "(max)";
	}
}
