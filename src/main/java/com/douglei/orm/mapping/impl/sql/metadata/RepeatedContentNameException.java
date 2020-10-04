package com.douglei.orm.mapping.impl.sql.metadata;

/**
 * 
 * @author DougLei
 */
public class RepeatedContentNameException extends RuntimeException{

	public RepeatedContentNameException(String name) {
		super("重复配置了name为"+name+"的<content>元素");
	}
}
