package com.douglei.orm.sessionfactory.sessions.sqlsession;

import com.douglei.orm.core.dialect.db.object.DBObjectType;
import com.douglei.orm.sessionfactory.sessions.ExecutionException;

/**
 * 数据库对象存在异常
 * @author DougLei
 */
public class DBObjectIsExistsException extends ExecutionException{
	private static final long serialVersionUID = 3378525242861350277L;

	public DBObjectIsExistsException(DBObjectType dbObjectType, String dbObjectName) {
		super("已经存在名为[" + dbObjectName + "]的["+dbObjectType.name()+"]");
	}
}
