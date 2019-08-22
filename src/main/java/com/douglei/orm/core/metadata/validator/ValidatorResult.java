package com.douglei.orm.core.metadata.validator;

/**
 * 
 * @author DougLei
 */
public interface ValidatorResult {
	
	/**
	 * 返回code, 后续可以集成国际化
	 * @return
	 */
	String getCode();
	
	/**
	 * 返回验证结果message
	 * @return
	 */
	String getMessage();
}
