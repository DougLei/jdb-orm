package com.douglei.orm.context;

/**
 * 获取<sql>下<content>元素的NodeList异常
 * @author DougLei
 */
public class GetSqlContentNodeListException extends RuntimeException{
	private static final long serialVersionUID = -235779680363038285L;

	public GetSqlContentNodeListException(Throwable cause) {
		super("获取<sql>下<content>元素的NodeList时出现异常", cause);
	}
}
