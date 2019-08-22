package com.douglei.orm.core.metadata.validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author DougLei
 */
public class ValidatorHandler implements Serializable{
	
	private String name;
	private List<Validator> validators;
	
	public ValidatorHandler(String name) {
		this.name = name;
	}

	/**
	 * 添加验证器
	 * @param validatorName 验证器的名称, 即配置文件中的属性名
	 * @param validatorConfigValue 验证器的配置值, 即配置文件中属性名等号右边配置的值
	 */
	public void addValidator(String validatorName, String validatorConfigValue) {
		if(validators == null) {
			validators = new ArrayList<Validator>();
		}
		validators.add(ValidatorContext.getValidatorInstance(validatorName, validatorConfigValue));
	}
	
	/**
	 * 进行验证, 如果验证通过, 则返回null, 否则返回验证失败的message
	 * @param value
	 * @return
	 */
	public ValidatorResult doValidate(Object value) {
		if(validators != null) {
			ValidatorResult result = null;
			for (Validator validator : validators) {
				result = validator.doValidate(value);
				if(result != null) {
					return result;
				}
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}
}
