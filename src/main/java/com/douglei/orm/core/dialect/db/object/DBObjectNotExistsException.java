package com.douglei.orm.core.dialect.db.object;

/**
 * 数据库对象不存在异常
 * @author DougLei
 */
public class DBObjectNotExistsException extends RuntimeException{
	private static final long serialVersionUID = 4813637449348321254L;

	public DBObjectNotExistsException(DBObjectType dbObjectType, String dbObjectName) {
		super("不存在名为[" + dbObjectName + "]的["+dbObjectType.getName()+"]");
	}
}
