package com.douglei.orm.configuration.environment.mapping;

import com.douglei.orm.sessionfactory.DynamicMappingException;

/**
 * 动态覆盖映射异常
 * @author DougLei
 */
public class DynamicCoverMappingException extends DynamicMappingException{
	private static final long serialVersionUID = 7829607046747663717L;

	public DynamicCoverMappingException(Throwable cause) {
		super("动态覆盖映射时出现异常", cause);
	}
}
