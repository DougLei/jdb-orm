package com.douglei.orm.mapping.impl.table.metadata;

import java.util.List;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.mapping.validator.Validator;

/**
 * 列元数据
 * @author DougLei
 */
public class ColumnMetadata extends AbstractMetadata {
	private String property;// 映射的代码类中的属性名
	private DBDataType dbDataType;// 数据类型
	private int length;// 长度
	private int precision;// 精度
	private boolean nullable;// 是否可为空
	private String description;// 描述
	private List<Validator> validators; // 验证器集合
	
	public ColumnMetadata(String name, String oldName, String property, DBDataType dbDataType, int length, int precision, boolean nullable, String description) {
		this.name = name;
		this.oldName = oldName;
		this.property = property;
		
		this.dbDataType = dbDataType;
		this.length = length;
		this.precision = precision;
		
		this.nullable = nullable;
		this.description = description;
	}
	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}


	@Override
	public String getCode() {
		if(property == null) 
			return name;
		return property;
	}
	
	@Override
	public boolean equals(Object obj) {
		return name.equals(((ColumnMetadata)obj).name);
	}

	public String getProperty() {
		return property;
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
	public boolean isNullable() {
		return nullable;
	}
	public String getDescription() {
		return description;
	}
	public List<Validator> getValidators() {
		return validators;
	}
}
