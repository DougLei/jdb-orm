package com.douglei.context.exception;

import javax.xml.parsers.DocumentBuilder;

/**
 * 创建DocumentBuilder实例异常
 * @author DougLei
 */
public class BuildDocumentBuilderInstanceException extends RuntimeException{
	private static final long serialVersionUID = -1002054446033749332L;

	public BuildDocumentBuilderInstanceException(Throwable cause) {
		super("创建"+DocumentBuilder.class.getName()+"实例时出现异常", cause);
	}
}
