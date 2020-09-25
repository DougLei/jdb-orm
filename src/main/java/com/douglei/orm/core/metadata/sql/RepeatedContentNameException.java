package com.douglei.orm.core.metadata.sql;

/**
 * 
 * @author DougLei
 */
public class RepeatedContentNameException extends RuntimeException{
	private static final long serialVersionUID = -5637734803766790213L;

	public RepeatedContentNameException(String name) {
		super("重复配置了name为"+name+"的<content>元素");
	}
}
