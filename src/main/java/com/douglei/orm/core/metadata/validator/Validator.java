package com.douglei.orm.core.metadata.validator;

import java.io.Serializable;

import com.douglei.tools.utils.StringUtil;

/**
 * 验证器
 * @author DougLei
 */
public abstract class Validator implements Serializable{
	private static final long serialVersionUID = 1587862778246048587L;

	/**
	 * 在获取了validatorConfigValue后, 初始化验证器操作, 可为空
	 */
	protected void doInitial(String validatorConfigValue) {
	}
	
	/**
	 * 进行验证, 如果验证通过, 则返回null, 否则返回验证失败的message
	 * @param validateFieldName
	 * @param value
	 * @return
	 */
	public abstract ValidatorResult doValidate(String validateFieldName, Object value);

	/**
	 * 初始化验证器
	 * @param validatorConfigValue 验证器的配置值, 即在配置文件中, 属性名等号后面配置的值
	 * @return
	 */
	final Validator initialValidator(String validatorConfigValue) {
		if(StringUtil.isEmpty(validatorConfigValue)) {
			throw new NullPointerException("验证器的配置值不能为空");
		}
		doInitial(validatorConfigValue);
		return this;
	}
}
