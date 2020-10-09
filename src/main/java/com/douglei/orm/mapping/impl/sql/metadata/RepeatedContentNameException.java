package com.douglei.orm.mapping.impl.sql.metadata;

/**
 * 
 * @author DougLei
 */
public class RepeatedContentNameException extends RuntimeException{
	private static final long serialVersionUID = 5832663690935006048L;

	public RepeatedContentNameException(String name) {
		super("重复配置了name为"+name+"的<content>元素");
	}
}
