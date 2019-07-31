package com.douglei.orm.configuration.environment.mapping.table;

/**
 * 约束配置异常
 * @author DougLei
 */
public class ConstraintConfigurationException extends RuntimeException{
	private static final long serialVersionUID = -6880772259591758612L;

	public ConstraintConfigurationException(String message) {
		super(message);
	}
}
