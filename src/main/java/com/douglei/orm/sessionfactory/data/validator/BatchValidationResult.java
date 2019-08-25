package com.douglei.orm.sessionfactory.data.validator;

import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * 批量验证(数据集合)时, 记录验证失败时的数据index, 和验证结果对象
 * @author DougLei
 */
public class BatchValidationResult {
	private byte index;
	private ValidationResult validationResult;
	
	private BatchValidationResult(byte index, ValidationResult validationResult) {
		this.index = index;
		this.validationResult = validationResult;
	}

	public static BatchValidationResult newInstance(byte index, ValidationResult validationResult) {
		if(validationResult != null) {
			return new BatchValidationResult(index, validationResult);
		}
		return null;
	}

	public byte getIndex() {
		return index;
	}
	public ValidationResult getValidationResult() {
		return validationResult;
	}
}
