package com.douglei.orm.dialect.datatype.db;

/**
 * 
 * @author DougLei
 */
public class DBDataTypeEntity {
	private DBDataType dbDataType;
	private int length;
	private int precision;

	DBDataTypeEntity(DBDataType dbDataType, int length, int precision) {
		this.dbDataType = dbDataType;
		this.length = dbDataType.correctInputLength(length);
		this.precision = dbDataType.correctInputPrecision(this.length, precision);
	}

	public DBDataType getDBDataType() {
		return dbDataType;
	}
	public int getLength() {
		return length;
	}
	public int getPrecision() {
		return precision;
	}
}
