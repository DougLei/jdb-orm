package com.douglei.orm.sessions.sqlsession;

/**
 * 数据库对象不存在异常
 * @author DougLei
 */
public class DBObjectNotExistsException extends RuntimeException{
	private static final long serialVersionUID = -1589603214568314800L;

	public DBObjectNotExistsException(DBObjectType dbObjectType, String dbObjectName) {
		super("不存在名为[" + dbObjectName + "]的["+dbObjectType.getName()+"]");
	}
}
