package com.douglei.orm.configuration.environment.mapping;

import com.douglei.orm.factory.dynamic.mapping.DynamicMappingException;

/**
 * 动态移除映射异常
 * @author DougLei
 */
public class DynamicRemoveMappingException extends DynamicMappingException{
	private static final long serialVersionUID = -5207059895403450560L;

	public DynamicRemoveMappingException(Throwable cause) {
		super("动态移除映射时出现异常", cause);
	}
}
