package com.douglei.orm.mapping.validator;

import com.douglei.tools.i18n.Message;

/**
 * 
 * @author DougLei
 */
public class ValidateFailResult extends Message{
	private int index; // 被验证数据所在集合的位置 (下标)
	private String name; // 验证失败的数据值的名称 (列名/属性名/sql参数名)
	
	public ValidateFailResult(String name, String message, String code, Object... params) {
		super(message, code, params);
		this.name = name;
	}
	
	/**
	 * 记录被验证数据所在集合的位置 (下标)
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 获取被验证数据所在集合的位置 (下标)
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * 获取验证失败的数据值的名称 (列名/属性名/sql参数名)
	 * @return
	 */
	public String getName() {
		return name;
	}
}
