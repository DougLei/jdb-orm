package com.douglei.database.dialect.datatype;

/**
 * 数据库数据类型
 * @author DougLei
 */
public abstract class DBDataType {
	protected short sqlType;// @see java.sql.Types
	protected String typeName;// 类型的名称, 大写
	
	public DBDataType(short sqlType) {
		this.typeName = getClass().getSimpleName().toUpperCase();
		this.sqlType = sqlType;
	}
	
	public short getSqlType() {
		return sqlType;
	}
	public String getTypeName() {
		return typeName;
	}
}
