package com.douglei.database.metadata.table.column.extend;

/**
 * 列属性
 * @author DougLei
 */
public class ColumnProperty {
	private String name;// 列名
	private boolean primaryKey;// 是否是主键
	private short length;// 长度
	private short precision;// 精度
	private String defaultValue;// 默认值
	private boolean unique;// 是否唯一
	private boolean nullabled;// 是否可为空
	private boolean validateData;// 是否验证数据, 即是否对传入的数据进行验证
	
	public ColumnProperty(String name, boolean primaryKey, short length, short precision,
			String defaultValue, boolean unique, boolean nullabled, boolean validateData) {
		this.name = name;
		this.primaryKey = primaryKey;
		this.length = length;
		this.precision = precision;
		this.defaultValue = defaultValue;
		this.unique = unique;
		this.nullabled = nullabled;
		this.validateData = validateData;
	}
	
	public String getName() {
		return name;
	}
	public boolean isPrimaryKey() {
		return primaryKey;
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
