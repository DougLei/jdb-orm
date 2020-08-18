package com.douglei.orm.core.metadata.validator;

import com.douglei.i18n.Result;

/**
 * 验证结果对象
 * @author DougLei
 */
public abstract class ValidationResult extends Result{
	private short index; // 如果验证的是集合, 该字段用来记录验证数据在集合中的下标
	private String fieldName; // 记录验证失败的属性名
	
	public ValidationResult(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public final short getIndex() {
		return index;
	}
	public final void setIndex(short index) {
		this.index = index;
	}
}
