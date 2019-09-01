package com.douglei.orm.core.metadata.validator;

import java.util.List;

import com.douglei.orm.core.result.Result;

/**
 * 验证结果对象
 * @author DougLei
 */
public abstract class ValidationResult extends Result{
	public static final String i18nCodePrefix = "jdb.data.validator.";
	private short index; // 如果验证的是集合, 该字段用来记录验证数据在集合中的下标
	private String validateFieldName;
	private List<String> validateFieldNames;
	
	public ValidationResult(String validateFieldName) {
		this.validateFieldName = validateFieldName;
	}
	public ValidationResult(List<String> validateFieldNames) {
		this.validateFieldNames = validateFieldNames;
	}
	
	public final void setIndex(short index) {
		this.index = index;
	}
	
	public final Object getValidateFieldName() {
		return validateFieldName != null? validateFieldName:validateFieldNames;
	}
	public final short getIndex() {
		return index;
	}
}
