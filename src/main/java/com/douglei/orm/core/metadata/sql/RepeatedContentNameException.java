package com.douglei.orm.core.metadata.sql;

/**
 * 
 * @author DougLei
 */
public class RepeatedContentNameException extends RuntimeException{
	private static final long serialVersionUID = -5637734803766790213L;

	public RepeatedContentNameException(String contentName) {
		super("重复配置了name=["+contentName+"]的<content>元素");
	}
}
