package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.id;

/**
 * Identity不支持的数据类型异常
 * @author DougLei
 */
public class UnsupportedIdentityDataTypeException extends RuntimeException{
	private static final long serialVersionUID = -3587706486964486007L;

	public UnsupportedIdentityDataTypeException(String message) {
		super(message);
	}
}
