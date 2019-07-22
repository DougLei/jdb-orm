package com.douglei.orm.sessions.session.table.impl;

/**
 * 不支持update没有主键的持久化对象异常
 * @author DougLei
 */
public class UnsupportUpdatePersistentWithoutPrimaryKeyException extends RuntimeException{
	private static final long serialVersionUID = -8963135373059624620L;

	public UnsupportUpdatePersistentWithoutPrimaryKeyException(String code) {
		super("不支持update没有主键的持久化对象 ["+code+"]");
	}
}
