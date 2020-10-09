package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varcharmax extends DBDataType{
	private static final Varcharmax singleton = new Varcharmax();
	public static Varcharmax getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Varcharmax() {
		super(12);
		super.name = "VARCHAR";
	}
	
	@Override
	public String getSqlStatement(int length, int precision) {
		return name + "(max)";
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
