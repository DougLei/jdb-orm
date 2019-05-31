package com.douglei.core.dialect;

/**
 * 
 * @author DougLei
 */
public enum DialectType {
	ALL,
	
	ORACLE,
	MYSQL,
	SQLSERVER;
	
	public String getCode() {
		return this.name();
	}
	
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
