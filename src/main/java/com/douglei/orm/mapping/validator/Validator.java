package com.douglei.orm.mapping.validator;

import java.io.Serializable;

/**
 * 
 * @author DougLei
 */
public interface Validator extends Serializable{
	
	/**
	 * 获取验证器的(验证)优先级; 越小越优先
	 * @return
	 */
	int getPriority();
}
