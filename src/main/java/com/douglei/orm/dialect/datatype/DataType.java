package com.douglei.orm.dialect.datatype;

import java.io.Serializable;

import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 数据类型
 * @author DougLei
 */
public abstract class DataType implements Serializable{
	
	/**
	 * 获取数据类型的分类
	 * @return
	 */
	public abstract Classification getClassification();
	
	/**
	 * 获取类型名称, 全局唯一
	 * @return
	 */
	public String getName() {
		return getClass().getName();
	}

	/**
	 * 验证指定属性名的值
	 * @param fieldName
	 * @param fieldValue
	 * @param length
	 * @param precision
	 * @return
	 */
	public abstract ValidationResult validate(String fieldName, Object fieldValue, int length, int precision);
	
	@Override
	public final boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(this == obj)
			return true;
		return this.getName().equals(((DataType)obj).getName());
	}
}
