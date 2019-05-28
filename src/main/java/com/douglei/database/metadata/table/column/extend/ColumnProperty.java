package com.douglei.database.metadata.table.column.extend;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 列属性
 * @author DougLei
 */
public class ColumnProperty {
	private String name;// 列名
	private boolean primaryKey;// 是否是主键
	private DBDataType dbDataType;// 对应数据库的数据类型, 如果没有, 则默认使用dataType的defaultDBDataType()
	private short length;// 长度
	private short precision;// 精度
	private String defaultValue;// 默认值
	private boolean unique;// 是否唯一
	private boolean nullabled;// 是否可为空
	private boolean validateData;// 是否验证数据, 即是否对传入的数据进行验证
	
	
	
	
	
	
	public String getName() {
		return name;
	}
	public boolean isPrimaryKey() {
		return primaryKey;
	}
	public DBDataType getDbDataType() {
		return dbDataType;
	}
	public short getLength() {
		return length;
	}
	public short getPrecision() {
		return precision;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public boolean isUnique() {
		return unique;
	}
	public boolean isNullabled() {
		return nullabled;
	}
	public boolean isValidateData() {
		return validateData;
	}
}
