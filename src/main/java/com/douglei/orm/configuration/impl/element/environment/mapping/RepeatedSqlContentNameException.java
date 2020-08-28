package com.douglei.orm.configuration.impl.element.environment.mapping;

/**
 * 
 * @author DougLei
 */
public class RepeatedSqlContentNameException extends Exception{
	private static final long serialVersionUID = -8292833914172835336L;

	public RepeatedSqlContentNameException(String sqlContentName) {
		super("重复配置了name=["+sqlContentName+"]的<sql-content>元素");
	}
}
