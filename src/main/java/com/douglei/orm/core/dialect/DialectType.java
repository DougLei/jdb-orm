package com.douglei.orm.core.dialect;

/**
 * 
 * @author DougLei
 */
public enum DialectType {
	ALL,
	
	ORACLE,
	MYSQL,
	SQLSERVER;
	
	public static DialectType toValue(String dialect) {
		dialect = dialect.toUpperCase();
		DialectType[] dts = DialectType.values();
		for (DialectType dt : dts) {
			if(dt.name().equals(dialect)) {
				return dt;
			}
		}
		return null;
	}
}
