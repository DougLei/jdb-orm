package com.douglei.orm.configuration.environment.mapping;

import com.douglei.orm.factory.dynamic.mapping.DynamicMappingException;

/**
 * 动态添加映射异常
 * @author DougLei
 */
public class DynamicAddMappingException extends DynamicMappingException{
	private static final long serialVersionUID = -3854835049734570104L;

	public DynamicAddMappingException(Throwable cause) {
		super("动态添加映射时出现异常", cause);
	}
}
