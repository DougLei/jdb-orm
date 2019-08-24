package com.douglei.orm.factory.sessions.db.object;

import com.douglei.orm.core.dialect.db.object.DBObjectType;
import com.douglei.orm.factory.sessions.ExecutionException;

/**
 * 数据库对象不存在异常
 * @author DougLei
 */
public class DBObjectNotExistsException extends ExecutionException{
	private static final long serialVersionUID = 1316233358969586102L;

	public DBObjectNotExistsException(DBObjectType dbObjectType, String dbObjectName) {
		super("不存在名为[" + dbObjectName + "]的["+dbObjectType.name()+"]");
	}
}
