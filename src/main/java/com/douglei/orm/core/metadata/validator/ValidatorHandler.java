package com.douglei.orm.core.metadata.validator;

import java.io.Serializable;

import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public class ValidatorHandler implements Serializable{
	/**
	 * name
	 */
	private String name;
	/**
	 * 验证的正则表达式
	 */
	private String regex;
	/**
	 * 验证是否可为空字符串, 默认值为true
	 */
	private boolean blankable = true;
	/**
	 * 是否可为空
	 */
	private boolean nullable;
	/**
	 * 默认值
	 */
	private String defaultValue;
	
	// 设置通用的验证配置信息
	public void setCommonValidatorConfig(boolean nullable, String defaultValue) {
		this.nullable = nullable;
		this.defaultValue = defaultValue;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRegex(String regex) {
		if(StringUtil.notEmpty(regex)) {
			this.regex = regex;
		}
	}
	public void setBlankable(String blankable) {
		if(VerifyTypeMatchUtil.isBoolean(blankable)) {
			this.blankable = Boolean.parseBoolean(blankable);
		}
	}
}
