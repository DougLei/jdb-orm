package com.douglei.orm.core.metadata.validator;

import java.io.Serializable;

import com.douglei.tools.utils.StringUtil;

/**
 * 验证器
 * @author DougLei
 */
public abstract class Validator implements Serializable{
	private static final long serialVersionUID = -6885545038721692123L;

	/**
	 * 在获取了validatorConfigValue后, 初始化验证器, 可以不实现
	 * @param configValue
	 */
	protected void initial(String configValue) {
	}
	
	/**
	 * 进行验证, 如果验证通过, 则返回null, 否则返回验证失败的message
	 * @param fieldName 要验证的属性名
	 * @param value 要验证的属性值
	 * @return
	 */
	public abstract ValidationResult validate(String fieldName, Object value);
	
	/**
	 * 初始化验证器
	 * @param configValue 验证器的配置值, 即在配置文件中, 属性名等号后面配置的值
	 * @return
	 */
	final Validator initialValidator(String configValue) {
		if(StringUtil.isEmpty(configValue)) {
			throw new NullPointerException("验证器的配置不能为空");
		}
		initial(configValue);
		return this;
	}
}
