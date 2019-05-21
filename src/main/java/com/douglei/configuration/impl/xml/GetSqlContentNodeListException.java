package com.douglei.configuration.impl.xml;

/**
 * 获取<sql>下<content>元素的NodeList异常
 * @author DougLei
 */
public class GetSqlContentNodeListException extends RuntimeException{
	private static final long serialVersionUID = -7413354778951191429L;

	public GetSqlContentNodeListException(Throwable cause) {
		super("获取<sql>下<content>元素的NodeList时出现异常", cause);
	}
}
