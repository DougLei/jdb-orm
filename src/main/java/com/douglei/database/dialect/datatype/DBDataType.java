package com.douglei.database.dialect.datatype;

/**
 * 数据库数据类型
 * @author DougLei
 */
public abstract class DBDataType {
	protected static final short _N = -1;
	
	protected short sqlType;// @see java.sql.Types
	protected String typeName;// 类型的名称, 大写
	
	protected short length;// 长度, -1标识不需要配置长度
	protected short precision;// 精度
	
	
	public DBDataType(short sqlType) {
		this(sqlType, _N, _N);
	}
	public DBDataType(short sqlType, short length) {
		this(sqlType, length, _N);
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
	
	// 是否支持精度, 默认是不支持的
	public boolean supportPrecision() {
		return false;
	}

	// 修正输入的长度值
	public short fixInputLength(short inputLength) {
		if(this.length == _N) {
			return _N;
		}
		if(inputLength < 1 || inputLength > this.length) {
			return this.length;
		}
		return inputLength;
	}
	
	// 修正输入的精度值
	public short fixInputPrecision(short inputLength, short inputPrecision) {
		if(supportPrecision()) {
			if(inputPrecision < 0 || inputPrecision > this.precision) {
				inputPrecision = this.precision;
			}
			if(inputPrecision > inputLength) {
				inputPrecision = inputLength;
			}
			return inputPrecision;
		}
		return _N;
	}
}
