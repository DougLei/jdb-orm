package com.douglei.orm.sessionfactory.sessions.session.table.impl;

/**
 * 不支持update没有主键的持久化对象异常
 * @author DougLei
 */
public class UnsupportUpdatePersistentWithoutPrimaryKeyException extends RuntimeException{
	private static final long serialVersionUID = -5645337078242912420L;

	public UnsupportUpdatePersistentWithoutPrimaryKeyException(String code) {
		super("不支持update没有主键的持久化对象 ["+code+"]");
	}
}
