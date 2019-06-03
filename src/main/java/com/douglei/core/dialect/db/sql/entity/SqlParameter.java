package com.douglei.core.dialect.db.sql.entity;

import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public class SqlParameter extends AbstractSqlParameter{
	
	protected short length;// 长度
	protected short precision;// 精度
	protected boolean nullabled;// 是否可为空
	protected String defaultValue;// 默认值
	
	public SqlParameter(String configurationText) {
		super(configurationText, false);
		setLength(propertyMap.get("length"));
		setPrecision(propertyMap.get("precision"));
		setNullabled(propertyMap.get("nullabled"));
		setDefaultValue(propertyMap.get("defaultValue"));
		clearPropertyMap(true);
	}

	void setLength(String length) {
		if(ValidationUtil.isLimitShort(length)) {
			this.length = Short.parseShort(length);
		}
	}
	void setPrecision(String precision) {
		if(ValidationUtil.isLimitShort(precision)) {
			this.precision = Short.parseShort(precision);
		}
	}
	void setNullabled(String nullabled) {
		if(ValidationUtil.isBoolean(nullabled)) {
			this.nullabled = Boolean.parseBoolean(nullabled);
		}else {
			this.nullabled = false;
		}
	}
	void setDefaultValue(String defaultValue) {
		if(defaultValue != null) {
			this.defaultValue = defaultValue;
		}
	}
	
	public short getLength() {
		return length;
	}
	public short getPrecision() {
		return precision;
	}
	public boolean isNullabled() {
		return nullabled;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
}
