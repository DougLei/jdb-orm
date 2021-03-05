package com.douglei.orm.mapping.validator;

import java.io.Serializable;

/**
 * 
 * @author DougLei
 */
public abstract class Validator implements Serializable{
	private transient int priority;
	protected Object value;
	
	protected Validator(int priority, Object value) {
		this.priority = priority;
		this.value = value;
	}
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
