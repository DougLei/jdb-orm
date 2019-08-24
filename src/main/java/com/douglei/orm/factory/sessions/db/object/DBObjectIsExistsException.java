package com.douglei.orm.factory.sessions.db.object;

import com.douglei.orm.core.dialect.db.object.DBObjectType;
import com.douglei.orm.factory.sessions.ExecutionException;

/**
 * 数据库对象存在异常
 * @author DougLei
 */
public class DBObjectIsExistsException extends ExecutionException{
	private static final long serialVersionUID = 1308263209850256334L;

	public DBObjectIsExistsException(DBObjectType dbObjectType, String dbObjectName) {
		super("已经存在名为[" + dbObjectName + "]的["+dbObjectType.name()+"]");
	}
}
