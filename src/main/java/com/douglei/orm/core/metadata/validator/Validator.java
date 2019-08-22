package com.douglei.orm.core.metadata.validator;

import java.io.Serializable;

/**
 * 验证器
 * @author DougLei
 */
public abstract class Validator implements Serializable{
	
	/**
	 * 验证器配置值
	 * 即在配置文件中, 属性名等于号后面配置的值
	 */
	protected String validatorConfigValue;
	
	/**
	 * 获取验证器的名称
	 * 这个是必须配置, 和配置文件中的属性名一致
	 * @return
	 */
	public abstract String getName();
	
	/**
	 * 进行验证, 如果验证通过, 则返回null, 否则返回验证失败的message
	 * @param value
	 * @return
	 */
	public abstract ValidatorResult doValidate(Object value);

	final Validator setValidatorConfigValue(String validatorConfigValue) {
		this.validatorConfigValue = validatorConfigValue;
		return this;
	}
}
