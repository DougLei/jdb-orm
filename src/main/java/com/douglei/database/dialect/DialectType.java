package com.douglei.database.dialect;

/**
 * 
 * @author DougLei
 */
public enum DialectType {
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
