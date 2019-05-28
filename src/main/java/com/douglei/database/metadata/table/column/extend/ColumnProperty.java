package com.douglei.database.metadata.table.column.extend;

/**
 * 列属性
 * @author DougLei
 */
public class ColumnProperty {
	
	private String name;// 列名
	private short length;// 长度
	private short precision;// 精度
	private boolean nullabled;// 是否可为空
	
	private boolean primaryKey;// 是否是主键
	private boolean unique;// 是否唯一
	private String defaultValue;// 默认值
	
	private boolean validateData;// 是否验证数据, 即是否对传入的数据进行验证
	
	public ColumnProperty(String name, short length, short precision, boolean nullabled, 
			boolean primaryKey, boolean unique, String defaultValue, 
			boolean validateData) {
		this.name = name;
		this.length = length;
		this.precision = precision;
		setNullabled(nullabled, primaryKey);
		
		this.primaryKey = primaryKey;
		this.unique = unique;// TODO oracle mysql当字段可为空且唯一时, 可以插入多个null, 即null不做为相同的数据, 而sqlserver会将null也做重复判断, 抛出异常
		this.defaultValue = defaultValue;
		
		this.validateData = validateData;
	}
	private void setNullabled(boolean nullabled, boolean primaryKey) {
		if(primaryKey) {
			this.nullabled = false;
		}else {
			this.nullabled = nullabled;
		}
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
