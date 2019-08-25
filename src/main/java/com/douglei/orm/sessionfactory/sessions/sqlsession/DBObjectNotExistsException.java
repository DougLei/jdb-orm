package com.douglei.orm.sessionfactory.sessions.sqlsession;

import com.douglei.orm.core.dialect.db.object.DBObjectType;
import com.douglei.orm.sessionfactory.sessions.ExecutionException;

/**
 * 数据库对象不存在异常
 * @author DougLei
 */
public class DBObjectNotExistsException extends ExecutionException{
	private static final long serialVersionUID = -3631470896941063821L;

	public DBObjectNotExistsException(DBObjectType dbObjectType, String dbObjectName) {
		super("不存在名为[" + dbObjectName + "]的["+dbObjectType.name()+"]");
	}
}
