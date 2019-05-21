package com.douglei.configuration.impl.xml;

import javax.xml.parsers.DocumentBuilder;

/**
 * 创建DocumentBuilder实例异常
 * @author DougLei
 */
public class BuildDocumentBuilderInstanceException extends RuntimeException{
	private static final long serialVersionUID = 8783326388311248508L;

	public BuildDocumentBuilderInstanceException() {
		super();
	}

	public BuildDocumentBuilderInstanceException(String message) {
		super(message);
	}

	public BuildDocumentBuilderInstanceException(Throwable cause) {
		super("创建"+DocumentBuilder.class.getName()+"实例时出现异常", cause);
	}
}
