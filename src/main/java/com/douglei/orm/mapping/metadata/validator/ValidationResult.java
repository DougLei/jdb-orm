package com.douglei.orm.mapping.metadata.validator;

import com.douglei.i18n.Result;

/**
 * 验证结果对象
 * @author DougLei
 */
public class ValidationResult extends Result{
	private int index; // 如果验证的是集合, 该字段用来记录验证数据在集合中的下标
	private String name; // 记录验证失败的对象名称
	
	public ValidationResult() {}
	public ValidationResult(String name, String originMessage, String code, Object... params) {
		super(originMessage, code, params);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public final int getIndex() {
		return index;
	}
	public final void setIndex(int index) {
		this.index = index;
	}
}
