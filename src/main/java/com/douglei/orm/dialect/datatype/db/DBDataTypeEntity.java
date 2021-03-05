package com.douglei.orm.dialect.datatype.db;

/**
 * 
 * @author DougLei
 */
public class DBDataTypeEntity {
	private DBDataType DBDataType;
	private int length;
	private int precision;

	DBDataTypeEntity(DBDataType DBDataType, int length, int precision) {
		this.DBDataType = DBDataType;
		this.length = DBDataType.correctInputLength(length);
		this.precision = DBDataType.correctInputPrecision(this.length, precision);
	}

	public DBDataType getDBDataType() {
		return DBDataType;
	}
	public int getLength() {
		return length;
	}
	public int getPrecision() {
		return precision;
	}
}
