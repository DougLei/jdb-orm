package com.douglei.orm.dialect.datatype;

import java.io.Serializable;

/**
 * 数据库数据类型
 * @author DougLei
 */
public abstract class DBDataType implements Serializable{
	private static final long serialVersionUID = -2656288653528204942L;

	public static final short NO_LIMIT = -1;
	
	protected short sqlType;// @see java.sql.Types
	protected String typeName;// 类型的名称, 大写
	
	protected short length;// 长度
	protected short precision;// 精度
	
	public DBDataType(short sqlType) {
		this(sqlType, NO_LIMIT, NO_LIMIT);
	}
	public DBDataType(short sqlType, short length) {
		this(sqlType, length, NO_LIMIT);
	}
	public DBDataType(short sqlType, short length, short precision) {
		this.typeName = getClass().getSimpleName().toUpperCase();
		this.sqlType = sqlType;
		this.length = length;
		this.precision = precision;
	}
	
	public short getSqlType() {
		return sqlType;
	}
	public String getTypeName() {
		return typeName;
	}
	
	/**
	 * 是否是字符类型
	 * @return
	 */
	public boolean isCharacterType() {
		return false;
	}
	
	/**
	 * 修正输入的长度值
	 * @param inputLength
	 * @return
	 */
	public short correctInputLength(short inputLength) {
		if(this.length == NO_LIMIT) {
			return NO_LIMIT;
		}
		if(inputLength < 1 || inputLength > this.length) {
			return this.length;
		}
		return inputLength;
	}
	
	/**
	 * 修正输入的精度值
	 * @param inputLength
	 * @param inputPrecision
	 * @return
	 */
	public short correctInputPrecision(short inputLength, short inputPrecision) {
		if(this.precision == NO_LIMIT) {
			return NO_LIMIT;
		}
		if(inputPrecision < 0 || inputPrecision > this.precision) {
			inputPrecision = this.precision;
		}
		if(inputPrecision > inputLength) {
			inputPrecision = inputLength;
		}
		return inputPrecision;
	}
	
	/**
	 * 获取数据库类型对应的sql语句
	 * @param length
	 * @param precision
	 * @return
	 */
	public String getDBType4SqlStatement(short length, short precision) {
		if(length == NO_LIMIT) {
			return getTypeName();
		}
		if(precision == NO_LIMIT) {
			return getTypeName() + "("+length+")";
		}
		return getTypeName() + "("+length+", "+precision+")";
	}
	
	@Override
	public String toString() {
		return typeName;
	}
	
	/**
	 * 比较类型名称
	 * @param other
	 * @return
	 */
	public boolean equalsTypeName(DBDataType other) {
		if(other == null) 
			return false;
		if(other == this) 
			return true;
		return typeName.equals(other.typeName);
	}
}