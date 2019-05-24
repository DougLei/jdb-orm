package com.douglei.database.metadata.sql.content.node.impl;

/**
 * 不支持的collection类型异常
 * @author DougLei
 */
public class UnsupportCollectionTypeException extends RuntimeException{
	private static final long serialVersionUID = -2316336324699129043L;

	public UnsupportCollectionTypeException(String message) {
		super(message);
	}
}
