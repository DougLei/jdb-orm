package com.douglei.orm.mapping.impl.table.exception;

/**
 * 约束配置异常
 * @author DougLei
 */
public class ConstraintConfigurationException extends RuntimeException{
	private static final long serialVersionUID = -6368484028427718260L;

	public ConstraintConfigurationException(String message) {
		super(message);
	}
}
