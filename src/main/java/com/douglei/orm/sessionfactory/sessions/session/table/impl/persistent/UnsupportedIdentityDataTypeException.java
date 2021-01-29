package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

/**
 * Identity不支持的数据类型异常
 * @author DougLei
 */
public class UnsupportedIdentityDataTypeException extends RuntimeException{
	private static final long serialVersionUID = 5063025353932494154L;

	public UnsupportedIdentityDataTypeException() {
		super("目前id只支持[int][java.lang.Integer], [long][java.lang.Long], [java.lang.String]或[java.util.Map]");
	}
}