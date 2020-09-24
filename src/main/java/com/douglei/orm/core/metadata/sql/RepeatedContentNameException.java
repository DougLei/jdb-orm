package com.douglei.orm.core.metadata.sql;

/**
 * 
 * @author DougLei
 */
public class RepeatedContentNameException extends RuntimeException{

	public RepeatedContentNameException(String name) {
		super("重复配置了name为"+name+"的<content>元素");
	}
}
