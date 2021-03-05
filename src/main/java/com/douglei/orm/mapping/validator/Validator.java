package com.douglei.orm.mapping.validator;

import com.douglei.orm.mapping.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public abstract class Validator implements Metadata{
	private transient int priority; // 和对应的Validator解析器的priority保持一致
	
	protected Validator(int priority) {
		this.priority = priority;
	}
	
	/**
	 * 获取优先级
	 * @return
	 */
	public int getPriority() {
		return priority;
	}
	
	/**
	 * 
	 * @param data 被验证的数据
	 * @return
	 */
	public abstract ValidateFailResult validate(ValidatedData data);
}
