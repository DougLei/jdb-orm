package com.douglei.orm.sessionfactory.validator.table;

import com.douglei.orm.mapping.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class UniqueValidationResult extends ValidationResult {
	
	public UniqueValidationResult(String validateFieldName) {
		super(validateFieldName, "值不唯一, 已存在相同值的数据", "jdb.data.validator.value.violation.unique.constraint");
	}
}
