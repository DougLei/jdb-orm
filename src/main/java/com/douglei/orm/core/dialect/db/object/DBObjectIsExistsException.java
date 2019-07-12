package com.douglei.orm.core.dialect.db.object;

/**
 * 数据库对象存在异常
 * @author DougLei
 */
public class DBObjectIsExistsException extends RuntimeException{
	private static final long serialVersionUID = 7207617729549006728L;

	public DBObjectIsExistsException(DBObjectType dbObjectType, String dbObjectName) {
		super("已经存在名为[" + dbObjectName + "]的["+dbObjectType.getName()+"]");
	}
}
