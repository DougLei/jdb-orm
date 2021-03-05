package com.douglei.orm.mapping.validator;

import com.douglei.orm.mapping.metadata.MetadataParseException;

/**
 * 
 * @author DougLei
 */
public interface ValidatorParser {
	
	/**
	 * 获取验证器类型
	 * @return
	 */
	default String getType() {
		return getClass().getName();
	}
	
	/**
	 * 获取验证器的(验证)优先级; 越小越优先
	 * @return
	 */
	default int getPriority() {
		return 30;
	}
	
	/**
	 * 
	 * @param value 配置文件中, 验证器类型名=后面配置的值
	 * @return 返回null, 框架会忽略当前配置的验证器
	 * @throws MetadataParseException
	 */
	Validator parse(String value) throws MetadataParseException;
}
