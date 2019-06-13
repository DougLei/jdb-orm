package com.douglei.orm.sessions.session.table.impl.persistent.id;

/**
 * Identity不支持的数据类型异常
 * @author DougLei
 */
public class UnsupportedIdentityDataTypeException extends RuntimeException{
	private static final long serialVersionUID = 1368833498163289205L;

	public UnsupportedIdentityDataTypeException(String message) {
		super(message);
	}
}
