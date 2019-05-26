package com.douglei.database.dialect.impl.oracle.datatype;

/**
 * 
 * @author DougLei
 */
public enum OracleDBType {
	VARCHAR2(12),
	NVARCHAR2(-9),
	CHAR(1),
	NCHAR(-15),
	NUMBER(2),
	DATE(93),
	CLOB(2005),
	BLOB(2004)
	;
	
	private String typeName;
	private int sqlType;

	private OracleDBType(int sqlType) {
		this.typeName = name();
		this.sqlType = sqlType;
	}

	public String getTypeName() {
		return typeName;
	}
	public int getSqlType() {
		return sqlType;
	}
}
