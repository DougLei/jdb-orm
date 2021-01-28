package com.douglei.orm.mapping.impl.sql.metadata;

/**
 * 
 * @author DougLei
 */
public class RepeatedContentNameException extends RuntimeException{
	private static final long serialVersionUID = -1483673935804765393L;

	public RepeatedContentNameException(String name, String contentName) {
		super("在["+name+"]映射中, 重复配置了name为["+contentName+"]的<content>元素");
	}
}
