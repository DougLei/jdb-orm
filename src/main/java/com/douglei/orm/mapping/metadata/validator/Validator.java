package com.douglei.orm.mapping.metadata.validator;

import java.io.Serializable;

import com.douglei.tools.utils.StringUtil;

/**
 * 验证器
 * @author DougLei
 */
public abstract class Validator implements Serializable{

	/**
	 * 获取验证器的顺序值, 当存在多个验证器时可进行排序
	 * @return
	 */
	protected int getOrder() {
		return 100;
	}
	
	/**
	 * 是否要调用下一个验证器进行验证; 如果当前验证器验证后没有必要再验证下去时, 该方法可返回false
	 * @return
	 */
	protected boolean toNext() {
		return true;
	}
	
	/**
	 * 在获取了validatorConfigValue后, 初始化验证器, 可以不实现
	 * @param configValue
	 */
	protected void init_(String configValue) {
	}
	
	/**
	 * 进行验证, 如果验证通过, 则返回null, 否则返回验证失败的message
	 * @param name 要验证的属性名
	 * @param value 要验证的属性值, 不会为null, 即在调用该方法时, 不会传入null
	 * @return
	 */
	public abstract ValidationResult validate(String name, Object value);
	
	/**
	 * 初始化验证器
	 * @param configValue 验证器的配置值, 即在配置文件中, 属性名等号后面配置的值
	 * @return
	 */
	final Validator init(String configValue) {
		if(StringUtil.isEmpty(configValue)) 
			throw new NullPointerException("验证器的配置值不能为空");
		init_(configValue);
		return this;
	}
}
