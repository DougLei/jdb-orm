package com.douglei.orm.core.metadata.validator;

import com.douglei.orm.core.result.Result;

/**
 * 验证结果对象
 * @author DougLei
 */
public abstract class ValidationResult extends Result{
	public static final String i18nCodePrefix = "jdb.data.validator.";
	private short index; // 如果验证的是集合, 该字段用来记录验证数据在集合中的下标
	private String validateFieldName;
	
	public ValidationResult(String validateFieldName) {
		this.validateFieldName = validateFieldName;
	}
	
	public final void setIndex(short index) {
		this.index = index;
	}
	
	public final String getValidateFieldName() {
		return validateFieldName;
	}
	public final short getIndex() {
		return index;
	}
}
