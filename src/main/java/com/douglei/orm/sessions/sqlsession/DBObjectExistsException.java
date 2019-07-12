package com.douglei.orm.sessions.sqlsession;

/**
 * 数据库对象存在异常
 * @author DougLei
 */
public class DBObjectExistsException extends RuntimeException{
	private static final long serialVersionUID = -1083805220309587204L;

	public DBObjectExistsException(DBObjectType dbObjectType, String dbObjectName) {
		super("已经存在名为[" + dbObjectName + "]的["+dbObjectType.getName()+"]");
	}
}
