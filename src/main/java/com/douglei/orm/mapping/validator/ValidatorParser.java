package com.douglei.orm.mapping.validator;

import com.douglei.orm.mapping.metadata.MetadataParseException;

/**
 * 
 * @author DougLei
 */
public interface ValidatorParser {

	/**
	 * 
	 * @return
	 */
	default String getType() {
		return getClass().getName();
	}
	
	/**
	 * 
	 * @param value 配置中, =后面的值
	 * @return 返回null, 会忽略当前验证器
	 * @throws MetadataParseException
	 */
	Validator parse(String value) throws MetadataParseException;
}
