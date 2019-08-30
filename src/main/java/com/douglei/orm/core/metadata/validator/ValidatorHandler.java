package com.douglei.orm.core.metadata.validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.core.metadata.validator.internal._NullableValidator;

/**
 * 
 * @author DougLei
 */
public class ValidatorHandler implements Serializable{
	private static final long serialVersionUID = -5505149989143310710L;
	private String name;
	private boolean byConfig;// 是否使用配置方式创建的ValidatorHandler实例
	private _NullableValidator _nullableValidator;// 必须先验证非空, 然后再进行其他验证, 如果可为空, 且值为空, 则不用进行其他验证, 所以该验证器必须配置
	private List<Validator> validators;
	
	public ValidatorHandler(String name) {
		this(name, false);
	}
	public ValidatorHandler(String name, boolean byConfig) {
		this.name = name.toUpperCase();
		this.byConfig = byConfig;
	}

	/**
	 * 是否不存在是否为空验证器
	 * @return
	 */
	public boolean unexistsNullableValidator() {
		return _nullableValidator == null;
	}
	
	/**
	 * [必须设置] 设置是否可为空验证器
	 * @param nullable
	 */
	public void setNullableValidator(boolean nullable) {
		if(_nullableValidator == null){
			_nullableValidator = new _NullableValidator(nullable);
		}
	}
	
	/**
	 * 添加验证器
	 * @param validatorName 验证器的名称, 即配置文件中的属性名
	 * @param validatorConfigValue 验证器的配置值, 即配置文件中属性名等号右边配置的值
	 */
	public void addValidator(String validatorName, String validatorConfigValue) {
		addValidator(ValidatorContext.getValidatorInstance(validatorName, validatorConfigValue));
	}
	
	/**
	 * 添加验证器
	 * @param validator
	 */
	public void addValidator(Validator validator) {
		if(validators == null) {
			validators = new ArrayList<Validator>();
		}
		validators.add(validator);
	}
	
	/**
	 * 进行验证, 如果验证通过, 则返回null, 否则返回验证失败的message
	 * @param value
	 * @return
	 */
	public ValidationResult doValidate(Object value) {
		if(_nullableValidator == null) {
			throw new NullPointerException("必须设置是否为空验证器["+_NullableValidator.class.getName()+"]");
		}
		ValidationResult result = _nullableValidator.doValidate(name, value);
		if(result == null && value != null && validators != null) {
			for (Validator validator : validators) {
				result = validator.doValidate(name, value);
				if(result != null) {
					break;
				}
			}
		}
		return result;
	}

	public String getName() {
		return name;
	}
	public boolean byConfig() {
		return byConfig;
	}
}
