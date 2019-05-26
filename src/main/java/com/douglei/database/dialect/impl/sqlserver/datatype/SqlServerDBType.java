package com.douglei.database.dialect.impl.sqlserver.datatype;

/**
 * 
 * @author DougLei
 */
public enum SqlServerDBType {
	VARCHAR(12),
	NVARCHAR(-9),
	CHAR(1),
	NCHAR(-15),
	INT(4),
	DECIMAL(3),
	DATETIME(93),
	TEXT(-1),
	VARBINARY(-3),
	BIGINT(-5)
	;
	
	private String typeName;
	private int sqlType;

	private SqlServerDBType(int sqlType) {
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
