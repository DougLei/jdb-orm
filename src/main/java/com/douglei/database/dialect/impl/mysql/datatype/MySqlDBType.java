package com.douglei.database.dialect.impl.mysql.datatype;

/**
 * 
 * @author DougLei
 */
public enum MySqlDBType {
	VARCHAR(12),
	CHAR(1),
	INT(4),
	DECIMAL(3),
	DATETIME(93),
	TEXT(-1),
	BLOB(-4),
	BIGINT(-5)
	;
	
	private String typeName;
	private int sqlType;

	private MySqlDBType(int sqlType) {
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
