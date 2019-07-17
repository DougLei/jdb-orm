package com.douglei.orm.core.dialect.impl.mysql.db.object;

import java.util.List;

import com.douglei.orm.core.dialect.db.object.DBObjectHandler;
import com.douglei.orm.core.dialect.db.object.DBObjectType;

/**
 * 
 * @author DougLei
 */
public class DBObjectHandlerImpl extends DBObjectHandler {

	@Override
	protected short nameMaxLength() {
		return 64;
	}

	@Override
	public String getQueryDBObjectIsExistsSqlStatement(DBObjectType dbObjectType, String dbObjectName, List<Object> parameters) {
		parameters.add(dbObjectName);
		switch(dbObjectType) {
			case PROCEDURE:
				parameters.add(dbObjectType.name());
				return "select count(1) from information_schema.routines where routine_schema = (select database()) and routine_name = ? and routine_type=?";
			case VIEW:
				return "select count(1) from information_schema.views where table_schema = (select database()) and table_name = ?";
		}
		return null;
	}
}