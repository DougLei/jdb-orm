package com.douglei.orm.dialect.datatype.util;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class DBDataTypeWrapper {
	private DBDataType dbDataType;
	private int length;
	private int precision;

	DBDataTypeWrapper(DBDataType dbDataType, int length, int precision) {
		this.dbDataType = dbDataType;
		this.length = dbDataType.correctInputLength(this.length);
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
